package com.thinkingtop.kaas.services.algorithm.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.algorithm.dao.impl.KaasOrderFrequentDAOImpl;
import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
@Component("kaasOrderFrequentManage")
public class KaasOrderFrequentManage {
	private KaasOrderFrequentDAO kaasOrderFrequentDAO;
	
	public int add(KaasOrderFrequent of){
		KaasOrderFrequent isof = kaasOrderFrequentDAO.findOneByProperty(of.getCombination());
		if(isof!=null){
			isof.setFrequent(of.getFrequent() + isof.getFrequent());
			update(isof);
			return 1;
		}
		return kaasOrderFrequentDAO.submit(of);
	}
	
	public KaasOrderFrequent getOrderFrequent(String myFreqSet){
		return kaasOrderFrequentDAO.findOneByProperty(myFreqSet);
	}
	
	public KaasOrderFrequent getOrderFrequent(int id){
		return kaasOrderFrequentDAO.getKeyMarsOrderFrequent(id);
	}
	
	public long size() {
		return kaasOrderFrequentDAO.size();
	}
	
	public void deleteAll(){
		kaasOrderFrequentDAO.clearOrderFrequent();
	}

	public KaasOrderFrequentDAO getKaasOrderFrequentDAO() {
		return kaasOrderFrequentDAO;
	}
	
	public void update(KaasOrderFrequent of){
		kaasOrderFrequentDAO.update(of);
	}

	@Resource(name="kaasOrderFrequentDAOImpl")
	public void setKaasOrderFrequentDAO(KaasOrderFrequentDAO kaasOrderFrequentDAO) {
		this.kaasOrderFrequentDAO = kaasOrderFrequentDAO;
	}
}
