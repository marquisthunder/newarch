package com.thinkingtop.mars.daemon.tools;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CombinationModelTest {

	@Test
	public void testGenRuleCombinations() {
		/*String[] lineArr = {"1","2","13","15"};
		CombinationModel cm = new CombinationModel(lineArr);
		Map<String,Integer> rulemap = null;
		rulemap = cm.genRuleCombinations();
        cm = null;
        for (Map.Entry<String, Integer> me: rulemap.entrySet()){
        	String[] tmp = me.getKey().split("\\|");
        	//System.out.println(me.getKey());
        	System.out.println(tmp[0]+"=>"+tmp[1]);
        }*/
	}
	
	@Test
	public void testGenCombinations() {
		/*int freqSetMaxSize = 10;
		Set<String> idlist = new HashSet<String>();
        idlist.add("a");
        idlist.add("b");
        idlist.add("c");
        idlist.add("d");
        Map<String,Integer> submitMap = new HashMap<String, Integer>();
		CombinationModel cm = new CombinationModel(idlist.toArray(new String[0]),submitMap);
        cm.genCombinations(freqSetMaxSize);
        //sset=null;
        cm=null;
        
        Set<String> idlist2 = new HashSet<String>();
        idlist2.add("a");
        idlist2.add("c");
        idlist2.add("d");
		cm = new CombinationModel(idlist2.toArray(new String[0]),submitMap);
        cm.genCombinations(freqSetMaxSize);
        //sset=null;
        cm=null;
        
        for (Map.Entry<String, Integer> me: submitMap.entrySet()){
        	System.out.println(me.getKey()+":"+me.getValue());
        }*/
	}

}
