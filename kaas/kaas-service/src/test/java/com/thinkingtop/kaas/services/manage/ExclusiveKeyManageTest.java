package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.service.util.BeforeTest;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceImpl;
import com.thinkingtop.kaas.services.util.APIKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class ExclusiveKeyManageTest {
	@Autowired
	private ExclusiveKeyManage exclusiveKeyManage;
	
	@Autowired
	private KebsiteManage kebsiteManage;
	
	@Autowired
	private ExclusiveKeyServiceImpl exclusiveKeyServiceImpl;
	
	@Autowired
	private APIKey apiKey;
	
    @Before
    public void init() {
    	BeforeTest.init();
    }
	@Test
	public void teatAdd(){
		ExclusiveKey ek = new ExclusiveKey();
		ek.setState(2);
		String str = apiKey.getAPIKey().toString();
		ek.setKeyString(str);
		Kebsite kebsite = kebsiteManage.getKebsite("jingdong");
		ek.setKebsite(kebsite);
		exclusiveKeyManage.add(ek);
		
		Assert.assertEquals(true, exclusiveKeyManage.isHold(new StringBuffer(str)));
	}
	
	@Test
	public void testIsHold() {
		boolean ish = exclusiveKeyManage.isHold(new StringBuffer("an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ"));
		Assert.assertEquals(true, ish);
		boolean ish2 = exclusiveKeyManage.isHold(new StringBuffer("NOk4OPyDZ8vNrJa1FlO3#46l0scN4SgKcLaf39UT6R_48U_rAhr8i3gEJ6nm8k7Qm9rtQ_B4TKcjpYy"));
		Assert.assertEquals(false, ish2);
	}
	
	@Test
	public void testIsHold2() {
		boolean ish = exclusiveKeyManage.isHold("jingdong","an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ");
		Assert.assertEquals(true, ish);
		boolean ish2 = exclusiveKeyManage.isHold("jingdong","NOk4OPyDZ8vNrJa1FlO3#46l0scN4SgKcLaf39UT6R_48U_rAhr8i3gEJ6nm8k7Qm9rtQ_B4TKcjpYy");
		Assert.assertEquals(false, ish2);
	}
	
	@Test
	public void testIsActivation() {
		boolean ish = exclusiveKeyManage.isActivation("an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ");
		Assert.assertEquals(true, ish);
	}
	
	@Test
	public void testGetExclusiveKey() {
		ExclusiveKey exclusiveKey = exclusiveKeyManage.getExclusiveKey("an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ");
		Assert.assertEquals(2, exclusiveKey.getState());
	}

}
