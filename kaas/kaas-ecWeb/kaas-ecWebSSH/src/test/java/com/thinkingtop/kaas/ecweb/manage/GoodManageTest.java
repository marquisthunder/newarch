package com.thinkingtop.kaas.ecweb.manage;

import static org.junit.Assert.*;
import javassist.bytecode.stackmap.TypeData.ArrayElement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.ecweb.model.Good;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class GoodManageTest {
	
	@Autowired
	private GoodManage goodManage;

	@Test
	public void testGetGood() {
		Good good = goodManage.getGood(4);
		System.out.println(good.getGoods_id() + "  -  " + good.getGoods_name()+ "  -  " + good.getGoods_number()+ "  -  " + good.getGoods_weight()+ "  -  " 
				+ good.getMarket_price()+ "  -  " + good.getShop_price()+ "  -  " +  good.getPromote_price() + "  -  " + good.getPromote_start_date() + "  -  " 
				+ good.getPromote_end_date()+ "  -  " + good.getGoods_brief()+ "  -  " + good.getGoods_desc()+ "  -  " + good.getIs_real()+ "  -  " 
				+ good.getAdd_time()+ "  -  " +good.getBin()[0]);
	}

}
