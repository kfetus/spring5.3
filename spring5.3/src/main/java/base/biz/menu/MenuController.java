package base.biz.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import base.comm.SystemConstance;
import base.comm.util.SessionManager;
import base.comm.vo.UserVO;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private MenuServiceImpl menuService;

	@Value("#{errorCode['validation.dup']}")
	private String validationDuplication;
	
	@Value("#{errorCode['noData']}")
	private String noData ;

	@Value("#{errorCode['success']}")
	private String successCode ;
	
	@RequestMapping(value = "/menuList.do")
	public ModelAndView menuList(HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> map) throws Exception {
		LOGGER.debug("####################### menuList START");
		
		ModelAndView mv = new ModelAndView();

		UserVO vo = sessionManager.getUserInfo(req);
//일반 jsp 리턴 방식. 새로고침을 해도 같은 이전 요청 데이터가 유지 됨		
		mv.setViewName("menu/menuList");
//리다이렉트 방식. 이전 post 요청 파라미터들 리셋이 필요할 때. 같은 url을 보내면 무한루프임		mv.setViewName("redirect:/menu/menuDetail.do");
		mv.addObject("userNm", vo.getUserName());
		
		int totalCnt = menuService.selectMenuListCnt(map);
		
		int nowPage = 0;
		int pageListCnt = SystemConstance.DEFAULT_PAGE_LIST_COUNT ;
		int startIdx = 0;
		
		if (totalCnt == 0) {
			mv.addObject("RESCODE", noData);
			mv.addObject("RESMSG", "데이타 없습니다.");
			mv.addObject("RESULT_TOTAL_CNT", "0");
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
			mv.addObject("RESCODE", successCode);
			mv.addObject("RESMSG", "정상적으로 처리되었습니다");
			mv.addObject("RESULT_LIST", resultList);
			mv.addObject("RESULT_JSON_LIST", jsonStr);
			mv.addObject("RESULT_TOTAL_CNT", totalCnt);
		}
		mv.addObject("nowPage",nowPage);
		mv.addObject("pageListCnt",pageListCnt);
		mv.addAllObjects(map);
		LOGGER.debug("####################### menuList END" +mv);
		return mv;
	}

	
	@ResponseBody
	@RequestMapping(value = "/updateMenuList.do")
	public Map<String, Object> updateMenuList(@RequestBody List<Map<String, String>> list, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ updateMenuList 시작=" + list);
		
		UserVO vo = sessionManager.getUserInfo(req);
		Map<String, Object> retMap = new HashMap<String, Object>();
		for(int i = 0 ; i < list.size(); i++) {
			list.get(i).put("REG_ID", vo.getUserNo()+"");
		}
		//multi update는 무조건 1건으로 처리되므로 conut가 의미 없다
		menuService.updateMenuList(list);
		
		retMap.put("RESCODE", successCode);
		retMap.put("RESMSG", "정상적으로 처리되었습니다");
//		retMap.put("CHANGE_COUNT", resultCount);

		LOGGER.debug("@@@@@@@@@@@ updateMenuList 종료" + retMap);
		return retMap;
	}

	@ResponseBody
	@RequestMapping(value = "/saveMenuOne.do")
	public Map<String, Object> saveMenuOne(@RequestBody Map<String, String> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ saveMenuOne 시작=" + map);
		
		UserVO vo = sessionManager.getUserInfo(req);
		Map<String, Object> retMap = new HashMap<String, Object>();

		map.put("REG_ID", vo.getUserNo()+"");

		int resultCount = menuService.saveMenuOne(map);
		if (resultCount != 1) {
			retMap.put("RESCODE", validationDuplication);
			retMap.put("RESMSG", "중복된 데이터가 있습니다.");
		} else {
			retMap.put("RESCODE", successCode);
			retMap.put("RESMSG", "정상적으로 처리되었습니다");
		}
		retMap.put("CHANGE_COUNT", resultCount);

		LOGGER.debug("@@@@@@@@@@@ saveMenuOne 종료" + retMap);
		return retMap;
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/deleteMenuList.do")
	public Map<String, Object> deleteMenuList(@RequestBody List<Map<String,String>> list, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ deleteMenuList 시작=" + list);
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		int resultCount = menuService.deleteMenuList(list);
		
		retMap.put("RESCODE", successCode);
		retMap.put("RESMSG", "정상적으로 처리되었습니다");
		retMap.put("CHANGE_COUNT", resultCount);

		LOGGER.debug("@@@@@@@@@@@ deleteMenuList 종료" + retMap);
		return retMap;
	}

}
