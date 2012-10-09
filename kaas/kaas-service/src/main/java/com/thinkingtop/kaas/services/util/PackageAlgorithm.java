package com.thinkingtop.kaas.services.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("packageAlgorithm")
public class PackageAlgorithm {
	private KaasDataPath kaasDataPath;
	public void jar(String inputFileName, String outputFileName)
			throws Exception {
		JarOutputStream out = new JarOutputStream(new FileOutputStream(
				outputFileName),new Manifest());
		File f = new File(inputFileName);// 这里应该以那个目录为根目录，jar的根应该是这个目录下的文件
		jar(out, f, "/"+f.getName());
		out.close();
	}

	private void jar(JarOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				jar(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new JarEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int n = in.read(buffer);
			while (n != -1) {
				out.write(buffer, 0, n);
				n = in.read(buffer);
			}
			in.close();
		}
	}

	
	public void packageA(){

		/*try {
			jar("/home/roadahead/myTest/outclassjar/com", "/home/roadahead/myTest/outclassjar/test.jar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			jar("/home/roadahead/myTest/outclassjar/com/algorithm/Algorithm.class", "/home/roadahead/myTest/outclassjar/test2.jar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}
	
	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}
}