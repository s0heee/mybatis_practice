package org.zerock.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.domain.BoardVO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class TTTTests {

	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("\t+ beforeAll() invoked");
		
		String myBatisConfigXml = "mybatis-config.xml";
		@Cleanup
		InputStream is = Resources.getResourceAsStream(myBatisConfigXml);
		Objects.requireNonNull(is);
		
		this.sqlSessionFactory = builder.build(is);
		log.trace("\t+ this.sqlSessionFactory: {}", this.sqlSessionFactory);
		
	}//beforeAll()
	
	
	@Test
	@Order(1)
	@DisplayName("1. selectAllBoards")
	@Timeout(value=10, unit=TimeUnit.SECONDS)
	void selectAllBoards() {
		log.trace("selectAllBoards() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession){
			
			String namespce = "TTT";
			String sqlId = "selectAllBoards";
			String sql = namespce + "." + sqlId;
			List<BoardVO> list = sqlSession.selectList(sql);
			
			Objects.requireNonNull(list);
			list.forEach(log::info);
			
		}// try-with resources
		
	}//selectAllBoards()
	
	
	
	
	
}// end class































