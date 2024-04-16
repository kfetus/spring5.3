package base.comm.login;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.comm.vo.UserVO;

@Service("loginService")
public class LoginServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginMapper loginMapper;

	
	public UserVO selectOneUserVo(UserVO vo) throws Exception {
		UserVO resultVo = loginMapper.selectOneUserVo(vo);
		LOGGER.debug("@@@@@@@@@@@@@ resultVo=" + resultVo);
		return resultVo;
	}

	public List<UserVO> selectListUserVo() throws Exception {
		List<UserVO> resultVoList = loginMapper.selectListUserVo();
		return resultVoList;
	}

}

