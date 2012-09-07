package com.thinkingtop.kaas.services.dao;

import java.util.List;
import java.util.Map;

import com.thinkingtop.kaas.services.model.MarsRule;

public interface MarsRuleDAO {

	public int submit(MarsRule r);
	public int submit();
	public List<MarsRule> getRule(String string);
	public Map<String,Integer> getRuleMap(String string);

}
