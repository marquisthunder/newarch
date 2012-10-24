package com.thinkingtop.kaas.services.algorithm;

public interface Algorithm {
    public void runIt(int name);
    public String[] getRecommend(String inputItems, int outputItemsNum,int outputQuantitye);
}
