package com.thinkingtop.kaas.services.algorithm;

public interface Algorithm {
    public void runIt(String name);
    public String[] getRecommend(String inputItems, int outputItemsNum,int outputQuantitye);
}
