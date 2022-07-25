package org.zerock.myapp.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
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

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class MapperXmlWithConfigiXmlTests {
		
	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private SqlSessionFactory sqlSessionFactory;
	


	@BeforeAll
	void beforeAll() throws IOException {
		log.debug("beforeAll() invoked.");
		
		//-----------------------------------------------------//
		
		//--1st. method by using CLASSPATH.
		String mybatisConfigXml = "mybatis-config.xml";
//		log.info("\t+ url: " + Resources.getResourceURL(mybatisConfigXml));
		
		InputStream is = Resources.getResourceAsStream(mybatisConfigXml);
//		InputStream is = Resources.getUrlAsStream( Resources.getResourceURL(mybatisConfigXml).getPath() );
		
		//-----------------------------------------------------//
		
//		//--2nd. method by using file path.
//		String mybatisConfigXml = "mybatis-config.xml";
//		log.info("\t+ url: " + Resources.getResourceURL(mybatisConfigXml));
//		
//		File f = new File( Resources.getResourceURL(mybatisConfigXml).getPath() );
//		FileInputStream is = new FileInputStream(f);
		
		//-----------------------------------------------------//
		
		try (is) {
			this.sqlSessionFactory = builder.build(is);
			
			Objects.requireNonNull(this.sqlSessionFactory);
			log.info("\t+ sqlSessionFactory: " + this.sqlSessionFactory);
		} // try-with-resources
	} // setup
	

//	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. selectAllBoards")
	@Timeout(unit = TimeUnit.SECONDS, value = 10)
	public void selectAllBoards() {
		log.debug("selectAllBoards() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: " + sqlSession);
		
		try(sqlSession) {
			String namespace = "BoardMapper";
			String sqlId = "selectAllBoards";
			
			List<BoardVO> boards = sqlSession.selectList(namespace+"."+sqlId);
			
			Objects.requireNonNull(boards);
			boards.forEach(log::info);
		} // try-with-resources
	} // selectAllBoards
	
	
//	@Disabled
	@Test
	@Order(4)
	@DisplayName("4. selectBoardByMapperInterface")
	@Timeout(unit = TimeUnit.SECONDS, value = 10)
	public void selectBoardByMapperInterface() {
		log.debug("selectBoardByMapperInterface() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: " + sqlSession);
		
		try(sqlSession) {
			 BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			 log.info("\t+ mapper: " + mapper);
			 
			 Objects.requireNonNull(mapper);
			 
			 BoardVO board = mapper.selectBoard(100);
			 log.info("\t+ board: " + board);
		} // try-with-resources
	} // selectBoardByMapperInterface
	

	@AfterAll
	void afterAll() {
		log.debug("afterAll() invoked.");
		
	} // afterAll

} // end class
