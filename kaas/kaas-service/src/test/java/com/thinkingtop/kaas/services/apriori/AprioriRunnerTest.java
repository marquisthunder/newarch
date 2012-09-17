package com.thinkingtop.kaas.services.apriori;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AprioriRunnerTest {

	@Test
	public void aprioriOfflineTest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		AprioriRunner aprioriService = (AprioriRunner)ctx.getBean("aprioriRunner");
		aprioriService.runIt();
		/*aprioriService.process(folderName);
		assert.*/
		ctx.destroy();
	}

}
