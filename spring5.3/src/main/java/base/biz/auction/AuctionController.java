package base.biz.auction;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/auction")
public class AuctionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionController.class);

	@RequestMapping(value = "/auctionBidMain.do")
	public ModelAndView auctionBidMain(@RequestParam HashMap<String,Object> map) throws Exception {
		LOGGER.debug("########## auctionBidMain  #############"+map);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auction/auctionMain");
		return mv;
	}
	
}
