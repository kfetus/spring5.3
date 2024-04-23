package base.comm.websocket.javax;

import java.time.Duration;
import java.time.LocalTime;
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


/**
 * @TODO 1. 관리자가 들어와서 경매 시작 종료.=>완료
 * 2. 낙찰 또는 유찰일 경우 다음 물건 자동 조회 해서 전송
 * 3. 관리자가 경매 진행 전체 STOP 가능 => 완료
 * 4. 신호등 => 진행 중. 실시간 업데이트 추가 필요. 2번과 연관.
 * 5. 4초간 입찰이 없을경우 처리(낙찰,유찰 메세지)
 * 6. REDIS 연동해서 멀티서버일 경우 처리
 * @author USER
 * 
 *  @ServerEndpoint 이 어노테이션이 붙으면 웹소켓이 연결될때마다 객체가 인스턴스가 생성된다. Singleton으로 처리하기 위해 추가한 부분 : @Service. (@Component도 상관은 없다)
 */
@Service
@ServerEndpoint(value = "/auctionWebsocket.do", encoders = MsgEncoder.class, decoders = MsgDecoder.class, configurator = ServerConfigurator.class)
public class JavaxWebSocketEndpointServer {
	
	private final Logger LOGGER = LoggerFactory.getLogger(JavaxWebSocketEndpointServer.class);
	
	private List<Session> sessionList = new ArrayList<>();
	
	//현재 응찰 가격
	private int nowBidMoney = 0;
	//응찰시 호가 상승 기준 가격
	private int upMoneyByBid = 0;
	
	//싱글레톤으로 처리가 되지 않으면 매 접속마다 초기화 된다.
	private BidMessage bidMessage = null;
	
	//경매 순번
	private int bidCount = 0;
	//테스트용도. 경매 물건 목록
	private List<BidMessage> bidList = new ArrayList<BidMessage>();

	//경매 물건 정보 초기화. 물건이 바뀔때마다 조회해서 전송해 줘야 함 
	public void initAuction() {
		//DB 조회해서 셋팅
		bidList.add(new BidMessage("2023", "R", 1500, 1800, "차량번호=12가1234,년식=2020,기어=auto,연료=가솔린,주행거리=10000,자가또는법인=자가", "차량 외부 이미지", "성능점검 이미지", "A++","준비"));
		bidList.add(new BidMessage("2024", "R", 5000, 5300, "차량번호=12가0001,년식=2024,기어=auto,연료=가솔린,주행거리=1000,자가또는법인=자가", "차량 외부 이미지","성능점검 이미지", "A++","준비"));
		bidList.add(new BidMessage("2025", "R", 500, 550, "차량번호=12가5678,년식=2000,기어=auto,연료=디젤,주행거리=100000,자가또는법인=자가", "차량 외부 이미지","성능점검 이미지", "A++","준비"));
		bidList.add(new BidMessage("2026", "R", 1000, 1100, "차량번호=12가9012,년식=2010,기어=auto,연료=가솔린,주행거리=10000,자가또는법인=자가", "차량 외부 이미지","성능점검 이미지", "A++","준비"));
		//테스트용도. 경매 낙찰 또는 유찰 후 자동 넘어가기 위해.
		bidMessage = bidList.get(bidCount);
		bidCount++;
		
		nowBidMoney = bidMessage.getMinAuctionMoney();
		if ( 1000 < bidMessage.getMinAuctionMoney() ) {
			upMoneyByBid = 5;
		} else {
			upMoneyByBid = 3;
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

		BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
		LOGGER.debug("@@@@@@@@ onOpen session vo=" + vo);
		
		if(  !"".equals(vo.getUserId()) ) {
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
				session.getBasicRemote().sendObject(localBidMessage);
				throw new Exception("login info is null");
			} catch (Exception e) {
				e.printStackTrace();
				//종료가 되면 안되므로 Exception을 던짐. 그 후 onError, onClose가 차례대로 호출됨
				throw e;
			}
		}
	}
	
