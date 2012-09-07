package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceImpl;


public class ExclusiveKeyManageTest {

	@Test
	public void teatAdd(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ExclusiveKeyManage service = (ExclusiveKeyManage)ctx.getBean("exclusiveKeyManage");
		KebsiteManage serviceKebsiteManage = (KebsiteManage)ctx.getBean("kebsiteManage");
		ExclusiveKeyServiceImpl eksi = (ExclusiveKeyServiceImpl)ctx.getBean("exclusiveKeyServiceImpl");
		System.out.println(service.getClass());
		ExclusiveKey ek = new ExclusiveKey();
		ek.setActivation(true);
		String str = eksi.getAPIKey().toString();
		System.out.println(str);
		ek.setKeyString(str);
		Kebsite kebsite = serviceKebsiteManage.getKebsite("jingdong");
		ek.setKebsite(kebsite);
		service.add(ek);
		ctx.destroy();
	}
	
	@Test
	public void testIsHold() {

		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ExclusiveKeyManage service = (ExclusiveKeyManage)ctx.getBean("exclusiveKeyManage");
		System.out.println(service.getClass());
		boolean ish = service.isHold(new StringBuffer("NOk4OPyDZ8vNrJa1FlO3#46l0scN4SgKcLaf39UT6R_48U_rAhr8i3gEJ6nm8k7Qm9rtQ_B4TKcjpYy"));
		System.out.println(ish);
		ctx.destroy();
	}
	
	@Test
	public void testIsHold2() {
		/*ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ExclusiveKeyManage service = (ExclusiveKeyManage)ctx.getBean("exclusiveKeyManage");
		System.out.println(service.getClass());
		boolean ish = service.isHold("京东","NOk4OPyDZ8vNrJa1FlO3#46l0scN4SgKcLaf39UT6R_48U_rAhr8i3gEJ6nm8k7Qm9rtQ_B4TKcjpYy");
		System.out.println(ish);
		ctx.destroy();*/
	}
	
	@Test
	public void testIsActivation() {
		/*ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ExclusiveKeyManage service = (ExclusiveKeyManage)ctx.getBean("exclusiveKeyManage");
		System.out.println(service.getClass());
		boolean ish = service.isActivation("NOk4OPyDZ8vNrJa1FlO3#46l0scN4SgKcLaf39UT6R_48U_rAhr8i3gEJ6nm8k7Qm9rtQ_B4TKcjpYy");
		System.out.println(ish);
		ctx.destroy();*/
	}

}
