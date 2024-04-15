package base.comm.websocket;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler  extends TextWebSocketHandler{
	// 로그인중인 유저
	ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

	// 클라이언트가 서버로 연결시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {	
		LOGGER.debug("Connection Success");
		users.add(session);
	}

	// 클라이언트가 Data 전송 시
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload(); //메시지
		
		if (msg != null) {
			LOGGER.debug("send Message :: " + msg);

			for (int i = 0; i < users.size(); i ++) { //모두에게 발송				
				((WebSocketSession)users.get(i)).sendMessage(new TextMessage(msg));
			}
		}
	}

	// 연결 해제될 때
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.debug( "Connection Close");
		users.remove(session);
	}

	// 에러 발생시
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		LOGGER.debug("Session Error", (Exception)exception);
	}
}
