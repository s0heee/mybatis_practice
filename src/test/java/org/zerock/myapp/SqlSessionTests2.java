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
import org.zerock.myapp.domain.CutomerVO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlSessionTests2 {
	
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
		
	}// beforeAll()
	
	
	@Test
	@Order(1)
	@DisplayName("1. selectAllCustomer")
	@Timeout(unit = TimeUnit.SECONDS, value = 10)
	void selectAllCustomer() {
		log.trace("\t+ selectAllCustomer() invoked");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try(sqlSession){
			String namespace = "Customer";
			String sqlId = "selectAllCustomer";
			String sql = namespace + "." + sqlId;
			
			List<CutomerVO> list = sqlSession.selectList(sql);
			Objects.requireNonNull(list);
			
			list.forEach(e -> log.info(e));
			
		}// try-with-resource
	}// selectAllCustomer()
	
}// end class
