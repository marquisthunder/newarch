package com.thinkingtop.kaas.services.algorithm;

public interface Algorithm {
    public void runIt();
    public String[] getRecommend(String inputItems, int outputItemsNum,int outputQuantitye);
}
