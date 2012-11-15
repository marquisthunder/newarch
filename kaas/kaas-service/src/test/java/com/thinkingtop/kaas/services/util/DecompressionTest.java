package com.thinkingtop.kaas.services.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.junit.Test;

public class DecompressionTest {
	public void jar(String inputFileName, String outputFileName)
			throws Exception {
		String classpath = PackageAlgorithm.class.getResource("/").toString().substring("file:".length());
		if(classpath.matches(".*target/test-classes.*")){
			classpath = classpath.replaceFirst("target/test-classes","target/classes");
		}
		Manifest m = new Manifest();
		Attributes a = m.getMainAttributes();
		a.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		a.put(Attributes.Name.MAIN_CLASS, "com.thinkingtop.kaas.services.util.Decompression");
		
		JarOutputStream out = new JarOutputStream(new FileOutputStream(
				outputFileName),m);
		
		File f = new File(inputFileName);
		String bean = "com/thinkingtop/kaas/services/algorithm/SevenZip";
		jar(out, f, bean);
		
		f = new File(classpath+"com/thinkingtop/kaas/services/util/Decompression.class");
		jar(out, f, "com/thinkingtop/kaas/services/util/Decompression.class");
		
		out.close();
	}



	private void jar(JarOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			base += "/";
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
	
	@Test
	public void testPackage() {
		String outpath = new File("").getAbsolutePath()+"/../dist/data/kaas-service";
		File f = new File(outpath);
		if(!f.exists()||!f.isDirectory()){
			f.mkdirs();
		}
		String classpath = Thread.currentThread().getContextClassLoader().getResource("").toString().substring("file:".length());
		if(classpath.matches(".*target/test-classes.*")){
			classpath = classpath.replaceFirst("target/test-classes","target/classes");
		}
		try {
			jar(classpath+"/com/thinkingtop/kaas/services/algorithm/SevenZip",outpath+"/Decompression.jar");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
