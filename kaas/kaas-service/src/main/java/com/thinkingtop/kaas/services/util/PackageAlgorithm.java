package com.thinkingtop.kaas.services.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;
import com.thinkingtop.kaas.services.model.ECommerce;
import com.thinkingtop.kaas.services.model.Scheme;

@Component("packageAlgorithm")
public class PackageAlgorithm {
	private KaasDataPath kaasDataPath;
	static Logger logger=Logger.getLogger(PackageAlgorithm.class);
	public void jar(String inputFileName, String outputFileName,String[] algorithm)
			throws Exception {
		Manifest m = new Manifest();
		Attributes a = m.getMainAttributes();
		a.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		a.put(Attributes.Name.MAIN_CLASS, "com.thinkingtop.kaas.services.algorithm.entrance.Entrance");
		
		JarOutputStream out = new JarOutputStream(new FileOutputStream(
				outputFileName),m);
		File f = new File(inputFileName);
		jar(out, f, "com/thinkingtop/kaas/services/algorithm",algorithm);
		out.close();
	}

	private void jarImpl(JarOutputStream out, File f, String base,
			String[] algorithm) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				//logger.info("impl filename:------"+fl[i].getName());
				for(String alg : algorithm){
					if(fl[i].getName().equals(alg+".class")){
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

	
	public void packageA(ECommerce eCommerce){
		Set<Scheme> schemes = eCommerce.getSchemes();
		for(Scheme s : schemes.toArray(new Scheme[schemes.size()])){
			String[] Algorithm = s.getAlgorithmNames().split(",");
			//logger.info("schemes------------"+s.getAlgorithmNames());
			try {
				jar(kaasDataPath.getAlgorithmPath(), kaasDataPath.getMyKaasdataPath()+"/"+s.getSchemeName()+".jar",Algorithm);
			} catch (Exception e) {
				e.printStackTrace();
			}
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