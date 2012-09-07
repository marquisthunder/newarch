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

import com.thinkingtop.kaas.services.dao.MarsRuleDAO;
import com.thinkingtop.kaas.services.model.MarsOrderFrequent;
import com.thinkingtop.kaas.services.model.MarsRule;

@Component("marsRuleDAOFileImpl")
public class MarsRuleDAOFileImpl implements MarsRuleDAO {
	private String outFilePath; 
	private Map<String,MarsRule> marsRuleAll;
	public MarsRuleDAOFileImpl(){
		marsRuleAll = new HashMap<String, MarsRule>();
	}
	
	public int submit(MarsRule r) {
		try{
			String rpr = r.getProducts()+"|"+r.getRecommendation();
			/*if(marsRuleAll.containsKey(rpr)){
				r.setConfidence(marsRuleAll.get(rpr).getConfidence()+r.getConfidence());
				marsRuleAll.put(rpr, r);
			}else{
	//System.out.println(r);
			}*/
			marsRuleAll.put(rpr, r);
	//System.out.println(marsRuleAll.size());
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
            for(Entry<String, MarsRule> me: marsRuleAll.entrySet()){
            	MarsRule o = me.getValue();
            	String song="";
            //System.out.println(one);
            	if(one){
            		one = false;
            		song = o.getProducts() + "==" + o.getRecommendation() + "==" + o.getConfidence() + "==" + o.getFlag();
            //System.out.println(song);
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(outFilePath,true);
            	}else{
            		song = "\r\n" + o.getProducts() + "==" + o.getRecommendation() + "==" + o.getConfidence() + "==" + o.getFlag();
            		fo.write(song.getBytes());
            //System.out.println(song);
            	}
            }
        } catch (Exception h) {
        	return 2;
        }finally{
        	try {
        		if(fo!=null)
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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

	public List<MarsRule> getRule(String string) {
		List<MarsRule> marsRuleList = new ArrayList<MarsRule>();
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

	public Map<String, MarsRule> getMarsRuleAll() {
		return marsRuleAll;
	}

	public void setMarsRuleAll(Map<String, MarsRule> marsRuleAll) {
		this.marsRuleAll = marsRuleAll;
	}

	public String getRuleMap(String basisGoods, int basisSize) {
		TreeMap<Double,String> marsRuleList = new TreeMap<Double,String>();
		for(Map.Entry<String, MarsRule> me : marsRuleAll.entrySet()){
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
