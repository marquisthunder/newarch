package com.thinkingtop.kaas.server.jar.maintenance;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.thinkingtop.kaas.server.jar.manager.TimerOperator;

/*
 * 1)TreeMap is sorted by key  in ascending order by default.
 * 2)not support duplicate key, new element with his key which has existed will cover the origin element.    
 */
public class Maintenance {
	
	private Maintenance() {
		
	}
	private static Maintenance maintenance=null;
	public static Maintenance newInstance() {
		if(maintenance==null) {
			maintenance = new Maintenance();
			return maintenance;
		}
		return maintenance;
	}
	
	private static SortedMap<Date, String> map = new TreeMap<Date, String>();
	
	/*public static void main(String args[]) {
		SortedMap<Date, String> map = null;
		map = new TreeMap<Date, String>();
		Date date = new Date();
		map.put(date, "http://www.jiangker.com/");
		map.put(date, "www.mldn.cn");
		map.put(new Date(date.getTime() + 2000), "www.zhinangtuan.net.cn");
		map.put(new Date(date.getTime() - 1000), "www.mldnjava.cn");
		System.out.print("first key：" + map.firstKey());
		System.out.println("value：" + map.get(map.firstKey()));
		System.out.print("last key：" + map.lastKey());
		System.out.println("value ：" + map.get(map.lastKey()));
		System.out.print("second key：" + date);
		System.out.println("value：" + map.get(date));
		System.out.println(map.size());
	}
*/
	public void addJarInfo(Date date, String jarName) {
		if(map.size()==0) {
			map.put(date, jarName);
			TimerOperator.getInstance().execute(this);
		}
		else {
			if(this.getFirstJarInfo().getExpiredDate().getTime()>date.getTime()) {
				map.put(date, jarName);
				TimerOperator.getInstance().execute(this);
			}
			else {
				map.put(date, jarName);
			}
		}
			
	}
	
	public void addJarInfo(JarInfo info) {
		Date date = info.getExpiredDate();
		String jarName = info.getJarName();
		map.put(date, jarName);
	}
	
	public JarInfo popJarInfo() {
		Date date = map.firstKey();
		String jarName = map.get(date);
		JarInfo info = new JarInfo();
		info.setExpiredDate(date);
		info.setJarName(jarName);
		map.remove(date);
		return info;
	}
	
	public JarInfo getFirstJarInfo() {
		if(map.size()==0) {
			return null;
		}
		Date date = map.firstKey();
		String jarName = map.get(date);
		JarInfo info = new JarInfo();
		info.setExpiredDate(date);
		info.setJarName(jarName);
		return info;
	}
	
}
