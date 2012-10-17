package com.thinkingtop.kaas.services.algorithm.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AlgorithmPropertiesTest {
	static Logger logger=Logger.getLogger(AlgorithmPropertiesTest.class);
	@Test
	public void test() {
		ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("algorithmbeans.xml");
		AlgorithmProperties algorithmProperties = (AlgorithmProperties) acx.getBean("algorithmProperties");
		logger.info("threadNum------------" + algorithmProperties.getThreadNum());
		acx.destroy();
	}

}
