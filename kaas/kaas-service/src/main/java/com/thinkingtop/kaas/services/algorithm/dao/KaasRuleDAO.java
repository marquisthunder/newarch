package com.thinkingtop.kaas.services.algorithm.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thinkingtop.kaas.services.algorithm.model.KaasRule;

public interface KaasRuleDAO {

	public int submit(KaasRule r);
	public int submit(String dataName);
	public void clearMarsRuleAll();
	public KaasRule getRule(int id);
	public List<KaasRule> getRules(String products);
	public Iterator<KaasRule> getRuleIterator();
	public int update(KaasRule r);
	public boolean isHold(String products,String recommendation);
	public KaasRule getRule(String products,String recommendation);
}
