package base.comm.websocket.javax;

import java.util.ArrayList;
import java.util.List;

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

@ServerEndpoint(value = "/websocket.do", encoders = MsgEncoder.class, decoders = MsgDecoder.class)
public class JavaxWebSocketEndpointServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JavaxWebSocketEndpointServer.class);
	
	private static List<Session> sessionList = new ArrayList<>();
	
	@OnOpen
	public void connectionOpen(Session session, @PathParam("param") String param) {
		LOGGER.debug("@@@@@@@@ connectionOpen");
		sessionList.add(session);
	}

	@OnMessage
	public void receiveMessage(Session session, String message) {
		String msg = message;
		LOGGER.debug("@@@@@@@@ receiveMessage message =" + message);
		msg = msg + ":REURN";
		LOGGER.debug("@@@@@@@@ SERVER MSG =" + msg);
		broadcast(session, msg);
	}

	@OnClose
	public void connectionClose(Session session) {
		LOGGER.debug("@@@@@@@@ connectionClose");
		sessionList.remove(session);
	}

	@OnError
	public void connectionError(Session session, Throwable t) {
		t.printStackTrace();
	}

	private synchronized void broadcast(Session selfSession, String message) {
		LOGGER.debug("@@@@@@@@ broadcast message =" + message);
		for (Session session : sessionList) {
		    if (selfSession.getId().equals(session.getId())) {
		        continue; // 메시지 보낸 당사자에게는 전송 제외하기
		    }
            Basic basic = session.getBasicRemote();
            try {
                basic.sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}	
	
}