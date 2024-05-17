package base.comm.exception;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseExceptionResolver extends AbstractHandlerExceptionResolver  {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionResolver.class);
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,	Exception exception) {
		
		LOGGER.debug("@@@@@@@@@@@@@ BaseExceptionResolver.doResolveException exception=" + exception);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			String accept = request.getHeader("accept");
			//response status code = 400 셋팅. ajax 일 경우 error 로 리턴받아 처리 됨
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			if (accept.equals("application/json")) {
				HashMap<String, Object> errorData = new HashMap<>();
				errorData.put("RESMSG", exception.getMessage());
				errorData.put("RESCODE", "9999");

				String jsonStr = objectMapper.writeValueAsString(errorData);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonStr);
				return new ModelAndView();
			} else {
				if(exception instanceof BaseException ) {
					return new ModelAndView("common/exception/bizException");
				} else {
					return new ModelAndView("common/exception/bizException");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
