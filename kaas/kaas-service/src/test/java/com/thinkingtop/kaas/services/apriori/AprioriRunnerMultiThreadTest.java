package com.thinkingtop.kaas.services.apriori;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AprioriRunnerMultiThreadTest {

	@Test
	public void test() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		AprioriRunnerMultiThread service = (AprioriRunnerMultiThread)ctx.getBean("aprioriRunnerMultiThread");
		System.out.println(service.getClass());
		service.runIt();
		ctx.destroy();
	}

}
