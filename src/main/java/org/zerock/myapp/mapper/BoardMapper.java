package org.zerock.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.BoardVO;

public interface BoardMapper {

	
	@Select("SELECT * FROM tbl_board")
	public abstract List<BoardVO> selectAllBoards();
	
	@Select("SELECT bno, title, content, writer FROM tbl_board WHERE bno = #{bno}")
	public abstract BoardVO selectBoard(@Param("bno") int bno);
	
}// end interface
