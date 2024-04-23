package base.comm.websocket.javax;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import base.comm.vo.UserVO;

//Singleton으로 처리하기 위해 추가된 부분 : @Configuration , implements ApplicationContextAware
@Configuration
public class ServerConfigurator extends Configurator implements ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfigurator.class);

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {

		HttpSession session = (HttpSession) request.getHttpSession();
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ modifyHandshake ¿¿¿¿¿¿¿¿¿¿¿¿" + session);
		if (session != null) {
			Enumeration<?> sessNames = session.getAttributeNames();
			while (sessNames.hasMoreElements()) {
				String sessName = (String) sessNames.nextElement();
				LOGGER.debug("key : " + sessName + ";value=" + session.getAttribute(sessName));
			}
		}

		BidderVO bider = new BidderVO("","","");
		
		if (session == null || session.getAttribute("USER_INFO") == null) {
			config.getUserProperties().put("WEBSOCKET_USER_INFO", bider);
		} else {
			UserVO vo = (UserVO) session.getAttribute("USER_INFO");
			bider.setUserId(vo.getUserId());
			bider.setUserName(vo.getUserName());
			bider.setGrade(vo.getGrade());
			config.getUserProperties().put("WEBSOCKET_USER_INFO", bider);
		}
	}

	//Singleton으로 하기위해 추가된 부분: 아래 모두
	private static volatile BeanFactory context;

	@Override
	public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ServerConfigurator.context = applicationContext;
	}
}
