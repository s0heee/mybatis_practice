package org.zerock.myapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class SqlSessionTests {
   
   private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
   private SqlSessionFactory sqlSessionFactory;
   
   
   @BeforeAll
   void beforeAll() throws IOException {
      log.trace("beforeAll() invoked.");
      
      String myBatisConfigXml = "mybatis-config.xml";
      
      @Cleanup
      InputStream is = Resources.getResourceAsStream(myBatisConfigXml);
      Objects.requireNonNull(is);
      
      this.sqlSessionFactory = builder.build(is);
      log.trace("\t+ this.sqlSessionFactory: {}", this.sqlSessionFactory);
      
   } // beforeAll
   
   
//   @Disabled   // Test 메소드 비활성화
   @Test
   @Order(1)
   @DisplayName("2. selectAllBoards")
   @Timeout(value=10, unit=TimeUnit.SECONDS)   // 타임아웃은 default 10초로 시작
   void selectAllBoards() {
      log.trace("selectAllBoards() invoked.");
      
      SqlSession sqlSession = this.sqlSessionFactory.openSession();
      
      try(sqlSession) {
         
         
//         String sql = "SELECT * FROM tbl_board";
//         List<BoardVO> list = sqlSession.<BoardVO>selectList(sql);   // selectOne - 한개 반환 selectList - 여러개 반환  select - 결과셋 직접 핸들링, 잘 안씀
                                                      // sqlSession.<BoardVO>selectList( sql(사용할문장)과 일치하는 unique한 식별자 );
         
         // mybatis가 요구하는 규칙 ( 수행시킬 SQL문장을 지정하는 방식 ) :
         // (1) 각 SQL Mapper XML파일의 namespace 속성마다 고유한 값을 가져야 한다.
         // (2) 각 SQL Mapper XML파일안에 저장된 각 SQL 태그마다 id값은 일반적으로 이 SQL을 수행할 메소드의 이름과 일치시킨다.
         //      그러나 본질적인 의미는 개발자 마음대로 짓되, 고유하게 지어라.
         String namespace = "TTTTTTT";
         String sqlId = "SELECT_1";
         String sql = namespace + "." + sqlId;   // Unique Identify
         List<BoardVO> list = sqlSession.<BoardVO>selectList(sql);
         
         
         assertNotNull(list);
//         log.info("\t+ list: {}", list);
         list.forEach(e -> log.info(e));
         
      } // try-with-resources
      
   } // selectAllBoards
   

} // end class