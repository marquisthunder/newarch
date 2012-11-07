package com.thinkingtop.kaas.server.dao;

import java.io.File;

import com.thinkingtop.kaas.server.model.KaasRule;

public interface KaasRuleDAO {
	public void insert(KaasRule kaasRule);
	public void insertFromFile(File file);
}
