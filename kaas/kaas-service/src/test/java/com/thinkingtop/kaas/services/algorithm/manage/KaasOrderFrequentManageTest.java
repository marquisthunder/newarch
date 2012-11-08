package com.thinkingtop.kaas.services.algorithm.manage;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:algorithmbeans.xml")
public class KaasOrderFrequentManageTest {
    static Logger logger=Logger.getLogger(KaasOrderFrequentManageTest.class);
    @Autowired
    private KaasOrderFrequentManage kaasOrderFrequentManage;
	@Test
	public void testAdd() {
		KaasOrderFrequent of = new KaasOrderFrequent("2,3,4,5", 2, 4, "all");
		kaasOrderFrequentManage.add(of);
	}

}
