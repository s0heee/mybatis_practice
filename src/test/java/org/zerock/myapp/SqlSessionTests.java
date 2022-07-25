package org.zerock.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.zerock.myapp.domain.UserVO;
import org.zerock.myapp.mapper.BoardMapper;
import org.zerock.myapp.mapper.UserMapper;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlSessionTests {
	
	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private SqlSessionFactory sqlSessionFactory;
	

	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("\t+ beforeAll() invoked");
		
		String mybatis = "mybatis-config.xml";
		
		@Cleanup
		InputStream is = Resources.getResourceAsStream(mybatis);
		Objects.requireNonNull(is);
		
		this.sqlSessionFactory = builder.build(is);
		log.trace("\t+ this.sqlSessionFactory: {}", this.sqlSessionFactory);
		
	}// before All()
	
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. selectAllBoards()")
	@Timeout(unit=TimeUnit.SECONDS, value = 10)
	void selectAllBoards() {
		log.trace("\t+ selectAllBoards() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try(sqlSession){
			
	        // mybatis가 요구하는 규칙 ( 수행시킬 SQL문장을 지정하는 방식 ) :
	        // (1) 각 SQL Mapper XML파일의 namespace 속성마다 고유한 값을 가져야 한다.
	        // (2) 각 SQL Mapper XML파일안에 저장된 각 SQL 태그마다 id값은 일반적으로 이 SQL을 수행할 메소드의 이름과 일치시킨다.
	        //      그러나 본질적인 의미는 개발자 마음대로 짓되, 고유하게 지어라.
			String namespace = "TTT";
			String sqlId = "selectAllBoards";
			String sql = namespace + "." + sqlId;
			
			List<BoardVO> list = sqlSession.selectList(sql);
			Objects.requireNonNull(list);
			
			list.forEach(e -> log.info(e));
			
		}//try-with-resource
		
		
	}// selectAllBoards()
	
	
////	@Disabled
//	@Test
//	@Order(2)
//	@DisplayName("2. selectOneBoards()")
//	@Timeout(unit=TimeUnit.SECONDS, value = 10)
//	void selectOneBoards() {
//		log.trace("\t+ selectOneBoards() invoked");
//		
//		SqlSession sqlSession = this.sqlSessionFactory.openSession();
//		
//		try(sqlSession){
//			
//			String namespace = "TTT";
//			String sqlId = "select_2";
//			String sql = namespace + "." + sqlId;
//			
//			BoardVO vo = sqlSession.selectOne(sql);
//			Objects.requireNonNull(vo);
//			
//			log.info(vo);
//			
//		}//try-with-resource
//	
//	}// selectOneBoards()
	
	
//	@Disabled
	@Test
	@Order(3)
	@DisplayName("3. selectBoardsByTwoCondition()")
	@Timeout(unit=TimeUnit.SECONDS, value = 10)
	void selectBoardsByTwoCondition() {
		log.trace("\t+ selectBoardsByTwoCondition() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession){
			String namespace = "TTT";
			String sqlId = "select_3";
			String sql = namespace + "." + sqlId;
			
			Map<String, Object> params = new HashMap<>();
			params.put("bno", 5);
			params.put("title", "TITLE_2");
			
			List<BoardVO> list = sqlSession.selectList(sql, params);
			Objects.requireNonNull(list);
			
			list.forEach(e -> log.info(e));
					
					
		}//t-w-r
		
	}// selectBoardsByTwoCondition()
	
	
//	@Disabled
	@Test
	@Order(4)
	@DisplayName("4. insertNewUSer()")
	@Timeout(unit=TimeUnit.SECONDS, value = 10)
	void insertNewUser() {
		log.trace("\t+ insertNewUser() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession(false);	//SET AUTOCOMMIT OFF
		
		try (sqlSession){
			
			String namespace = "mappers.UserMapper";
			String sqlId = "insertNewUser";
			String sql = namespace + "." + sqlId;
			
//			바인드변수에 전달할 파라미터 값 생성하면서 한번에 여러개의 레코드를 INSERT함
			for(int i=2; i<10; i++) {
	
				Map<String, Object> params = new HashMap<>();
				params.put("userid", "USER_"+i);
				params.put("userpw", "PASS_"+i);
				params.put("username", "NAME_"+i);
				
				//MAP 객체를 파라미터로 전달하면서 MApped Statement 수행(DML)
				int insertedRows = sqlSession.insert(sql, params);
				log.info("\t+ insertedRows: {}", insertedRows);
				
			}// for
			
			sqlSession.commit();
			log.info("\t+ Commited");		//TCL: ALL or NOTHING
		} catch (Exception e) {
			sqlSession.rollback();
			log.info("\t+ Rolled back");
			
			throw e;
		} // try- catch
		
	}// insertNewUSer()
	
	
//	@Disabled
	@Test
	@Order(5)
	@DisplayName("5. selectAllBoardsByMapperInterface()")
	@Timeout(unit=TimeUnit.SECONDS, value = 10)
	void selectAllBoardsByMapperInterface() {
		log.trace("selectAllBoardsByMapperInterface() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		//Mapper Interface를 이용한 SQL문장처리
		try(sqlSession){
			//step#1. 설정파일에 등록된 Mapper Interface의 구현객체를 획득
			BoardMapper mapper = sqlSession.<BoardMapper>getMapper(BoardMapper.class);
			Objects.requireNonNull(mapper);
			log.info("\t + mapper: {}", mapper);
			
			//step#2. 동적 Proxy 객체를 통해 Mapper 인터페이스에 선언된 메소드 호출1
			List<BoardVO> list = mapper.selectAllBoards();
			Objects.requireNonNull(list);
			list.forEach(e -> log.info(e));
			
			//step#3. 동적 proxy 객체를 통해 Mapper 인터페이스에 선언된 메소드 호출2
			BoardVO vo = mapper.selectBoard(33);
			Objects.requireNonNull(vo);
			log.info(vo);
			
		}// try-with-resource
	
	}// selectAllBoardsByMapperInterface()
	
	
//	@Disabled
	@Test
	@Order(6)
	@DisplayName("6. selectUsers()")
	@Timeout(unit=TimeUnit.SECONDS, value = 10)
	void selectUsers() {
		log.trace("selectUsers() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession){
			UserMapper mapper = sqlSession.<UserMapper>getMapper(UserMapper.class);
			Objects.requireNonNull(mapper);
			log.info("\t+ mapper: {}" , mapper);
			
			List<UserVO> list = mapper.selectUsers("USER_4", "NAME_2");
			Objects.requireNonNull(list);
			
			list.forEach(e-> log.info(e));
			
		}// try-with-resource
		
	}// selectUsers()
	
}// end class



















