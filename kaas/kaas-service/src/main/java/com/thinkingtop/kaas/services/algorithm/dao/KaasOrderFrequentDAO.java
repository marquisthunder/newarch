package com.thinkingtop.kaas.services.algorithm.dao;

import java.util.Map;

import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.util.OfConcurrentHashMap;

public interface KaasOrderFrequentDAO {
	public int submit(KaasOrderFrequent of);
	public int submit();
	public KaasOrderFrequent findOneByProperty(String freqSet, String myFreqSet);
	public void getKeys();
	public int size();
	public KaasOrderFrequent getKeyMarsOrderFrequent(int i);
	public void setFileAll(OfConcurrentHashMap<String, KaasOrderFrequent> fileAll);
}
