package com.thinkingtop.kaas.services.manage;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.RuleDAO;
import com.thinkingtop.kaas.services.model.Rule;

@Component("ruleManage")
public class RuleManage {
	static Logger logger=Logger.getLogger(RuleManage.class);
	private RuleDAO ruleDAO;
	
	public Rule getRule(int id){
		return ruleDAO.getRule(id);
	}
	public String[] getRecommend(String scheme,String inputItems, int outputItemsNum,
			int outputQuantitye) {
		if(outputItemsNum<=0){
			outputItemsNum = 1;
		}
		if(outputQuantitye<=0){
			outputQuantitye = 1;
		}
		
		List<Rule> rules = ruleDAO.getRecommend(inputItems, outputItemsNum, outputQuantitye);
		
		//logger.info("rules seze :-----"+rules.size());
		String[] items = new String[rules.size()];
		int i=0;
		for(Rule r : rules){
			items[i] = r.getRecommendation();
			i++;
		}
		return items;
	}
	
	
	public RuleDAO getRuleDAO() {
		return ruleDAO;
	}
	
	@Resource(name="ruleDAOImpl")
	public void setRuleDAO(RuleDAO ruleDAO) {
		this.ruleDAO = ruleDAO;
	}
}
