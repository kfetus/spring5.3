package base.biz.sample;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import base.biz.board.BoardController;

@Controller
public class SampleController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
	
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
}
