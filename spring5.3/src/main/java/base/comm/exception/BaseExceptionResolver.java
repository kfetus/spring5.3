package base.comm.exception;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 기본적인 Exception 처리. 여기서 처리 안되면 프레임웍 차원 및 web.xml 설정에 의해 error.jsp로 에러 처리된다.
 * @author USER
 *
 */
public class BaseExceptionResolver extends AbstractHandlerExceptionResolver  {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionResolver.class);
	
	@Value("#{errorCode['system.runtime']}")
	private String systemRuntimeErrorCode;
	
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,	Exception exception) {
		
		LOGGER.debug("@@@@@@@@@@@@@ BaseExceptionResolver.doResolveException exception=" + exception);
		exception.printStackTrace();
		LOGGER.debug("@@@@@@@@@@@@@");

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			LOGGER.debug("@@@@@@@@@@@@@ request.getHeader(\"accept\")=" + request.getHeader("accept"));
			String accept = request.getContentType();
			LOGGER.debug("@@@@@@@@@@@@@ accept=" + accept);	

			if ("application/json".equals(accept)) {

				//response status code = 500 셋팅. ajax 일 경우 error 로 리턴받아 처리 됨
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

				HashMap<String, Object> errorData = new HashMap<>();
				errorData.put("RESMSG", exception.getMessage());
				errorData.put("RESCODE", systemRuntimeErrorCode);

				String jsonStr = objectMapper.writeValueAsString(errorData);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonStr);
				return new ModelAndView();
			} else {
				ModelAndView mv = new ModelAndView();
				mv.addObject("exception", exception);
				if(exception instanceof BaseException ) {
					mv.setViewName("common/exception/bizException");
				} else {
					mv.setViewName("common/exception/baseError");
				}
				return mv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
