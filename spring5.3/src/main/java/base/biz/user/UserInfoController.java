package base.biz.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import base.comm.util.HttpUtil;
import base.comm.util.SessionManager;
import base.comm.util.StringUtil;
import base.comm.vo.UserVO;

@Controller
public class UserInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private UserInfoServiceImpl userInfoService;

	@Value("#{errorCode['login.infoNullCODE']}")
	private String loginNullErrorCode ;
	
	@Value("#{errorCode['validation.null']}")
	private String validationNullCode;
	
	/**
	 * 회원가입 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/joinForm.do")
	public ModelAndView joinForm(HttpServletRequest req, HttpServletResponse res) {
		LOGGER.debug("####################### joinForm");
		ModelAndView mv = new ModelAndView();


		mv.setViewName("user/joinInfoForm");
		return mv;
	}

	/**
	 * 회원수정 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/userUpdateForm.do")
	public ModelAndView userUpdateForm(HttpServletRequest req, HttpServletResponse res) {
		LOGGER.debug("####################### userUpdateForm");
		ModelAndView mv = new ModelAndView();


		mv.setViewName("user/userUpdateForm");
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/checkDupId.do")
	public Map<String,Object> checkDupIdOne(@RequestBody UserVO vo) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ checkDupIdOne 시작=" + vo.toString());
		Map<String , Object> retMap = new HashMap<String,Object>();

		if( !StringUtils.hasText(vo.getUserId())) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","아이디가 없습니다.");
			return retMap;
		}
		if (!StringUtils.hasText(vo.getUserPass()) ) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","패스워드가 없습니다.");
			return retMap;
		}
		
		int result = userInfoService.checkDupIdOne(vo);
		
		retMap.put("RESCODE","0000");
		retMap.put("RESMSG","");
		retMap.put("RESULT_STATE",result == 0 ? "FALSE" : "TRUE");

		LOGGER.debug("@@@@@@@@@@@ checkDupIdOne 종료"+retMap);
		return retMap;
	}
	
	/**
	 * 
	 * @설명 : 회원 가입 
	 * @param map
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/joinUserOne.do")
	public Map<String,Object> insertUserInfoOne(@RequestBody UserVO vo, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ insertUserInfoOne 시작=" + vo.toString());
		Map<String , Object> retMap = new HashMap<String,Object>();
		
		if( !StringUtils.hasText(vo.getUserId())) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","아이디가 없습니다.");
			return retMap;
		}
		if (!StringUtils.hasText(vo.getUserPass()) ) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","패스워드가 없습니다.");
			return retMap;
		}
		if (!StringUtils.hasText(vo.getUserName()) ) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","이름이 없습니다.");
			return retMap;
		}

		if (!StringUtils.hasText(vo.getHpNo()) ) {
			retMap.put("RESCODE",validationNullCode);
			retMap.put("RESMSG","휴대전화 정보가 없습니다.");
			return retMap;
		}

		vo.setUserIp(HttpUtil.getClientIp(req));
		LOGGER.debug("@@@@@@@@@@@ insertUserInfoOne insert data=" + vo.toString());
		int result = userInfoService.insertUserInfoOne(vo);

		retMap.put("RESCODE","0000");
		retMap.put("RESMSG","");
		retMap.put("RESULT_CNT",result);

		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 종료"+retMap);
		return retMap;
	}

	@ResponseBody
	@RequestMapping(value = "/userInfoOne.do")
	public Map<String,Object> selectUserInfoOne(@RequestBody  HashMap<String,String> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectBoardOne 시작=" + map);
		Map<String , Object> retMap = new HashMap<String,Object>();

		UserVO loginVo = sessionManager.getUserInfo(req);
		if (loginVo == null) {
			retMap.put("RESCODE",loginNullErrorCode);
			retMap.put("RESMSG","로그인 정보가 없습니다.");
//			retMap = loginNullMap;
			return retMap;
		}
		
//		String userNo = map.get("userNo");
		UserVO vo = userInfoService.selectUserInfoOne(String.valueOf(loginVo.getUserNo()));
		vo.setUserId(StringUtil.asteriskName(vo.getUserId()));//ID 변환.
		vo.setUserName(StringUtil.asteriskName(vo.getUserName()));//이름 변환.
		vo.setHpNo(StringUtil.asteriskHP(vo.getHpNo()));//HP 변환
		
		retMap.put("RESCODE","0000");
		retMap.put("RESMSG","");
		retMap.put("RESULT_DATA",vo);

		LOGGER.debug("@@@@@@@@@@@ selectBoardOne 종료"+retMap);
		return retMap;
	}

	
	@ResponseBody
	@RequestMapping(value = "/updateUserInfoOne.do")
	public Map<String,Object> updateUserInfoOne(@RequestBody UserVO vo, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ updateUserInfoOne 시작=" + vo.toString());
		Map<String , Object> retMap = new HashMap<String,Object>();
		
		UserVO loginVo = sessionManager.getUserInfo(req);
		if (loginVo == null) {
			retMap.put("RESCODE",loginNullErrorCode);
			retMap.put("RESMSG","로그인 정보가 없습니다.");
			return retMap;
		}
		vo.setUserNo(loginVo.getUserNo());
		vo.setUserId(loginVo.getUserId());
		vo.setUserIp(HttpUtil.getClientIp(req));
		int result = userInfoService.updateUserInfoOne(vo);

		retMap.put("RESCODE","0000");
		retMap.put("RESMSG","");
		retMap.put("RESULT_CNT",result);

		LOGGER.debug("@@@@@@@@@@@ updateBoardOne 종료"+retMap);
		return retMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteUserInfoOne.do")
	public Map<String,Object> deleteUserInfoOne(@RequestBody  HashMap<String,String> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ deleteUserInfoOne 시작=" + map);
		Map<String , Object> retMap = new HashMap<String,Object>();
		
		UserVO vo = sessionManager.getUserInfo(req);
		if (vo == null) {
			retMap.put("RESCODE",loginNullErrorCode);
			retMap.put("RESMSG","로그인 정보가 없습니다.");
			return retMap;
		}

		int result = userInfoService.deleteUserInfoOne(vo.getUserNo());

		retMap.put("RESCODE","0000");
		retMap.put("RESMSG","");
		retMap.put("RESULT_CNT",result);

		LOGGER.debug("@@@@@@@@@@@ updateBoardOne 종료"+retMap);
		return retMap;
	}
}
