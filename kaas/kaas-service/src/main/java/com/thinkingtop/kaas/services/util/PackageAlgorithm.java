package com.thinkingtop.kaas.services.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.model.ECommerce;
import com.thinkingtop.kaas.services.model.ECommerce_Scheme;
import com.thinkingtop.kaas.services.model.Scheme;

@Component("packageAlgorithm")
public class PackageAlgorithm {
	private PackagePath packagePath;
	static Logger logger=Logger.getLogger(PackageAlgorithm.class);
	public void jar(String inputFileName, String outputFileName, String schemeName,String ecommerceName, String algorithmNames)
			throws Exception {
		String[] algorithm = algorithmNames.split(",");
		Manifest m = new Manifest();
		Attributes a = m.getMainAttributes();
		a.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		a.put(Attributes.Name.MAIN_CLASS, "com.thinkingtop.kaas.services.algorithm.entrance.Entrance");
		a.put(Attributes.Name.CLASS_PATH, "lib/kaas-schemeRely-jar-with-dependencies.jar");
		
		JarOutputStream out = new JarOutputStream(new FileOutputStream(
				outputFileName),m);
		String base = "com/thinkingtop/kaas/services";
		String[] bases = base.split("/");
		for(int i=0;i<bases.length;i++){
			String ba = "";
			for(int j=0;j<=i;j++){
				ba = ba + bases[j] + "/";
			}
			out.putNextEntry(new JarEntry(ba));
		}
		
		File f = new File(inputFileName);
		jar(out, f, base+"/algorithm",algorithm);
		
		String classpath = PackageAlgorithm.class.getResource("/").toString().substring("file:".length());
		if(classpath.matches(".*target/test-classes.*")){
			classpath = classpath.replaceFirst("target/test-classes","target/classes");
		}
		f = new File(classpath+"algorithm.properties");
		jar(out, f, "algorithm.properties",algorithm);
		f = new File(classpath+"algorithmbeans.xml");
		jar(out, f, "algorithmbeans.xml",algorithm);
		f = new File(classpath+"algorithmjdbc.properties");
		jar(out, f, "algorithmjdbc.properties",algorithm);
		f = new File(classpath+"log4j.properties");
		jar(out, f, "log4j.properties",algorithm);
		jarSchemeProperties(out,schemeName,ecommerceName,algorithmNames);
		
		out.close();
	}

	private void jarSchemeProperties(JarOutputStream out, String schemeName,
			String ecommerceName, String algorithmNames) {
		
		Properties properties = new Properties();
		try {
			out.putNextEntry(new JarEntry("scheme.properties"));
			properties.setProperty("scheme.name", schemeName);
			properties.setProperty("eCommerce.name", ecommerceName);
			properties.setProperty("algorithm.Sequence", algorithmNames);
            properties.store(out, "author: 954068039@QQ.com");  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void jarImpl(JarOutputStream out, File f, String base,
			String[] algorithm) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			base = base.length() == 0 ? "" : base + "/";
			out.putNextEntry(new JarEntry(base));
			for (int i = 0; i < fl.length; i++) {
				//logger.info("impl filename:------"+fl[i].getName());
				for(String alg : algorithm){
					if(fl[i].getName().equals(alg+".class")||fl[i].getName().equals(alg+"$KaasAprioriTask.class")){
						jar(out, fl[i], base + fl[i].getName(),algorithm);
					}
				}
			}
		}
	}

	private void jar(JarOutputStream out, File f, String base,String[] algorithm)
			throws Exception {
		if (f.isDirectory()) {
			if(base.equals("com/thinkingtop/kaas/services/algorithm/impl")){
				jarImpl(out, f, base, algorithm);
				return;
			}
			File[] fl = f.listFiles();
			base = base.length() == 0 ? "" : base + "/";
			out.putNextEntry(new JarEntry(base));
			for (int i = 0; i < fl.length; i++) {
				jar(out, fl[i], base + fl[i].getName(),algorithm);
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

	
	public void packageA(ECommerce ecommerce){
		Set<ECommerce_Scheme> ec_ss = ecommerce.getEcommerce_scheme();
		for(ECommerce_Scheme ec_s : ec_ss.toArray(new ECommerce_Scheme[ec_ss.size()])){
			Scheme s = ec_s.getScheme();
			//logger.info("schemes------------"+s.getAlgorithmNames());
			try {
				String jarName = packagePath.getMyKaasdataPath()+"/"+s.getSchemeName()+".jar";
				logger.info("packagePath: "+ jarName);
				File jar = new File(jarName);
				if(!jar.exists()){
					jar(packagePath.getAlgorithmPath(), jarName,s.getSchemeName(),ecommerce.getEcommerceName(),s.getAlgorithmNames());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public PackagePath getPackagePath() {
		return packagePath;
	}
	
	@Resource(name="packagePath")
	public void setPackagePath(PackagePath packagePath) {
		this.packagePath = packagePath;
	}
	
}