package base.comm.websocket.sockjs;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * sockjs 용 websocket. 웹 브라우저용. java client 프로그래밍으로는 해당 프로토콜을 지원하지 않아서 400 BAD Request만 떨어짐
 * 브라우저에서 해당url/info 로 정보를 받아와서 101 메세지를 보내주면 키값을 만들어서 클라이언트에게 보내고 해당 키값을 기준으로 서버와 클라이언트가 통신하는 쳬계임
 * @author OJH
 *
 */
public class SockJsWebSocketHandler  extends TextWebSocketHandler{

	/**
	 * 1. max 3명 정도만 가격 관리. 1명,2명,3명 이상일 경우 상태값 전송
	 * 2. 일반적으로 3초인데 그건 설정에 따르고 낙찰 되는 시간 정해서 낙찰되면 방 폭파 후 다음 또는 경매 전체 종료
	 * 실제 입찰자 정보 세션으로 관리
	 * 낙찰 됐을 경우 해당 낙찰자와 금액, 차량 정보 저장
	 * 클릭해서 들어올 경우 해당 사용자의 금액만 더해주고, 맥스값은 다륻면 유지. 계속 클릭해서 Max가 넘어가면 해당 금액 표시. 
	 * 권리 표시
	 */
	
	
	HashMap<String,String> bid = new HashMap<String,String>();
	ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
	private int bidMoney = 0;
	private static final Logger LOGGER = LoggerFactory.getLogger(SockJsWebSocketHandler.class);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {	
		LOGGER.debug("Connection Success"+session.getId());
		bid.put(session.getId(), "0");
		users.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload(); //메시지
		bidMoney += 5;
		bid.put(session.getId(), bidMoney+"");
		LOGGER.debug("@@@@@@@@@ bid " + bid);
		
		//내금액: 얼마 맥스값 : 얼마 내가 권리 있음
		if (msg != null) {
			LOGGER.debug("send Message :: " + msg);

			for (int i = 0; i < users.size(); i ++) {
//				((WebSocketSession)users.get(i)).sendMessage(new TextMessage(msg));
				((WebSocketSession)users.get(i)).sendMessage(new TextMessage(bidMoney+""));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.debug( "Connection Close");
		users.remove(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		LOGGER.debug("Session Error", (Exception)exception);
	}
}
