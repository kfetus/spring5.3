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

import base.comm.vo.UserVO;

@ServerEndpoint(value = "/auctionWebsocket.do", encoders = MsgEncoder.class, decoders = MsgDecoder.class, configurator = ServerConfigurator.class)
public class JavaxWebSocketEndpointServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JavaxWebSocketEndpointServer.class);
	
	private static List<Session> sessionList = new ArrayList<>();
//	private ConcurrentHashMap<Session, BidMessage> joinList = new ConcurrentHashMap<>();
	
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
		} else {
			try {
				BidMessage bidMessage = new BidMessage();
				bidMessage.setConState("9999");
				bidMessage.setMessage("로그인 정보가 없습니다.");
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
	public void onMessage(Session session, BidMessage message) {
		LOGGER.debug("@@@@@@@@ onMessage session=" + session.getId() + "|" + session.getUserProperties());
		LOGGER.debug("@@@@@@@@ onMessage message=" + message);
		serveMessage(session, message);
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

	private synchronized void serveMessage(Session reqSession, BidMessage message) {
		LOGGER.debug("@@@@@@@@ serveMessage message =" + message);
		for (Session session : sessionList) {
            try {
    			LOGGER.debug("@@@@@@@@ serveMessage session.getId() =" + session.getId());
    			LOGGER.debug("@@@@@@@@ serveMessage WEBSOCKET_USER_INFO =" + session.getUserProperties().get("WEBSOCKET_USER_INFO"));
    			// 내가 보낸 메세지 나한텐 안보내기. 경매에서는 전체 모두 갱신
//    		    if (reqSession.getId().equals(session.getId())) {
//    		        continue; 
//    		    }
                Basic basic = session.getBasicRemote();
                basic.sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}	
	
}