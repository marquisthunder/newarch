package com.thinkingtop.kaas.etl.result;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Result2Test {

	public static void main(String[] args) throws MalformedURLException {
		Result r = new Result();
		r.getResult();
		/*URL ls = new URL("http://localhost:8080/1.jar");
		URLClassLoader lo = new URLClassLoader(new URL[]{ls},ClassLoader.getSystemClassLoader());
		URL ll[] = lo.getURLs();
		for(int i=0;i<ll.length;i++) {
			System.out.println(ll[i].toString());
		}*/
		
	}

}
