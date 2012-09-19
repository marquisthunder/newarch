package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jolbox.bonecp.BoneCPDataSource;
import com.thinkingtop.kaas.service.util.BeforeTest;
import com.thinkingtop.kaas.services.manage.KebsiteManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class KebsiteManageTest {
	@Autowired
	private KebsiteManage kebsiteManage;
	
    @Before
    public void init() {
    	BeforeTest.init();
    }
	
	@Test
	public void testAdd() {
		Kebsite kebsite = new Kebsite();
		kebsite.setKebsiteName("taobao");
		kebsiteManage.add(kebsite);
		
		Kebsite kebsite2 = kebsiteManage.getKebsite(2);
		Assert.assertEquals("taobao", kebsite2.getKebsiteName());
	}
	
	@Test
	public void testGetKebsite() {
		Kebsite kebsite = kebsiteManage.getKebsite(1);
		Assert.assertEquals("jingdong", kebsite.getKebsiteName());
		
		kebsite = kebsiteManage.getKebsite("jingdong");
		long kebsiteId = kebsite.getId();
		Assert.assertEquals(1, kebsiteId);
	}

}
