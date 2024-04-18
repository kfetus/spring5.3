package test;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import base.comm.websocket.javax.MsgDecoder;
import base.comm.websocket.javax.MsgEncoder;

@ClientEndpoint(encoders = MsgEncoder.class, decoders = MsgDecoder.class)
public class JavaxWebSocketEndpointClient {

	public JavaxWebSocketEndpointClient() {

	}

	@OnOpen
	public void onOpen(Session userSession) {
		System.out.println("################# open Websocket");
	}

	@OnClose
	public void onClose() {
		System.out.println("################# close Websocket");
	}

	@OnMessage
	public void sendMessage(String message) {
		System.out.println("################# sendMessage : " + message);
	}
	
	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
	
	public static void main(String[] args) {
		System.out.println("################# START");

		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			Session session = container.connectToServer(JavaxWebSocketEndpointClient.class, new URI("ws://localhost:8080/websocket.do"));
			session.getBasicRemote().sendText("가나다라마바사1");
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

//		try {
//			JavaxWebSocketEndpointClient clientEndPoint = new JavaxWebSocketEndpointClient();
//			clientEndPoint.sendMessage("메세지를 첫번째로 보냅니다......");
//			clientEndPoint.sendMessage("메세지를 두번째로 보냅니다......");
//			clientEndPoint.sendMessage("메세지를 세번째로 보냅니다......");
//			clientEndPoint.onClose();
//		} catch (Exception e) {
//			e.getStackTrace();
//		}

	}	
	
}
