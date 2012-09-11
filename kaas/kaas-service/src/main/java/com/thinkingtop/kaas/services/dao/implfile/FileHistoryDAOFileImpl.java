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
	public List<String> getFileList() {
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



	public String getFileString() {
		return fileString;
	}

	@Value("${runner.dataFile}")
	public void setFileString(String fileString) {
		this.fileString = fileString;
	}

}
