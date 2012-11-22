package com.thinkingtop.kaas.ecweb.action;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.ecweb.model.Good;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class GoodActionTest {
	static Logger logger=Logger.getLogger(GoodActionTest.class);
	@Autowired
	private GoodAction goodAction;

	@Test
	public void testExecute() {
		try {
			goodAction.setId(1);
			goodAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Good good = goodAction.getGood();
		List<Good> goods = goodAction.getGoods();
		logger.info("good --: "+good.getGoods_id() + "  -  " + good.getGoods_name()+ "  -  " + good.getGoods_number()+ "  -  " + good.getGoods_weight()+ "  -  " 
				+ good.getMarket_price()+ "  -  " + good.getShop_price()+ "  -  " +  good.getPromote_price() + "  -  " + good.getPromote_start_date() + "  -  " 
				+ good.getPromote_end_date()+ "  -  " + good.getGoods_brief()+ "  -  " + good.getGoods_desc()+ "  -  " + good.getIs_real()+ "  -  " 
				+ good.getAdd_time()+ "  -  " +good.getBin()[0]);
		int i = 0;
		logger.info("goods size: "+goods.size());
		for(Good g : goods){
			i++;
			logger.info("g: --"+i+"---  :"+g.getGoods_id() );
		}
	}

}
