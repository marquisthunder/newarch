package com.thinkingtop.kaas.services.algorithm.manage;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.model.KaasRule;

@Component("kaasRuleManage")
public class KaasRuleManage {
	static Logger logger=Logger.getLogger(KaasRuleManage.class);
	private KaasRuleDAO kaasRuleDAO;
	public int add(KaasRule r){
		KaasRule rule = kaasRuleDAO.getRule(r.getProducts(), r.getRecommendation());
		if(rule!=null){
			logger.info("update-------");
			rule.setConfidence(r.getConfidence());
			logger.info("r id : --" + r.getId());
			logger.info("r Products : --" + r.getProducts());
			logger.info("r Recommendation : --" + r.getRecommendation());
			return kaasRuleDAO.update(rule);
		}
		return kaasRuleDAO.submit(r);
	}

	public boolean isHold(String products,String recommendation){
		return kaasRuleDAO.isHold(products, recommendation);
	}
	
	public int update(KaasRule r){
		return kaasRuleDAO.update(r);
	}
	
	public void deleteAll(){
		kaasRuleDAO.clearMarsRuleAll();
	}
	
	public KaasRule getRule(int id){
		return kaasRuleDAO.getRule(id);
	}
	
	public KaasRuleDAO getKaasRuleDAO() {
		return kaasRuleDAO;
	}

	@Resource(name="kaasRuleDAOImpl")
	public void setKaasRuleDAO(KaasRuleDAO kaasRuleDAO) {
		this.kaasRuleDAO = kaasRuleDAO;
	}

}
