package base.biz.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.comm.util.SessionManager;
import base.comm.vo.UserVO;

@Controller
@ResponseBody
public class ScheduleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);
	
	@Value("#{errorCode['login.infoNullCODE']}")
	private String loginNullErrorCode ;
	
	@Value("#{errorCode['validation.null']}")
	private String validationNullCode;

	@Value("#{errorCode['success']}")
	private String successCode ;
	
	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private ScheduleServiceImpl scheduleService;
	
	@RequestMapping(value = "/scheduleCalender.do")
	public Map<String,Object> scheduleCalender(@RequestBody  HashMap<String,String> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ scheduleCalender 시작=" + map);
		
		Map<String , Object> retMap = new HashMap<String,Object>();

		UserVO loginVo = sessionManager.getUserInfo(req);
		if (loginVo == null) {
			retMap.put("RESCODE",loginNullErrorCode);
			retMap.put("RESMSG","로그인 정보가 없습니다.");
			return retMap;
		}		
		
		HashMap<String,String> paramMap = new HashMap<String,String>();
		
		String yyyymmdd = map.get("yyyymmdd");

		if( !StringUtils.hasText(yyyymmdd)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date now = new Date();
			yyyymmdd = format.format(now);
		} else {
			yyyymmdd = yyyymmdd.replaceAll("-", "").substring(0, 6) + "01";
		}

		paramMap.put("userNo", String.valueOf(loginVo.getUserNo()));
		paramMap.put("yyyymmdd", yyyymmdd);

		List<HashMap<String,String>> resultList = scheduleService.selectScheduleList(paramMap);

		retMap.put("RESCODE",successCode);
		retMap.put("RESMSG","정상적으로 처리되었습니다");
		retMap.put("RESULT_LIST",resultList);
		retMap.put("YYYYMMDD",yyyymmdd.substring(0, 4)+"-"+yyyymmdd.substring(4, 6));

		LOGGER.debug("@@@@@@@@@@@ scheduleCalender 종료"+ retMap);
		return retMap;
	}
	
	
	@RequestMapping(value = "/scheduleOneDay.do")
	public Map<String,Object> selectScheduleOneDayList(@RequestBody  HashMap<String,String> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectScheduleOneDayList 시작=" + map);
		
		Map<String , Object> retMap = new HashMap<String,Object>();

		UserVO loginVo = sessionManager.getUserInfo(req);
		if (loginVo == null) {
			retMap.put("RESCODE",loginNullErrorCode);
			retMap.put("RESMSG","로그인 정보가 없습니다.");
			return retMap;
		}		
		
		String yyyymmdd = map.get("yyyymmdd");

		if( !StringUtils.hasText(yyyymmdd)) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","조회하려는 날짜를 선택해 주세요.");
			return retMap;
		}

		HashMap<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("yyyymmdd", yyyymmdd);
		paramMap.put("userNo", String.valueOf(loginVo.getUserNo()));
		
		List<HashMap<String,String>> resultList = scheduleService.selectScheduleOneDay(paramMap);

		retMap.put("RESCODE",successCode);
		retMap.put("RESMSG","정상적으로 처리되었습니다");
		retMap.put("RESULT_LIST",resultList);

		LOGGER.debug("@@@@@@@@@@@ selectScheduleOneDayList 종료"+ retMap);
		return retMap;
	}

	
	@RequestMapping(value = "/scheduleUpdate.do")
	public Map<String,Object> scheduleInsert(@RequestBody  HashMap<String,String> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ scheduleUpdate 시작=" + map);
		
		Map<String , Object> retMap = new HashMap<String,Object>();

		UserVO loginVo = sessionManager.getUserInfo(req);
 
		if (loginVo == null) {
			retMap.put("RESCODE",loginNullErrorCode);
			retMap.put("RESMSG","로그인 정보가 없습니다.");
			return retMap;
		}		
		
		String yyyymmdd = map.get("yyyymmdd");
		String strList = map.get("scheduleList");

		if( !StringUtils.hasText(yyyymmdd)) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","변경하려는 날짜를 선택해 주세요.");
			return retMap;
		}

		ObjectMapper om = new ObjectMapper();
		List<HashMap<String,String>> paramList = om.readValue(strList, new TypeReference<List<HashMap<String,String>>>() {});
		LOGGER.debug("@@@@@@@@@@@ scheduleUpdate =" + paramList.size());
		LOGGER.debug("@@@@@@@@@@@ scheduleUpdate =" + paramList);
		
		if(paramList.size() == 0) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","추가하려는 스케쥴을 입력하세요.");
			return retMap;
		}
		
		for ( int i = 0 ; i < paramList.size();i++) {
			paramList.get(i).put("userNo", String.valueOf(loginVo.getUserNo()));
		}

		HashMap<String , Object> paramMap = new HashMap<String,Object>();
		paramMap.put("paramList", paramList);
		paramMap.put("yyyymmdd", yyyymmdd);
		paramMap.put("userNo", loginVo.getUserNo());
		
		int result = scheduleService.insertSchedule(paramMap);

		retMap.put("RESCODE",successCode);
		retMap.put("RESMSG","정상적으로 처리되었습니다");
		retMap.put("RESULT_DATA",result);

		LOGGER.debug("@@@@@@@@@@@ scheduleUpdate 종료"+ retMap);
		return retMap;
	}

}
