package com.thinkingtop.kaasservice;

import org.junit.Assert;
import org.junit.Test;

public class KaasServiceManageTest {

	@Test
	public void test() {
		String ecommerceName = "jingdong";
		String apiKey = "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ";
		String endUser = "liming";
		String scheme = "scheme1";
		int outputItemsNum = 2;
		int outputQuantitye = 3;
		int st = KaasServiceManage.getState(ecommerceName, apiKey);
		//Assert.assertEquals(2, st);
		
	}

}
