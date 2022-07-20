package org.zerock.myapp;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class AppTest {
	
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
	} // beforeAll
	
	
	@BeforeEach
	void beforeEach() {
		log.trace("beforeEach() invoked.");
		
	} // beforeEach
	
	
//	@Disabled
	@Test
	@Order(2)
	@DisplayName("contextLoads1")
	@Timeout(value=1000, unit=TimeUnit.MILLISECONDS)
	void contextLoads1() {
		log.trace("contextLoads1() invoked.");
		
		
	} // contextLoads1
	
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("contextLoads2")
	@Timeout(value=1000, unit=TimeUnit.MILLISECONDS)
	void contextLoads2() {
		log.trace("contextLoads2() invoked.");
		
		
	} // contextLoads2
	
	
	@AfterEach
	void afterEach() {
		log.trace("afterEach() invoked.");
		
	} // afterEach
	
	
	@AfterAll
	void afterAll() {
		log.trace("afterAll() invoked.");
		
	} // afterAll
	
} // end class
