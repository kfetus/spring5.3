package base.comm.websocket.javax;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConfigurator extends Configurator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfigurator.class);
	
	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		
		HttpSession session = (HttpSession) request.getHttpSession();
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ modifyHandshake ¿¿¿¿¿¿¿¿¿¿¿¿"+session);
		if( session != null) {
			Enumeration<?> sessNames = session.getAttributeNames();
			while (sessNames.hasMoreElements()) {
				String sessName = (String) sessNames.nextElement();
	        	LOGGER.debug("key : " + sessName +";value="+session.getAttribute(sessName));
			}
		}
		//@TODO 기본적으로 session이 null 이 될수 없을텐데... 머 일단 셋팅해주지 않으면 null이네. 로그인을 하지 않았다. 처리 필요
		if( session == null || session.getAttribute("USER_INFO") == null ) {
			config.getUserProperties().put("WEBSOCKET_USER_INFO","");
		} else {
			config.getUserProperties().put("WEBSOCKET_USER_INFO", session.getAttribute("USER_INFO"));
		}
	}

}
