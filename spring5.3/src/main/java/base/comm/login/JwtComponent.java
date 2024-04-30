package base.comm.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import base.comm.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * 아주 기본적인 jwt 셋팅 클래스. bearer 를 Authorization에 셋팅하고 자시고 머 그닥 필수는 아님. 중요한건 키를 가지고 암호화 해서 header 에 셋팅하고 주고받는부분
 * Expire Time을 선언해서 하는 부분. 갱신 하는 부분은 구현 안함
 * @author USER
 *
 */
@Component
public class JwtComponent {

	private final Logger LOGGER = LoggerFactory.getLogger(JwtComponent.class);

	@Value("#{controlsite['jwt.baseKey']}")
	private String JWT_KEY ;
	
	public final String HEADER_KEY = "Authorization";

	/**
	 * 
	 * @param userVO  = UserVO
	 * @param subject = "Authorization"
	 * @return
	 */
	public String makeToken(UserVO userVO, String subject) {

		Map<String, Object> claims = new HashMap<>();

		claims.put("id", userVO.getUserId());
		claims.put("name", userVO.getUserName());
		claims.put("grade", userVO.getGrade());
		claims.put("hpno", userVO.getHpNo());

		LOGGER.debug("######secret=" + JWT_KEY);

		// 무작위 키값 만들기. 서버가 여러대면 서버마다 키값이 틀리다. 단독 서버일 경우에도 서버가 Restart 한다면 해당 값이 날라간다.
		// SecretKey key = Jwts.SIG.HS256.key().build();

		return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1800000))
				.signWith(Keys.hmacShaKeyFor(JWT_KEY.getBytes()), Jwts.SIG.HS512).compact();

	}

	public boolean verifyJWT(String token) {
		boolean state = true;
		LOGGER.debug("###### verifyJWT token=" + token);
		Jws<Claims> claimJwt = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(JWT_KEY.getBytes())).build()
				.parseSignedClaims(token);
		Claims claims = claimJwt.getPayload();
		LOGGER.debug("###### verifyJWT parsing claims=" + claims);
		if( !StringUtils.hasText((String)claims.get("id"))) {
			state = false;
		}
		return state;
	}

	public static void main(String[] args) {
		System.out.println("###### START ######");

		JwtComponent jwt = new JwtComponent();
		
		jwt.JWT_KEY="TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT";
//		jwt.JWT_KEY="255bytemorekeylength-so-no-reason-data-domain-version-target98761&&^^%**@";
		UserVO vo = new UserVO();
		vo.setUserId("TESXT");
		vo.setUserName("아무개");
		vo.setGrade("M");
		vo.setHpNo("0101234567");

		String token = jwt.makeToken(vo, jwt.HEADER_KEY);
		System.out.println("###### jwt token="+token);
		System.out.println("###### verifyJWT(token)="+jwt.verifyJWT(token));
//		System.out.println("###### verifyJWT(token)="+jwt.verifyJWT("eyJhbGciOiJIUzUxMiJ9.eyJncmFkZSI6Ik0iLCJuYW1lIjoi7Jik7Yag7KSR6rOg7LCoIiwiaHBubyI6IjAxMDU1NTU0NDQ0IiwiaWQiOiJhdXRvIiwic3ViIjoiQXV0aG9yaXphdGlvbiIsImlhdCI6MTcxNDM4MDkxMywiZXhwIjoxNzE0MzgyNzEzfQ.qTROxPZ1hVW9UNVo1JOdp2FaJjb0SYwRRoxrKqYPkVtQK8r6p3HWr0DfDyLMZy0sMXn3Us8oKWu6SZj2tHr-MQ"));
		
		
		System.out.println("###### END ######");
	}

}
