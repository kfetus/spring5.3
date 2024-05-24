package base.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import base.comm.login.LoginMapper;
import base.comm.util.crypto.Sha256Crypto;
import base.comm.vo.UserVO;

@Service("userInfoService")
public class UserInfoServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private LoginMapper loginMapper;
	
	public int checkDupIdOne(UserVO vo) throws Exception {
		int cnt = userInfoMapper.checkDupIdOne(vo);
		return cnt;
	}
	
	public int insertUserInfoOne(UserVO vo) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ insertUserInfoOne data=" + vo.toString());
		
		String salt = Sha256Crypto.getSalt();
		vo.setSalt(salt);
		vo.setUserPass(Sha256Crypto.encSah256(vo.getUserPass(), salt));
		
		loginMapper.insertLoginInfoOne(vo);
		if( !StringUtils.hasText(vo.getRegId()) ) {
			vo.setRegId("2");
		}
		int result = userInfoMapper.insertUserInfoOne(vo);
		return result;
	}
	
	public UserVO selectUserInfoOne(String userNo) throws Exception {
		UserVO vo = userInfoMapper.selectUserInfoOne(userNo);
		return vo;
	}

	public int updateUserInfoOne(UserVO vo) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ updateUserInfoOne map=" + vo.toString());
		
		String salt = Sha256Crypto.getSalt();
		vo.setSalt(salt);
		vo.setUserPass(Sha256Crypto.encSah256(vo.getUserPass(), salt));
		loginMapper.updateLoginInfoOne(vo);
		int result = userInfoMapper.updateUserInfoOne(vo);
		return result;
	}
	
	public int deleteUserInfoOne(int userNo) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ deleteUserInfoOne key=" + userNo);
		int result = userInfoMapper.deleteUserInfoOne(userNo);
		return result;
	}
}
