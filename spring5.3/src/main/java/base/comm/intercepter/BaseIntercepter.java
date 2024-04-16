package base.comm.intercepter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author ojh
 *
 */
public class BaseIntercepter implements AsyncHandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseIntercepter.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ intercepter preHandle request.getRequestURI()=" + request.getRequestURI());
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ intercepter preHandle request.getContentType()=" + request.getContentType());
		
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ parameter ######");
        Enumeration<?> en = request.getParameterNames();
        while(en.hasMoreElements()) {
        	String paramKey = (String) en.nextElement();            	
        	LOGGER.debug("key : " + paramKey +";value="+request.getParameter(paramKey));
        }
/*
        LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ Attribute ¿¿¿¿¿¿¿¿¿¿¿¿");
		Enumeration<?> attrNames = request.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = (String) attrNames.nextElement();
        	LOGGER.debug("key : " + attrName +";value="+request.getAttribute(attrName));

		}
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ session ¿¿¿¿¿¿¿¿¿¿¿¿");
		Enumeration<?> sessNames = request.getSession().getAttributeNames();
		while (sessNames.hasMoreElements()) {
			String sessName = (String) attrNames.nextElement();
        	LOGGER.debug("key : " + sessName +";value="+request.getSession().getAttribute(sessName));
		}
*/		
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ intercepter postHandle" + request.getRequestURI());
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ intercepter afterCompletion" + request.getRequestURI());
		
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ intercepter afterConcurrentHandlingStarted" + request.getRequestURI());
	}

}
