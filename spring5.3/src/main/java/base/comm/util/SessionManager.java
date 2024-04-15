package base.comm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import base.comm.vo.UserVO;

@Component
public class SessionManager {

	private static final String USER_INFO = "USER_INFO";

	public void createUserInfo(HttpServletRequest req, UserVO vo) {
		HttpSession session =  req.getSession();
		session.removeAttribute(USER_INFO);
		session.setAttribute(USER_INFO, vo);
		session.setMaxInactiveInterval(600);//10분
	}

	public UserVO getUserInfo(HttpServletRequest req) {
		HttpSession session =  req.getSession();
		UserVO vo = (UserVO)session.getAttribute(USER_INFO);
		return vo;
	}

	public void removeUserInfo(HttpServletRequest req) {
		HttpSession session =  req.getSession();
		session.removeAttribute(USER_INFO);
		session.invalidate();
	}
	
}
