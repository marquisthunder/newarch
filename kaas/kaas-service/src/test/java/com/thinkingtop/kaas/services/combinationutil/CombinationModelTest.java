package com.thinkingtop.kaas.services.combinationutil;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class CombinationModelTest {
    static Logger logger=Logger.getLogger(CombinationModelTest.class);
	@Test
	public void testGenRuleCombinations() {
		String[] lineArr = {"1","2","13","15"};
		CombinationModel cm = new CombinationModel(lineArr);
		Map<String,Integer> rulemap = null;
		rulemap = cm.genRuleCombinations();
        cm = null;
        for (Map.Entry<String, Integer> me: rulemap.entrySet()){
        	//logger.info(me.getKey());
        	Assert.assertEquals(true, me.getKey().matches(".+[\\|].+"));
        }
        Assert.assertEquals(14, rulemap.size());
	}
	
	@Test
	public void testGenCombinations() {
		int freqSetMaxSize = 10;
		Set<String> idlist = new HashSet<String>();
        idlist.add("a");
        idlist.add("b");
        idlist.add("c");
        idlist.add("d");
        Map<String,Integer> submitMap = new HashMap<String, Integer>();
		CombinationModel cm = new CombinationModel(idlist.toArray(new String[0]),submitMap);
        cm.genCombinations(freqSetMaxSize);
        cm=null;
        
        Set<String> idlist2 = new HashSet<String>();
        idlist2.add("a");
        idlist2.add("c");
        idlist2.add("d");
		cm = new CombinationModel(idlist2.toArray(new String[0]),submitMap);
        cm.genCombinations(freqSetMaxSize);
        cm=null;
        
        Assert.assertEquals(15, submitMap.size());
        for (Map.Entry<String, Integer> me: submitMap.entrySet()){
        	Assert.assertEquals(true, me.getKey().length()<=(idlist.size()*2-1));
        	Assert.assertEquals(true,me.getValue()<=2);
        }
	}

}
