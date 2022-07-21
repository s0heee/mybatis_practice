package org.zerock.myapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class SqlSessionFactoryTests {

	private SqlSessionFactory sqlSessionFactory;
	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();		//builder를 얻음
	
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked");
		
//		mybatis설정 파일명을 변수에 저장
		String config = "mybatis-config.xml";
		
		@Cleanup
		InputStream is = Resources.getResourceAsStream(config);
	
//		공장을 만듦
		this.sqlSessionFactory = this.sqlSessionFactoryBuilder.build(is);
		assertNotNull(this.sqlSessionFactory);
		log.info("\t+ this.sqlSessionFactory = {}", this.sqlSessionFactory);
		
	}//beforeAll()
	
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("getSqlSession")
	@Timeout(value=2000, unit=TimeUnit.MILLISECONDS)
	void getSqlSession() throws Exception {
		log.trace("getSqlSession() invoked");
		
//		SqlSessionFactory 객체로부터 SqlSession 객체를 얻어내자
//		SqlSession은 Closeable인터페이스를 구현하는 클래스므로 꼭 닫아줘야 한다! 
		@Cleanup("close")	//자원닫기 방법#1
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		Objects.requireNonNull(sqlSession);
		log.info("\t+ sqlSession: {}", sqlSession);
		
		try (sqlSession) {		//SQL 문장처리를 프레임워크에 의뢰
			
		}// try-with-resources	//자원닫기 방법#2
		
//		sqlSession.close();		//자원닫기 방법#3
		
	}// getSqlSession()
	
	
}// end class



































