package base.comm.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class HttpUtil {

	/**
	 * 현재 알려진 header를 체크해서 프록시를 통해서 온 클라이언트 ip 얻기
	 * @설명 : 
	 * @param req
	 * @return
	 */
	public static String getClientIp(HttpServletRequest req) {
		String ip = "";

		String[] ipHeaderArray = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
				"HTTP_X_FORWARDED_FOR" };

		for(String ipHeader: ipHeaderArray) {
		    ip = req.getHeader(ipHeader);
		    if( StringUtils.hasText(ip) ) {
		    	break;
		    }
		}
		if( !StringUtils.hasText(ip) ) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}
}
