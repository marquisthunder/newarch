package com.thinkingtop.kaas.services.dao.implfile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Repeat;
import org.springframework.validation.annotation.Validated;

import com.thinkingtop.kaas.services.dao.FileHistoryDAO;

@Component("fileHistoryDAOFileImpl")
public class FileHistoryDAOFileImpl implements FileHistoryDAO {
	private String fileString;
	public List<String> getFileList(String Order, int in1, int in2) {
		// TODO Auto-generated method stub
		/*for(int i=0;i<10;i++){
			fileLiest.add("smb"+i);
		}*/
		List<String> fileLiest = new ArrayList<String>();
		String[] files = fileString.split(",");
		for(String file : files){
			if(file==null&&file.equals("")){
				return null;
			}
			fileLiest.add(file);
		}
		return fileLiest;
	}

	public void updateFlag(List<String> filelist, String Order, int i) {
		// TODO Auto-generated method stub

	}

	public String getFileString() {
		return fileString;
	}

	@Value("${runner.dataFile}")
	public void setFileString(String fileString) {
		this.fileString = fileString;
	}

}
