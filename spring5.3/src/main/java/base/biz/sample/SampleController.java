package base.biz.sample;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 기본 테스트용 클래스
 * @author USER
 *
 */
@Controller
public class SampleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private SampleServiceImpl sampleService;

	/**
	 * SockJs 방식의 브라우저용 websocket 테스트 샘플
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sockJsSample.do")
	public String sockJsSample(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("####################### sockJsSample");
		return "websocket/sockJsSample";
	}

	/**
	 * websocket 일반적인 방식(javax.websocket)의 websocket 테스트 샘플
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/javaxWebsocketSample.do")
	public String javaxWebsocketSample(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("####################### javaxWebsocketSample");
		return "websocket/javaxWebsocketSample";
	}
	
	/**
	 * 샘플용 리스트 페이지 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/baseList.do")
	public ModelAndView baseList(@RequestParam HashMap<String,Object> map) throws Exception {
		LOGGER.debug("########## baseList  #############"+map);
		List<HashMap<String,String>> list = sampleService.selectList(map);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list );
		mv.addObject("pageTitle", "게시판 목록" );
		
		mv.setViewName("sample/baseList");
		return mv;
	}
	
	/**
	 * url에 들어온 url 그대로 view로 매핑. 파라미터들 그대로 전달
	 * @param map
	 * @param subPath
	 * @param viewName
	 * @return
	 */
	@RequestMapping("/urlToView/{subPath}/{viewName}")
	public ModelAndView subPathUrlToVies( @RequestParam HashMap<String,Object> map,@PathVariable String subPath,@PathVariable String viewName) {
		LOGGER.debug("########## subPathUrlToVies  #############"+map);
		ModelAndView mv = new ModelAndView();
		String viewFileName = viewName.substring(0, viewName.indexOf("."));
		mv.setViewName(subPath+"/"+viewFileName);
		return mv;
	}
}
