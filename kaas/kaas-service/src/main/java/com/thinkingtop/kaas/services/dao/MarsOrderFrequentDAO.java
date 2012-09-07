package com.thinkingtop.kaas.services.dao;

import java.util.Map;

import com.thinkingtop.kaas.services.model.MarsOrderFrequent;

public interface MarsOrderFrequentDAO {
	public int submit(MarsOrderFrequent of);
	public int submit();
	public MarsOrderFrequent findOneByProperty(String freqSet, String myFreqSet);
	public void getKeys();
	public int size();
	public MarsOrderFrequent getKeyMarsOrderFrequent(int i);
	public void setFileAll(Map<String, MarsOrderFrequent> fileAll);
}
