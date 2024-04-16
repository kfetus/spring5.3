package base.biz.user;

import org.apache.ibatis.annotations.Mapper;

import base.comm.vo.UserVO;

@Mapper
public interface UserInfoMapper {

	public int checkDupIdOne(UserVO vo);
	
	public UserVO selectUserInfoOne(String userNo);

	public int insertUserInfoOne(UserVO vo);
	
	public int updateUserInfoOne(UserVO vo);
	
	public int deleteUserInfoOne(int userNo);
}
