package com.thinkingtop.kaas.services.algorithm.dao.impl;

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
public class KaasOrderFrequentDAOImplTest {
    static Logger logger=Logger.getLogger(KaasOrderFrequentDAOImplTest.class);
    @Autowired
    private KaasOrderFrequentDAOImpl kaasOrderFrequentDAOImpl;
    
	@Test
	public void testSubmitKaasOrderFrequent() {
		/*KaasOrderFrequent of = new KaasOrderFrequent("2,3,4,5", 2, 4, "all");
		kaasOrderFrequentDAOImpl.submit(of);*/
	}

}
