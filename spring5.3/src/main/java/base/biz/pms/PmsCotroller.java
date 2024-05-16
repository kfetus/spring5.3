package base.biz.pms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import base.comm.SystemConstance;
import base.comm.util.SessionManager;
import base.comm.vo.UserVO;

@Controller
public class PmsCotroller {

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsCotroller.class);

	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private PmsServiceImpl pmsService;

	/**
	 * 일정관리
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pmsMain.do")
	public ModelAndView pmsMain(HttpServletRequest req, HttpServletResponse res) {
		LOGGER.debug("####################### pmsMain");
		
		ModelAndView mv = new ModelAndView();
		UserVO vo = sessionManager.getUserInfo(req);
		mv.addObject("userNm", vo.getUserName());
		mv.setViewName("pms/pmsMain");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/pmsList.do")
	public Map<String, Object> pmsList(@RequestBody HashMap<String, Object> map,HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ pmsList 시작=" + map);
		Map<String, Object> retMap = new HashMap<String, Object>();
//		LOGGER.debug("@@@@@@@@@@@ pmsList 사용자정보=" + sessionManager.getUserInfo(req));
		
		int totalCnt = pmsService.selectPmsListCnt(map);
		
		int nowPage = 0;
		int pageListCnt = SystemConstance.DEFAULT_PAGE_LIST_COUNT;
		int startIdx = 0;
		
		if (totalCnt == 0) {
			retMap.put("RESCODE", "0000");
			retMap.put("RESMSG", "데이타 없습니다.");
			retMap.put("RESULT_SIZE", "0");
			return retMap;
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
			List<HashMap<String, String>> resultList = pmsService.selectPmsList(map);

			
			retMap.put("RESCODE", "0000");
			retMap.put("RESMSG", "");
			retMap.put("RESULT_SIZE", resultList.size());
			retMap.put("RESULT_LIST", resultList);
			retMap.put("RESULT_TOTAL_CNT", totalCnt);
		}

		LOGGER.debug("@@@@@@@@@@@ pmsList 종료" + retMap);
		return retMap;
	}


	@ResponseBody
	@RequestMapping(value = "/changePmsList.do")
	public Map<String, Object> changePmsList(@RequestBody List<Map<String, String>> list, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ changePmsList 시작=" + list);
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		int resultCount = pmsService.changePmsList(list);
		
		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");
		retMap.put("CHANGE_COUNT", resultCount);

		LOGGER.debug("@@@@@@@@@@@ changePmsList 종료" + retMap);
		return retMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePmsList.do")
	public Map<String, Object> deletePmsList(@RequestBody ArrayList<String> list, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ deletePmsList 시작=" + list);
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		int resultCount = pmsService.deletePmsList(list);
		
		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");
		retMap.put("CHANGE_COUNT", resultCount);

		LOGGER.debug("@@@@@@@@@@@ changePmsList 종료" + retMap);
		return retMap;
	}
	
}
