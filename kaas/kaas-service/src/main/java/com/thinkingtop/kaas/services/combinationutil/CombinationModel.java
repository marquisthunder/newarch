package com.thinkingtop.kaas.services.combinationutil;

import java.util.HashMap;
import java.util.Map;

/**
 * Combination generator auxiliary class
 * @author roadahead
 *
 */
public class CombinationModel {
	private String[] lineArr;
	Map<String, Integer> submitMap;
	public CombinationModel(String[] array, Map<String, Integer> submitMap) {
		this.lineArr = array;
		this.submitMap = submitMap;
	}

	public CombinationModel(String[] lineArr) {
		this.lineArr = lineArr;
	}

	public void genCombinations(int freqSetMaxSize) {
		int[] indices;
		//int j = freqSetMaxSize;
		for(int j=1;j<=lineArr.length && j<=freqSetMaxSize;j++){
	        CombinationGenerator x = new CombinationGenerator(lineArr.length, j);
	        StringBuffer combination;
	        while (x.hasMore ()) {
	          combination = new StringBuffer ();
	          indices = x.getNext ();
	          for (int i = 0; i < indices.length; i++) {
	            combination.append (lineArr[indices[i]]);
	            combination.append (",");
	          }
	          if(combination.toString().endsWith(",")){
	        	  combination.deleteCharAt(combination.length()-1);
	          }
	          if(submitMap.containsKey(combination.toString())){
	        	  int newSup = submitMap.get(combination.toString ());
	        	  submitMap.put(combination.toString (),newSup+1);
	          }else{
	        	  submitMap.put(combination.toString (),1);
	          }
	        }
		}
	}

	public Map<String, Integer> genRuleCombinations() {
		Map<String,Integer> rulemap = new HashMap<String, Integer>();
		int[] indices;
		for(int j=1;j<lineArr.length;j++){
	        CombinationGenerator x = new CombinationGenerator(lineArr.length, j);
	        StringBuffer combination;
	        StringBuffer combination2;
	        while (x.hasMore ()) {
	          combination = new StringBuffer ();
	          combination2 = new StringBuffer ();
	          indices = x.getNext ();
	          String[] elements2 = new String[lineArr.length];
	          for (int i = 0; i < indices.length; i++) {
	        	elements2[indices[i]]="1";
	            combination.append (lineArr[indices[i]]);
	            combination.append (",");
	          }
	          for(int i=0;i<elements2.length;i++){
	        	  if(elements2[i]==null){
	        		  combination2.append (lineArr[i]);
	  	              combination2.append (",");
	        	  }
	          }
	          if(combination.toString().endsWith(",")){
	        	  combination.deleteCharAt(combination.length()-1);
	          }
	          if(combination2.toString().endsWith(",")){
	        	  combination2.deleteCharAt(combination2.length()-1);
	          }
	          
	          rulemap.put(combination.toString ()+"|"+combination2.toString (),null);
	        }
		}
		return rulemap;
	}


}
