package com.thinkingtop.kaas.services.dao.implfile;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.model.KaasOrderFrequent;

@Component("kaasOrderFrequentDAOFileImpl")
public class KaasOrderFrequentDAOFileImpl implements KaasOrderFrequentDAO {
	private String outFilePath; 
	private Map<String,KaasOrderFrequent> fileAll;
	private String[] keys;
	public KaasOrderFrequentDAOFileImpl() {
		fileAll = new HashMap<String, KaasOrderFrequent>();
	}
	public KaasOrderFrequent findOneByProperty(String freqSet, String myFreqSet) {
		return fileAll.get(myFreqSet);
	}

	public int submit(KaasOrderFrequent o) {
		try{
			if(fileAll.containsKey(o.getCombination())){
				o.setFrequent(fileAll.get(o.getCombination()).getFrequent()+o.getFrequent());
				fileAll.put(o.getCombination(), o);
			}else{
				fileAll.put(o.getCombination(), o);
			}
		}catch (Exception e) {
			return 2;
		}
		return 1;
	}
	
	
	public int submit(){
		FileOutputStream fo = null;
		try {
            fo = new FileOutputStream(outFilePath,false);
            boolean one =true;
            for(Map.Entry<String, KaasOrderFrequent> me: fileAll.entrySet()){
            	KaasOrderFrequent o = me.getValue();
            	String song="";
            	if(one){
            		one = false;
            		song = o.getCombination() + "==" + o.getFrequent() + "==" + o.getItemNum() + "==" + o.getOfType();
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(outFilePath,true);
            	}else{
            		song = "\r\n"+o.getCombination() + "==" + o.getFrequent() + "==" + o.getItemNum() + "==" + o.getOfType();
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
	public Map<String, KaasOrderFrequent> getFileAll() {
		return fileAll;
	}
	public void setFileAll(Map<String, KaasOrderFrequent> fileAll) {
		this.fileAll = fileAll;
	}
	public void getKeys() {
		keys = fileAll.keySet().toArray(new String[0]);
	}
	public int size() {
		getKeys();
		return fileAll.size();
	}
	public KaasOrderFrequent getKeyMarsOrderFrequent(int i) {
		int j = 1000000000;
		return fileAll.get(keys[i]);
	}
	public String getOutFilePath() {
		return outFilePath;
	}
	
	@Value("${runner.orderFrequentOutPath}")
	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}
}
