package org.zerock.myapp.mapper;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MapperInterfacesWithNoConfigXmlTests {

	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private SqlSessionFactory sqlSessionFactory;
	
	
	
	@BeforeAll()
	void beforeAll() {
		log.debug("beforeAll() invoked.");
		
		//-----------------------------------------------------//
		
		HikariConfig hikariConfig = new HikariConfig();
		
//		hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		
//		hikariConfig.setJdbcUrl("jdbc:oracle:thin:@atp20191201_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP");
		
		hikariConfig.setUsername("ADMIN");
		hikariConfig.setPassword("Oracle1234567");
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setConnectionTimeout(1000);
		hikariConfig.setDataSourceJNDI("jdbc/HikariCP");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		Objects.requireNonNull(hikariDataSource);
		log.info("\t+ 1. dataSource: " + hikariDataSource);
		
		//-----------------------------------------------------//
		
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		log.info("\t+ 2. transactionFactory: " + transactionFactory);
		
		//-----------------------------------------------------//
		
		Environment env = new Environment("development", transactionFactory, hikariDataSource);
		log.info("\t+ 3. env: " + env);
		
		//-----------------------------------------------------//
		
		Configuration mybatisConfig = new Configuration(env);
		log.info("\t+ 4. mybatisConfig: " + mybatisConfig);
		
		mybatisConfig.addMapper(BoardMapper.class);
		mybatisConfig.addMapper(MemberMapper.class);
	
		
		//-----------------------------------------------------//
		
		this.sqlSessionFactory = builder.build(mybatisConfig);
		Objects.requireNonNull(this.sqlSessionFactory);
		
		log.info("\t+ 5. sqlSessionFactory: " + this.sqlSessionFactory);
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
		
		try (sqlSession) {
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			log.info("\t+ mapper: " + mapper);
			
			Objects.requireNonNull(mapper);
			
			List<BoardVO> boards = mapper.selectAllBoards();
			boards.forEach(log::info);
		} // try-with-resources
	} // selectAllBoards
	
	
	@AfterAll
	public void afterAll() {
		log.debug("afterAll() invoked.");
		
	} // tearDown

} // end class
