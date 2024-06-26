package test;

import java.net.URI;
import java.util.Scanner;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import base.comm.websocket.javax.BidMessage;
import base.comm.websocket.javax.MsgDecoder;
import base.comm.websocket.javax.MsgEncoder;

@ClientEndpoint(encoders = MsgEncoder.class, decoders = MsgDecoder.class)
public class JavaxWebSocketEndpointClient {

	@OnOpen
	public void onOpen(Session userSession) {
		System.out.println("################# open Websocket");
	}

	@OnClose
	public void onClose() {
		System.out.println("################# close Websocket");
		System.exit(0);
	}

	@OnMessage
	public void sendMessage(BidMessage message) {
		System.out.println("################# sendMessage : " + message.getMessage());
	}
	
	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
	
	public static void main(String[] args) {
		System.out.println("################# START");
		BidMessage bidMessage = new BidMessage();
		//try with resource패턴. try가 알아서 close 호출함
		try (Scanner in = new Scanner(System.in)) {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			Session session = container.connectToServer(JavaxWebSocketEndpointClient.class, new URI("ws://localhost:8080/auctionWebsocket.do"));
			session.getBasicRemote().sendText("안녕하세요 여러분~");

			while(true) {
				String inText = in.nextLine();
				if("qqq".equals(inText)) {
					break;
				} else {
					bidMessage.setMessage(inText);
					session.getBasicRemote().sendObject(bidMessage);
					//session.getBasicRemote().sendText(inText);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	
	
}
