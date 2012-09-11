package com.thinkingtop.kaas.services.dao;

import java.util.Map;

import com.thinkingtop.kaas.services.model.KaasOrderFrequent;

public interface KaasOrderFrequentDAO {
	public int submit(KaasOrderFrequent of);
	public int submit();
	public KaasOrderFrequent findOneByProperty(String freqSet, String myFreqSet);
	public void getKeys();
	public int size();
	public KaasOrderFrequent getKeyMarsOrderFrequent(int i);
	public void setFileAll(Map<String, KaasOrderFrequent> fileAll);
}
