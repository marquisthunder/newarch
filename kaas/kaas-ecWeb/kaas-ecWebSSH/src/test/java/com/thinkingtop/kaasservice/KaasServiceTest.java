package com.thinkingtop.kaasservice;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class KaasServiceTest {
	static Logger logger=Logger.getLogger(KaasServiceTest.class);
	@Autowired
	private KaasServiceManage kaasServiceManage;
	@Test
	public void test() {
		System.out.println("----------------");
		kaasServiceManage.getExclusiveKeyService();
		//KaasService kaasService = new KaasService();
		/*ExclusiveKeyService eks = kaasService.getExclusiveKeyServicePort();
		List<String> a = eks.test("afdsa");
		logger.info("test----------:"+eks.test("afdsa"));*/
	}
}
