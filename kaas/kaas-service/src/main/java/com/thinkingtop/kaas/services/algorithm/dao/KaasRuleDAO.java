package com.thinkingtop.kaas.services.algorithm.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thinkingtop.kaas.services.algorithm.model.KaasRule;

public interface KaasRuleDAO {

	public int submit(KaasRule r);
	public int submit();
	public List<KaasRule> getRule(String string);
	public Map<String,Integer> getRuleMap(String Goods);
	public void setMarsRuleAll(Map<String, KaasRule> marsRuleAll);
	public String[] getRuleMap(String inputItems,int outputItemsNum,int outputQuantitye);
	public String[] getRuleMap(String inputItems, int outputItemsNum);
}
