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

import com.thinkingtop.kaas.services.dao.MarsOrderFrequentDAO;
import com.thinkingtop.kaas.services.model.MarsOrderFrequent;

@Component("marsOrderFrequentDAOFileImpl")
public class MarsOrderFrequentDAOFileImpl implements MarsOrderFrequentDAO {
	private String outFilePath; 
	private Map<String,MarsOrderFrequent> fileAll;
	private String[] keys;
	public MarsOrderFrequentDAOFileImpl() {
		fileAll = new HashMap<String, MarsOrderFrequent>();
		/*DataInputStream in = null;
		try {
			in = new DataInputStream(new BufferedInputStream(
			        new FileInputStream("/home/roadahead/myTest/testsmb/Mars/MarsOrderFrequent")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line="";
		try {
			while ((line = in.readLine()) != null) {
				if(line.equals("")){
					continue;
				}
				String[] ss = line.split("==");
			//System.out.println(ss[0]+ss[1]+ss[2]+ss[3]);
				MarsOrderFrequent mof = new MarsOrderFrequent(ss[0],Integer.valueOf(ss[1]),Integer.valueOf(ss[2]),ss[3]);
			//System.out.println(mof.getFreqSet());
				fileAll.put(mof.getFreqSet(), mof);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//System.out.println(fileAll.size());
	}
	public MarsOrderFrequent findOneByProperty(String freqSet, String myFreqSet) {
		return fileAll.get(myFreqSet);
	}

	public int submit(MarsOrderFrequent o) {
		try{
			if(fileAll.containsKey(o.getFreqSet())){
				o.setSupport(fileAll.get(o.getFreqSet()).getSupport()+o.getSupport());
				fileAll.put(o.getFreqSet(), o);
			}else{
	//System.out.println(o);
				fileAll.put(o.getFreqSet(), o);
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
            for(Map.Entry<String, MarsOrderFrequent> me: fileAll.entrySet()){
            	MarsOrderFrequent o = me.getValue();
            	String song="";
            //System.out.println(one);
            	if(one){
            		one = false;
            		song = o.getFreqSet() + "==" + o.getSupport() + "==" + o.getLevel() + "==" + o.getOfType();
            //System.out.println(song);
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(outFilePath,true);
            	}else{
            		song = "\r\n"+o.getFreqSet() + "==" + o.getSupport() + "==" + o.getLevel() + "==" + o.getOfType();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return 1;
	}
	public Map<String, MarsOrderFrequent> getFileAll() {
		return fileAll;
	}
	public void setFileAll(Map<String, MarsOrderFrequent> fileAll) {
		this.fileAll = fileAll;
	}
	public void getKeys() {
		keys = fileAll.keySet().toArray(new String[0]);
	}
	public int size() {
		getKeys();
		return fileAll.size();
	}
	public MarsOrderFrequent getKeyMarsOrderFrequent(int i) {
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
