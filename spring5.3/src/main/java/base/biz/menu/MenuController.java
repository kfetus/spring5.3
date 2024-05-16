package base.biz.menu;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import base.biz.pms.PmsCotroller;
import base.comm.SystemConstance;
import base.comm.util.SessionManager;
import base.comm.vo.UserVO;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsCotroller.class);
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private MenuServiceImpl menuService;

	@RequestMapping(value = "/menuList.do")
	public ModelAndView menuList(HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> map) throws Exception {
		LOGGER.debug("####################### menuList START");
		
		ModelAndView mv = new ModelAndView();

		UserVO vo = sessionManager.getUserInfo(req);
		mv.setViewName("menu/menuList");
		mv.addObject("userNm", vo.getUserName());
		
		int totalCnt = menuService.selectMenuListCnt(map);
		
		int nowPage = 0;
		int pageListCnt = SystemConstance.DEFAULT_PAGE_LIST_COUNT ;
		int startIdx = 0;
		
		if (totalCnt == 0) {
			mv.addObject("RESCODE", "0000");
			mv.addObject("RESMSG", "데이타 없습니다.");
			mv.addObject("RESULT_TOTAL_CNT", "0");
			return mv;
		} else {
			
			if( !ObjectUtils.isEmpty(map.get("nowPage")) && StringUtils.hasText(String.valueOf(map.get("nowPage")))) {
				nowPage = Integer.parseInt(String.valueOf(map.get("nowPage")));
				if(nowPage > 0) {
					nowPage = nowPage -1;
				}
			}
			if( !ObjectUtils.isEmpty(map.get("pageListCnt")) && StringUtils.hasText(String.valueOf(map.get("pageListCnt")))) {
				pageListCnt = Integer.parseInt(String.valueOf(map.get("pageListCnt")));
			}
			
			startIdx = nowPage * pageListCnt;
			map.put("startIdx", startIdx);
			map.put("pageListCnt", pageListCnt);
			List<HashMap<String, String>> resultList = menuService.selectMenuList(map);

			//JSON 배열 데이터로 넘겨줄때 Json으로 변환하지 않으면 html단에서 키값에 " 가 없어서 JSON.parse() 가 에러남. 
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(resultList);
			mv.addObject("RESCODE", "0000");
			mv.addObject("RESMSG", "");
			mv.addObject("RESULT_LIST", resultList);
			mv.addObject("RESULT_JSON_LIST", jsonStr);
			mv.addObject("RESULT_TOTAL_CNT", totalCnt);
		}
		mv.addObject("nowPage",nowPage);
		mv.addObject("pageListCnt",pageListCnt);
		LOGGER.debug("####################### menuList END");
		return mv;
	}

}
