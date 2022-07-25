package org.zerock.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.UserVO;

public interface UserMapper {

	
	@Select("SELECT userid, userpw, username "
			+ "FROM tbl_user "
			+ "WHERE userid > #{userid} AND username > #{username}")
	public abstract List<UserVO> selectUsers(@Param("userid") String userid, @Param("username")String username);
	
}// end interface
