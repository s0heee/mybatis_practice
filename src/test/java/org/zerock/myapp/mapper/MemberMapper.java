package org.zerock.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.MemberVO;


public interface MemberMapper {
	
	
	@Select("SELECT /*+ index_desc(tbl_member) */ * FROM tbl_member")
	public abstract List<MemberVO> selectAllMembers();
	
	@Select("SELECT * FROM tbl_member WHERE userid = #{TheUserId}")
	public abstract MemberVO selectMember(@Param("TheUserId") String userid);

} // end interface