	@OnMessage
	public void onMessage(Session session, BidMessage onMessage) {
		LOGGER.debug("@@@@@@@@ onMessage session=" + session.getId() + "|" + session.getUserProperties());
		LOGGER.debug("@@@@@@@@ onMessage message=" + onMessage);

		BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
		if( "START_MASTER".equals(onMessage.getMessage())) {
			//Master만 시작이나 종료를 할 수 있다.
			if( "M".equals( vo.getGrade()) ) {
				bidMessage.setAuctionState("S");
			}
		} else if( "STOP_MASTER".equals(onMessage.getMessage())) {
			//Master만 시작이나 종료를 할 수 있다.
			if( "M".equals( vo.getGrade()) ) {
				bidMessage.setAuctionState("E");
			}
		} else {
			if( "S".equals(bidMessage.getAuctionState()) || "C".equals(bidMessage.getAuctionState()) ) {
				nowBidMoney += upMoneyByBid;
				bidMessage.setNowAuctionMoney(nowBidMoney);
				
				vo.setNowBidMoney(nowBidMoney);
				vo.setLastBidTime(LocalTime.now());
			}
		}

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

	//경쟁 상태 체크 함수. 4초이내 콜인 경우 카운트
	//현재 들어와 있는 키값이 아이디인 시간 어레이를 체크해서 제거한 후 남은 갯수로 경쟁 상태여부를 셋팅
	public int getcompetePeopleNum() {
		LOGGER.debug("@@@@@@@@ getcompetePeopleNum sessionList.size =" + sessionList.size());

		int retCount = 0;
		LocalTime nowTime = LocalTime.now();
		for (Session session : sessionList) {
   			BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
   			if(vo.getLastBidTime() == null) continue;//아직 경매 호가를 안한 입찰자는 정보가 없음
   			long minusTime = Duration.between( vo.getLastBidTime(), nowTime ).getSeconds();
   			if( minusTime < 4 ) {
   				LOGGER.debug("@@@@@@@@ getcompetePeopleNum sessionList.size =" + session.getId() + "|" + session.getUserProperties());
   				retCount++;
   			}
		}
		return retCount;
	}
	
	/**
	 * 전체 인원 발송. synchronized 에 대해 생각해 볼 필요가 있다. 많은 사람들이 동시에 접속하면서 경매가 진행중일 경우 자원 경합이나 로스가 일어날 수 있다. 일단 샘플이니 넘어간다. 
	 * @param message
	 */
	private synchronized void serveAnyMessage(BidMessage message) {
		//경쟁상태. 응찰중인 인원
		bidMessage.setCompetePeopleNum(getcompetePeopleNum());
		LOGGER.debug("@@@@@@@@ serveAnyMessage BidMessage =" + message);
		
		for (Session session : sessionList) {
            try {
    			LOGGER.debug("@@@@@@@@ serveAnyMessage session.getId() =" + session.getId() +"|WEBSOCKET_USER_INFO =" + session.getUserProperties().get("WEBSOCKET_USER_INFO"));
    			
    			BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
    			message.setBidId(vo.getUserId());
    			message.setUserName(vo.getUserName());
    			Basic basic = session.getBasicRemote();
                basic.sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		message.setBidId("");//BidMessage는 전역변수이므로 사용 후 해당 값 초기화
	}	

	/**
	 * 특정 인원에게만 발송
	 * @param reqSession
	 * @param message
	 */
	private synchronized void serveOneMessage(Session reqSession, BidMessage message) {
		LOGGER.debug("@@@@@@@@ serveOneMessage message =" + message);
        try {
        	BidderVO vo = (BidderVO)reqSession.getUserProperties().get("WEBSOCKET_USER_INFO");
			message.setBidId(vo.getUserId());
			message.setUserName(vo.getUserName());
        	reqSession.getBasicRemote().sendObject(message);
        	message.setBidId("");//BidMessage는 전역변수이므로 사용 후 해당 값 초기화
        } catch (Exception e) {
            e.printStackTrace();
        }
	}	
	
	
}