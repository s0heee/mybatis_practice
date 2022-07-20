package org.zerock.myapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
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
	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder;
	
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked");
		
		String config = "mybatis-config.xml";
		@Cleanup
		InputStream is = Resources.getResourceAsStream(config);
		
		this.sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
		Objects.requireNonNull(this.sqlSessionFactoryBuilder);
		log.info("\t+ this.sqlSessionFactoryBuilder = {}", this.sqlSessionFactoryBuilder);
	
		this.sqlSessionFactory = this.sqlSessionFactoryBuilder.build(is);
		assertNotNull(this.sqlSessionFactory);
		log.info("\t+ this.sqlSessionFactory = {}", this.sqlSessionFactory);
		
		
	}//beforeAll()
	
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("contextLoads")
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void contextLoads() {
		log.trace("contextLoads2() invoked");
		
	}// contextLoads2()
	
}// end class



































