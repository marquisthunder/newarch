package com.thinkingtop.kaasservice;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class KaasServiceTest {
	static Logger logger=Logger.getLogger(KaasServiceTest.class);
	@Autowired
	private KaasService kaasService;
	@Test
	public void test() {
		ExclusiveKeyService eks = kaasService.getExclusiveKeyServicePort();
		List<String> a = eks.test("afdsa");
		logger.info("test----------:"+eks.test("afdsa"));
	}
}
