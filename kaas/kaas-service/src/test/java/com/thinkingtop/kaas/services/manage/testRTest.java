package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class testRTest {

	@Test
	public void testRunIt() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		testR service = (testR)ctx.getBean("testR");
		System.out.println(service.getClass());
		service.runIt();
		ctx.destroy();
	}

}
