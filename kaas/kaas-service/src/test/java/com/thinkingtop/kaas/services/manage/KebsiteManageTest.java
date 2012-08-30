package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.services.manage.KebsiteManage;
import com.thinkingtop.kaas.services.model.Kebsite;

public class KebsiteManageTest {

	@Test
	public void testAdd() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		KebsiteManage service = (KebsiteManage)ctx.getBean("kebsiteManage");
		System.out.println(service.getClass());
		Kebsite kebsite = new Kebsite();
		kebsite.setKebsiteName("京东");
		service.add(kebsite);
		ctx.destroy();
	}

}
