package com.thinkingtop.kaas.services.algorithm.dao;

import java.util.Map;

import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.algorithm.util.OfConcurrentHashMap;

public interface KaasOrderFrequentDAO {
	public int submit(KaasOrderFrequent of);
	public int submit();
	public KaasOrderFrequent findOneByProperty(String freqSet, String myFreqSet);
	public int size();
	public KaasOrderFrequent getKeyMarsOrderFrequent(long id);
	public void clearOrderFrequent();
}
