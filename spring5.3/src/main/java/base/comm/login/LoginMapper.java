package base.comm.login;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import base.comm.vo.UserVO;

@Mapper
public interface LoginMapper {
	
	public UserVO selectOneUserVo(UserVO vo);
	
	public List<UserVO> selectListUserVo();
	
	public int insertLoginInfoOne(UserVO vo);
}

