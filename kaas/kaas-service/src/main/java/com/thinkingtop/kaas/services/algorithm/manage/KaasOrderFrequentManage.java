package com.thinkingtop.kaas.services.algorithm.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.impl.KaasOrderFrequentDAOImpl;
import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
@Component("kaasOrderFrequentManage")
public class KaasOrderFrequentManage {
	private KaasOrderFrequentDAOImpl kaasOrderFrequentDAOImpl;
	public void add(KaasOrderFrequent of){
		kaasOrderFrequentDAOImpl.submit(of);
	}
	public KaasOrderFrequentDAOImpl getKaasOrderFrequentDAOImpl() {
		return kaasOrderFrequentDAOImpl;
	}
	@Resource(name="kaasOrderFrequentDAOImpl")
	public void setKaasOrderFrequentDAOImpl(
			KaasOrderFrequentDAOImpl kaasOrderFrequentDAOImpl) {
		this.kaasOrderFrequentDAOImpl = kaasOrderFrequentDAOImpl;
	}
}
