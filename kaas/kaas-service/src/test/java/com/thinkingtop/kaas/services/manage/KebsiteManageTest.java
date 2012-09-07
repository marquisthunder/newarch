package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.services.manage.KebsiteManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceImpl;

public class KebsiteManageTest {

	@Test
	public void testAdd() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		KebsiteManage service = (KebsiteManage)ctx.getBean("kebsiteManage");
		System.out.println(service.getClass());
		Kebsite kebsite = new Kebsite();
		kebsite.setKebsiteName("jingdong");
		service.add(kebsite);
		ctx.destroy();
	}
	
	@Test
	public void testGetKebsite() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		KebsiteManage service = (KebsiteManage)ctx.getBean("kebsiteManage");
		System.out.println(service.getClass());
		Kebsite kebsite = service.getKebsite(1);
		System.out.println(kebsite.getKebsiteName());
		kebsite = service.getKebsite("京东");
		System.out.println(kebsite.getId());
		ctx.destroy();
	}

}
