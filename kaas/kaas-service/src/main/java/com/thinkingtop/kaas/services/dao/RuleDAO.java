package com.thinkingtop.kaas.services.dao;

import com.thinkingtop.kaas.services.model.Rule;

public interface RuleDAO {
	public String[] getRecommend(String scheme, String inputItems, int outputItemsNum,
			int outputQuantitye);
	public Rule getRule(int id);
}
