package com.thinkingtop.kaas.services.dao.implfile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.model.KaasRule;

@Component("kaasRuleDAOFileImpl")
public class KaasRuleDAOFileImpl implements KaasRuleDAO {
	private String outFilePath; 
	private Map<String,KaasRule> marsRuleAll;
	public KaasRuleDAOFileImpl(){
		marsRuleAll = new HashMap<String, KaasRule>();
	}
	
	public int submit(KaasRule r) {
		try{
			String rpr = r.getProducts()+"|"+r.getRecommendation();
			marsRuleAll.put(rpr, r);
		}catch (Exception e) {
			return 2;
		}
		return 1;
	}
	public int submit() {
		FileOutputStream fo = null;
		try {
            fo = new FileOutputStream(outFilePath,false);
            boolean one =true;
            for(Entry<String, KaasRule> me: marsRuleAll.entrySet()){
            	KaasRule o = me.getValue();
            	String song="";
            	if(one){
            		one = false;
            		song = o.getProducts() + "==" + o.getRecommendation() + "==" + o.getConfidence() + "==" + o.getFlag();
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(outFilePath,true);
            	}else{
            		song = "\r\n" + o.getProducts() + "==" + o.getRecommendation() + "==" + o.getConfidence() + "==" + o.getFlag();
            		fo.write(song.getBytes());
            	}
            }
        } catch (Exception h) {
        	return 2;
        }finally{
        	try {
        		if(fo!=null)
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return 1;
	}

	public String getOutFilePath() {
		return outFilePath;
	}

	@Value("${runner.ruleOutPath}")
	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}

	public List<KaasRule> getRule(String string) {
		List<KaasRule> marsRuleList = new ArrayList<KaasRule>();
		Set<String> mra = marsRuleAll.keySet();
		for(String s : mra){
			if(s.startsWith(string+"|")){
				marsRuleList.add(marsRuleAll.get(s));
			}
		}
		return marsRuleList;
	}
	
	public Map<String,Integer> getRuleMap(String string) {
		Map<String,Integer> marsRuleList = new HashMap<String,Integer>();
		Set<String> mra = marsRuleAll.keySet();
		for(String s : mra){
			if(s.startsWith(string+"|")){
				marsRuleList.put(s,0);
			}
		}
		return marsRuleList;
	}

	public Map<String, KaasRule> getMarsRuleAll() {
		return marsRuleAll;
	}

	public void setMarsRuleAll(Map<String, KaasRule> marsRuleAll) {
		this.marsRuleAll = marsRuleAll;
	}

	public String getRuleMap(String basisGoods, int basisSize) {
		TreeMap<Double,String> marsRuleList = new TreeMap<Double,String>();
		for(Map.Entry<String, KaasRule> me : marsRuleAll.entrySet()){
			String[] bg = me.getKey().split("\\|");
			if(bg[0].equals(basisGoods)&&(bg[1].split(",").length==basisSize)){
				marsRuleList.put(me.getValue().getConfidence(),bg[1]);
			}
		}
		if(marsRuleList.size()>0){
			return marsRuleList.get(marsRuleList.lastKey());
		}
		return null;
	}

}
