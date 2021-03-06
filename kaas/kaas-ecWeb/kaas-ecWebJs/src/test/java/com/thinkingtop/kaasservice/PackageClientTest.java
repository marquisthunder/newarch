package com.thinkingtop.kaasservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.Logger;

import org.junit.Test;

public class PackageClientTest {
	private Logger logger=Logger.getLogger(PackageClientTest.class.toString());
	public void jar(String inputFileName, String outputFileName)
			throws Exception {
		String classpath = PackageClientTest.class.getResource("/").toString().substring("file:".length());
		if(classpath.matches(".*target/test-classes.*")){
			classpath = classpath.replaceFirst("target/test-classes","target/classes");
		}
		Manifest m = new Manifest();
		Attributes a = m.getMainAttributes();
		a.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		a.put(Attributes.Name.IMPLEMENTATION_TITLE, "kaasServiceClient");
		a.put(Attributes.Name.IMPLEMENTATION_VERSION, "1.0.1");
		a.put(Attributes.Name.IMPLEMENTATION_VENDOR, "\"Apache Software Foundation\"");
		
		JarOutputStream out = new JarOutputStream(new FileOutputStream(
				outputFileName),m);
		String base = "com/thinkingtop/kaasservice";
		
		String[] bases = base.split("/");
		for(int i=0;i<bases.length;i++){
			String ba = "";
			for(int j=0;j<=i;j++){
				ba = ba + bases[j] + "/";
			}
			out.putNextEntry(new JarEntry(ba));
		}
		out.putNextEntry(new JarEntry("META-INF/"));
		File f = new File(inputFileName);
		jar(out, f, base);
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
		String outpath = new File("").getAbsolutePath()+"/../../dist/data/kaas-ecWeb/lib";
		logger.info("package path--:"+outpath);
		File f = new File(outpath);
		if(!f.exists()||!f.isDirectory()){
			f.mkdirs();
		}
		String classpath = Thread.currentThread().getContextClassLoader().getResource("").toString().substring("file:".length());
		if(classpath.matches(".*target/test-classes.*")){
			classpath = classpath.replaceFirst("target/test-classes","target/classes");
		}
		try {
			jar(classpath+"/com/thinkingtop/kaasservice",outpath+"/kaasServiceClient-1.0.jar");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
