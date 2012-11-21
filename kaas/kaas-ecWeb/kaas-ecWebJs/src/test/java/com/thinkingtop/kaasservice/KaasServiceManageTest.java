package com.thinkingtop.kaasservice;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

public class KaasServiceManageTest {
	static Logger logger=Logger.getLogger(KaasServiceManageTest.class);
	private KaasServiceManage kaasServiceManage;
	@Test
	public void test() {
		kaasServiceManage = new KaasServiceManage();
		System.out.println("----------------");
		kaasServiceManage.getExclusiveKeyService();
		//KaasService kaasService = new KaasService();
		/*ExclusiveKeyService eks = kaasService.getExclusiveKeyServicePort();
		List<String> a = eks.test("afdsa");
		logger.info("test----------:"+eks.test("afdsa"));*/
	}
}
