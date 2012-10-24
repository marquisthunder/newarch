package com.thinkingtop.kaas.services.dao.implfile;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.util.AlgorithmProperties;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;
import com.thinkingtop.kaas.services.dao.RuleDAO;
import com.thinkingtop.kaas.services.util.PackagePath;

@Component("ruleDAOFileImpl")
public class RuleDAOFileImpl implements RuleDAO {
	static Logger logger=Logger.getLogger(RuleDAOFileImpl.class);
    private KaasDataPath kaasDataPath;
    private  AlgorithmProperties algorithmProperties;
	public String[] getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye) {
		if(outputItemsNum<=0){
			outputItemsNum = 1;
		}
		if(outputQuantitye<=0){
			outputQuantitye = 1;
		}
		Map<String,Double> marsRuleList = new HashMap<String,Double>();
		DataInputStream inR = null;
		try {
            inR = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(kaasDataPath.getRDataPath()+"/date1")));
        } catch (FileNotFoundException e) {
            logger.warn("local offline file may be moved or renamed!");
        }
		int iR = 0;
		String rulestr;
		try {
            while ((rulestr = inR.readLine()) != null) {
            	String[] rules = rulestr.split("==");
            	if(rules[0].equals(inputItems)&&rules[1].split(algorithmProperties.getItemDelimiter()).length==outputItemsNum){
            		marsRuleList.put(rules[1],Double.valueOf(rules[2]));
            	}
            	iR ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	try {
        		inR.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
		
		ArrayList<Map.Entry<String, Double>> list = sortMap(marsRuleList);
		String[] item = new String[outputQuantitye];
		for(int i=0;i<outputQuantitye&&i<list.size();i++){
			item[i] = list.get(i).getKey();
		}
		return item;
	}
	
	public ArrayList<Map.Entry<String, Double>> sortMap(Map map){
		 ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
		 Collections.sort(list,new Comparator<Map.Entry<String, Double>>(){
			 public int compare(Entry<java.lang.String, Double> arg0,
			 Entry<java.lang.String, Double> arg1) {
				 double o1 = arg0.getValue();
				 double o2 = arg1.getValue();
				 if(o1<o2) return 1;
					else if(o1==o2) return 0;
					else return -1;
			 }
		 });
		 return list;
	}
	public AlgorithmProperties getAlgorithmProperties() {
		return algorithmProperties;
	}
	@Resource(name="algorithmProperties")
	public void setAlgorithmProperties(AlgorithmProperties algorithmProperties) {
		this.algorithmProperties = algorithmProperties;
	}

	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}

	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}


}
