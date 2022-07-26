package org.zerock.myapp.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public final class DynamicSQLsWithConfigXmlTests {
		
	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private SqlSessionFactory sqlSessionFactory;
	
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked");
		
		String mybatisConfigXml = "mybatis-config.xml";
		InputStream is = Resources.getResourceAsStream(mybatisConfigXml);
		
		try(is){
			this.sqlSessionFactory = builder.build(is);
			
			Objects.requireNonNull(this.sqlSessionFactory);
			log.info("\t+ this.sqlSessionFactory: {}", this.sqlSessionFactory);
		}// try-with resource
	}// beforeAll()
	
	
	@Test
	@Order(1)
	@DisplayName("1. findBoardByBno")
	@Timeout(unit = TimeUnit.MINUTES, value = 10)
	public void findBoardByBno() {
		log.trace("findBoardByBno() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: {}", sqlSession);
		
		try(sqlSession){
			Integer bno = 33;
//			Integer bno = null;
			
			String namespace = "BoardMapper";
			String sqlId = "findBoardByBno";
			
			BoardVO board = sqlSession.<BoardVO>selectOne(namespace+"."+sqlId, bno);
		}// try-with-resource
		
		
	}// findBoardByBno()
	
	
	@Test
	@Order(2)
	@DisplayName("2. findBoardByTitle")
	@Timeout(unit = TimeUnit.MINUTES, value = 10)
	public void findBoardByTitle() {
		log.debug("findBoardByTitle() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: {}", sqlSession);
		
		try (sqlSession){
//			String title = "7";
//			String title = null;	//모든레코드를 실행
			String title = "%7%";
			
			String namespace = "BoardMapper";
			String sqlId = "findBoardByTitle";
			
			List<BoardVO> boards = sqlSession.<BoardVO>selectList(namespace+"."+sqlId, title);
			Objects.requireNonNull(boards);
			boards.forEach(e -> log.info(e));
			
		}//try-with-resource
		
	}// findBoardByTitle()
	
	@Test
	@Order(3)
	@DisplayName("3. findBoardByWriter")
	@Timeout(unit = TimeUnit.MINUTES, value = 10)
	public void findBoardByWriter() {
		log.debug("findBoardByWriter() invokd");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: {}", sqlSession);
		
		try(sqlSession){
			String writer = "17";
//			String writer = null;
			
			String namespace = "BoardMapper";
			String sqlId = "findBoardByWriter";
			
			List<BoardVO> boards = sqlSession.<BoardVO>selectList(namespace+"."+sqlId, writer);
			Objects.requireNonNull(boards);
			boards.forEach(e -> log.info(e));
			
		}//try-with-resource
		
	}//findBoardByWriter()
	
	
	@Test
	@Order(4)
	@DisplayName("4. findBoardsByBnoAndTitle")
	@Timeout(unit = TimeUnit.MINUTES, value = 10)
	public void findBoardsByBnoAndTitle() {
		log.debug("findBoardsByBnoAndTitle() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: {}", sqlSession);
		
		try(sqlSession){
			//1case
			Integer bno = 33;
			String title = "TITLE";
			
			//2case
//			Integer bno = null;
//			String title = "33";
			
			//3case
//			Integer bno = null;
//			String title = null;
			
			String namespace = "BoardMapper";
			String sqlId = "findBoardsByBnoAndTitle";
			
			Map<String, Object> params = new HashMap<>();
			params.put("bno", bno);
			params.put("title", title);
			
			List<BoardVO> boards = sqlSession.selectList(namespace+"."+sqlId, params);
			Objects.requireNonNull(boards);
			boards.forEach(e -> log.info(e));
			
		}//try-with-resource
		
		
	}//findBoardsByBnoAndTitle()
	
	
	@Test
	@Order(5)
	@DisplayName("5. findBoardsByBnoOrTitle")
	@Timeout(unit = TimeUnit.MINUTES, value = 10)
	public void findBoardsByBnoOrTitle() {
		log.debug("findBoardsByBnoOrTitle() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ sqlSession: {}", sqlSession);
		
		try(sqlSession){
			
			Map<String, Object> params = new HashMap<>();
			
//			params.put("bno", 33);
			params.put("title", "_32");
			
			String namespace = "BoardMapper";
			String sqlId = "findBoardsByBnoOrTitle";
			
			List<BoardVO> boards = sqlSession.<BoardVO>selectList(namespace+"."+sqlId, params);
			Objects.requireNonNull(boards);
			boards.forEach(e -> log.info(e));
			
			
		}//try-with-resource
		
		
	}// findBoardsBySomeBnos()
	
	
	@Test
	@Order(6)
	@DisplayName("6. findBoardsBySomeBnos")
	@Timeout(unit = TimeUnit.MINUTES, value = 10)
	public void findBoardsBySomeBnos() {
		log.debug("findBoardsBySomeBnos() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try(sqlSession){
			List<Integer> bnoList = Arrays.asList(1,2,3,4,5,6,7);
			log.info("\t+bnoList: {}", bnoList);
			
			String namespace = "BoardMapper";
			String sqlId = "findBoardsBySomeBnos";
			
			List<BoardVO> boards = sqlSession.<BoardVO>selectList(namespace+"."+sqlId, bnoList);
			Objects.requireNonNull(boards);
			boards.forEach(e -> log.info(e));
			
		}//try-with-resource
		
	}// findBoardsBySomeBnos()
	
	
	@AfterAll
	void afterAll() {
		log.debug("afterAll() invoked.");
		
	} // afterAll

} // end class















