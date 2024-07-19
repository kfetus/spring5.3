package base.biz.sample;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import base.comm.exception.BaseException;
import base.comm.vo.CommonFBVO;

/**
 * 
 * @author ojh
 *
 */
@Controller
@ResponseBody
public class RestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

	@Value("#{errorCode['success']}")
	private String successCode ;
	
	@Value("#{errorCode['biz.defaultException']}")
	private String bizDefaultException;

	@RequestMapping(value = "/sampleRest.do")
	public String sampleRest(HttpServletRequest req, @RequestBody Map<String, Object> map) {
		LOGGER.debug("@@@@@@@@@@@@@@@@@@"+req.getParameterMap());
		LOGGER.debug("@@@@@@@@@@@@@@@@@@"+map);
		
		Enumeration<String> e = req.getParameterNames();
		
		while(e.hasMoreElements()) {
			String key = e.nextElement();
			LOGGER.debug("param key="+key+", value="+req.getParameter(key));	
		}
		
		Enumeration<String> attribute = req.getAttributeNames();
		
		while(attribute.hasMoreElements()) {
			String key2 = attribute.nextElement();
			LOGGER.debug("attribute key="+key2+", value="+req.getAttribute(key2));	
		}

		Enumeration<String> header = req.getHeaderNames();
		
		while(header.hasMoreElements()) {
			String key3 = header.nextElement();
			LOGGER.debug("header key="+key3+", value="+req.getHeader(key3));	
		}

		
		return "sample";
	}
	
	
	@RequestMapping(value = "/restBaseModel.do")
	public Map<String,String> restBaseModel(@RequestBody Map<String, String> map) {
		LOGGER.debug("@@@@@@@@@@@ restBaseModel 시작="+map);
		

		map.put("reply","test");
		LOGGER.debug("@@@@@@@@@@@ restBaseModel 종료");
		return map;
	}

	@RequestMapping(value = "/restBaseResVo.do")
	public CommonFBVO restBaseResVo(@RequestBody Map<String, String> map) {
		LOGGER.debug("@@@@@@@@@@@ restBaseResVo 시작="+map);
		CommonFBVO frontBackVo = new CommonFBVO();
		frontBackVo.setChangeCount("15");
		frontBackVo.setResCode(successCode);
		frontBackVo.setResMsg("이런저런메세지");
		frontBackVo.setResultList(null);
		frontBackVo.setResultSize("0");
		frontBackVo.setResultTotalCnt("1230");
		
		LOGGER.debug("@@@@@@@@@@@ restBaseResVo 종료");
		return frontBackVo;
	}

	
	/**
	 * 
	 * @설명 : 에러 페이지 테스트 중 ControllerAdvice로 처리해도 되나 일단 귀찮음
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/restBaseError.do")
	public Map<String,String> restBaseError(@RequestBody Map<String, String> map) throws Exception{
		LOGGER.debug("@@@@@@@@@@@ restBaseError 시작="+map);
		throw new BaseException("이런 메세지를 넣으세요", bizDefaultException);
	}

}
