package base.comm.login;

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

import base.comm.util.SessionManager;
import base.comm.util.StringUtil;
import base.comm.util.crypto.Sha256Crypto;
import base.comm.vo.UserVO;

/**
 * 
 * @author ojh
 *
 */
@Controller
@ResponseBody
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private LoginServiceImpl loginService;
	
	@Autowired
	private JwtComponent jwt;
	
	@Value("#{errorCode['validation.null']}")
	private String validationNullCode;
	
	@Value("#{errorCode['biz.noUser']}")
	private String bizNoUser;

	@Value("#{errorCode['success']}")
	private String successCode;	
	
	@RequestMapping(value = "/restLogin.do")
	public Map<String,Object> restLogin(@RequestBody  UserVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ restLogin 시작="+vo.toString());
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

		UserVO vo2 = loginService.selectOneUserVo(vo);
		LOGGER.debug("@@@@@@@@@@@ restLogin after="+vo2);
		
		if( vo2 == null) {
			retMap.put("RESCODE",bizNoUser);
			retMap.put("RESMSG","사용자가 없습니다.");
			return retMap;
		} else {
			String sha256pass = Sha256Crypto.encSah256(vo.getUserPass(), vo2.getSalt());
			if(vo2.getUserPass().equals(sha256pass)) {
				retMap.put("RESCODE",successCode);
				retMap.put("RESMSG","정상적으로 처리되었습니다.");
//				vo.setUserIp((null != req.getHeader("X-FORWARDED-FOR")) ? req.getHeader("X-FORWARDED-FOR") : req.getRemoteAddr());
				vo2.setUserPass("");
				vo2.setSalt("");
				
				vo2.setUserId(StringUtil.asteriskName(vo2.getUserId()));//ID 변환.
				vo2.setUserName(StringUtil.asteriskName(vo2.getUserName()));//이름 변환.
				vo2.setHpNo(StringUtil.asteriskHP(vo2.getHpNo()));//HP 변환
				
				retMap.put("userInfo", vo2);
				sessionManager.createUserInfo(req, vo2);
				
				//JWT 관련 심플 셋팅. 30분 expire Time 설정
				String token = jwt.makeToken(vo2, jwt.HEADER_KEY);
				
				//이 값을 DB나 Redis에 PK 기준으로 저장해서 차후 Token 갱신때 비교해야 함. 현재는 생략함.  
				String refreshToken = jwt.makeRefreshToken(vo2, jwt.HEADER_KEY);
				
				retMap.put("token", token);
				retMap.put("refreshToken", refreshToken);
			} else {
				retMap.put("RESCODE",bizNoUser);
				retMap.put("RESMSG","사용자가 없습니다.");//패스워드가 없습니다가 맞으나 너무 많은 정보를 줄 필요가 없음. 해킹 우려
				return retMap;
			}
		}

		LOGGER.debug("@@@@@@@@@@@ restLogin 종료"+sessionManager.getUserInfo(req));
		return retMap;
	}
	
	
	@RequestMapping(value = "/restLogOut.do")
	public Map<String,Object> restLogOut(@RequestBody Map<String, Object> map, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ restLogOut 시작="+map);

		sessionManager.removeUserInfo(req);
		LOGGER.debug("@@@@@@@@@@@ restLogOut 종료");
		return map;
	}

	@RequestMapping(value = "/checkUser.do")
	public Map<String,Object> checkUser(HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ checkUser 시작=");
		
		Map<String , Object> retMap = new HashMap<String,Object>();
		retMap.put("RESCODE",successCode);
		retMap.put("RESMSG","정상적으로 처리되었습니다.");
				
		UserVO vo = (UserVO)sessionManager.getUserInfo(req);
		if( vo != null) {
			LOGGER.debug("@@@@@@@@@@@ 로그인 사용자 정보:"+vo.toString());
			retMap.put("loginYn","Y");
		} else {
			retMap.put("loginYn","N");
		}
		retMap.put("userInfo", vo);
		LOGGER.debug("@@@@@@@@@@@ checkUser 종료");
		return retMap;
	}

	
}
