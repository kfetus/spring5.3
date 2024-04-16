package base.biz.sample;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private SampleServiceImpl sampleService;

	
	@RequestMapping(value = "/client.do")
	public String getClient(HttpServletRequest request, HttpServletResponse response) {
		return "websocket/client";
	}

	@RequestMapping(value = "/server.do")
	public String getServer(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("#######################");
		System.out.println("$$$$$$$$$$$$$$");
		return "websocket/server";
	}
	
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
	
	
}
