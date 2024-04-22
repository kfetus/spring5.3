package base.comm.websocket.javax;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import base.comm.vo.UserVO;

/**
 * @TODO 1. 관리자가 들어와서 경매 물건 초기화
 * 2. 낙찰 또는 유찰일 경우 다음 물건 자동 조회 해서 전송
 * 3. 관리자가 경매 진행 전체 STOP 가능
 * 4. 신호등
 * @author USER
 * Singleton으로 처리하기 위해 추가한 부분 : @Service
 *  @ServerEndpoint 이 어노테이션이 붙으면 웹소켓이 연결될때마다 객체가 인스턴스가 생성된다.
 */
@Service
@ServerEndpoint(value = "/auctionWebsocket.do", encoders = MsgEncoder.class, decoders = MsgDecoder.class, configurator = ServerConfigurator.class)
public class JavaxWebSocketEndpointServer {
	
	private final Logger LOGGER = LoggerFactory.getLogger(JavaxWebSocketEndpointServer.class);
	
	private List<Session> sessionList = new ArrayList<>();
	
	private int nowBidMoney = 0;
	private int upMoneyTime = 0;
	
	//싱글레톤으로 처리가 되지 않으면 매 접속마다 초기화 된다.
	private BidMessage bidMessage = null;

	//경매 물건 정보 초기화. 물건이 바뀔때마다 조회해서 전송해 줘야 함 
	public void initAuction() {
		//DB 조회해서 셋팅
		bidMessage = new BidMessage("2024", "R", 1500, 1800, "차량번호=12가1234,년식=2020,기어=auto,연료=가솔린,주행거리=10000,자가또는법인=자가", "차량 외부 이미지",
			"성능점검 이미지", "A++","준비");
		nowBidMoney = bidMessage.getMinAuctionMoney();
		if ( 1000 < bidMessage.getMinAuctionMoney() ) {
			upMoneyTime = 3;
		} else {
			upMoneyTime = 5;
		}
		
		serveAnyMessage(bidMessage);
	}
	
	//PathParam은 @ServerEndpoint에서 url값에 {} 같이 변수가 설정되어 있을때 받는 파라미터
	@OnOpen
	public void onOpen( @PathParam("param") String param,Session session, EndpointConfig config) throws Exception {
		LOGGER.debug("@@@@@@@@ onOpen");
		//ServerEndpointConfig에 값을 넣으면 session에 있는 내용이나 config에 있는 내용이나 같은 값. 다만 session은 ID를 설정해 주므로 이것으로 컨트롤
		LOGGER.debug("@@@@@@@@ onOpen session=" + session.getId() + "|" + session.getUserProperties());
//		LOGGER.debug("@@@@@@@@ onOpen config=" + config.getUserProperties());
		Object vo = session.getUserProperties().get("WEBSOCKET_USER_INFO");
		LOGGER.debug("@@@@@@@@ onOpen session vo=" + vo);

		if(  vo instanceof UserVO) {
			sessionList.add(session);
			if(bidMessage == null) {
				initAuction();
			} else {
				serveOneMessage(session,bidMessage);
			}
		} else {
			try {
				BidMessage localBidMessage = new BidMessage();
				localBidMessage.setConState("9999");
				localBidMessage.setMessage("로그인 정보가 없습니다.");
				session.getBasicRemote().sendObject(bidMessage);
				throw new Exception("login info is null");
			} catch (Exception e) {
				e.printStackTrace();
				//종료가 되면 안되므로 Exception을 던짐. 그 후 onError, onClose가 차례대로 호출됨
				throw e;
			}
		}
	}

	@OnMessage
	public void onMessage(Session session, BidMessage getMessage) {
		LOGGER.debug("@@@@@@@@ onMessage session=" + session.getId() + "|" + session.getUserProperties());
		LOGGER.debug("@@@@@@@@ onMessage message=" + getMessage);

		Object vo = session.getUserProperties().get("WEBSOCKET_USER_INFO");
		//Master만 시작이나 종료를 할 수 있다.
		if( "M".equals( ((UserVO) vo).getGrade()) ) {
			
		} else {

		}

		nowBidMoney += upMoneyTime;
		bidMessage.setNowAuctionMoney(nowBidMoney);
		serveAnyMessage( bidMessage);
	}

	@OnClose
	public void onClose(Session session) {
		LOGGER.debug("@@@@@@@@ onClose=" + session.getId() + "|" + session.getUserProperties());
		sessionList.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable t) {
		LOGGER.debug("@@@@@@@@ onError=" + session.getId() + "|" + session.getUserProperties());
		t.printStackTrace();
		LOGGER.error(t.getMessage());
	}

	/**
	 * 전체 인원 발송
	 * @param message
	 */
	private synchronized void serveAnyMessage(BidMessage message) {
		LOGGER.debug("@@@@@@@@ serveAnyMessage message =" + message);
		for (Session session : sessionList) {
            try {
    			LOGGER.debug("@@@@@@@@ serveAnyMessage session.getId() =" + session.getId());
    			LOGGER.debug("@@@@@@@@ serveAnyMessage WEBSOCKET_USER_INFO =" + session.getUserProperties().get("WEBSOCKET_USER_INFO"));
                Basic basic = session.getBasicRemote();
                basic.sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}	

	/**
	 * 특정 인원에게만 발송
	 * @param reqSession
	 * @param message
	 */
	private synchronized void serveOneMessage(Session reqSession, BidMessage message) {
		LOGGER.debug("@@@@@@@@ serveOneMessage message =" + message);
        try {
        	reqSession.getBasicRemote().sendObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}	
	
	
}