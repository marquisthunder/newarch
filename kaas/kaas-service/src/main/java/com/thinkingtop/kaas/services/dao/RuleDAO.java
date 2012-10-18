package com.thinkingtop.kaas.services.dao;

public interface RuleDAO {
	public String[] getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye);
}
