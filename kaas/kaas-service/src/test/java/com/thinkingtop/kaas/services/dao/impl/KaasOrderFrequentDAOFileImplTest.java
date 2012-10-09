package com.thinkingtop.kaas.services.dao.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thinkingtop.kaas.services.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.dao.implfile.KaasOrderFrequentDAOFileImpl;
import com.thinkingtop.kaas.services.model.KaasOrderFrequent;

public class KaasOrderFrequentDAOFileImplTest {

	@Test
	public void testSubmit() {
		KaasOrderFrequent of = new KaasOrderFrequent();
		String fs = "12,2,23,1";
        of.setCombination(fs);
        of.setFrequent(212);
        of.setItemNum(fs.split(",").length);
        of.setOfType("all");
        KaasOrderFrequentDAO mof = new KaasOrderFrequentDAOFileImpl();
        mof.submit(of);
        of = new KaasOrderFrequent("1,21,23,2",11,4,"all");
        mof.submit(of);
        //mof.submit();
		
	}

}
