package base.biz.auction;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.comm.util.SessionManager;
import base.comm.vo.UserVO;

@Controller
@RequestMapping(value = "/auction")
public class AuctionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionController.class);

	@Autowired
	private SessionManager sessionManager;
	
	@RequestMapping(value = "/auctionBidMain.do")
	public ModelAndView auctionBidMain(@RequestParam HashMap<String,Object> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("########## auctionBidMain  #############"+map);
		
		UserVO vo = sessionManager.getUserInfo(req);
		
		ModelAndView mv = new ModelAndView();
		if(vo != null) {
			mv.addObject("grade", vo.getGrade());
		} else {
			mv.addObject("grade", "O");
		}
		mv.setViewName("auction/auctionMain");
		return mv;
	}
	
}
