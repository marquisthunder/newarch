package com.thinkingtop.kaas.server.jar.maintenance;

import java.util.Iterator;
import java.util.List;

import com.thinkingtop.kaas.server.model.KaasJarInfo;
import com.thinkingtop.kaas.server.service.JarService;

/*
 * 1)TreeMap is sorted by key  in ascending order by default.
 * 2)not support duplicate key, new element with his key which has existed will cover the origin element.  
 * for test, we use TreeMap.. But in real environment, we store data in mysql/   
 */
public class Maintenance {
	
	//private static final Logger logger = LoggerFactory.getLogger(Maintenance.class.getName());
	
	private JarService jarService;

	public void setJarService(JarService jarService) {
		this.jarService = jarService;
	}

	/*private Maintenance() {
		
	}
	
	private static Maintenance maintenance=null;
	public static Maintenance newInstance() {
		if(maintenance==null) {
			maintenance = new Maintenance();
			return maintenance;
		}
		return maintenance;
	}
	*/
	//private static SortedMap<Date, String> map = new TreeMap<Date, String>();
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
	
	public void addJarInfo(KaasJarInfo info) {
		/*Date date = info.getExpiredDate();
		String jarName = info.getJarName();
		map.put(date, jarName);*/
		jarService.addJarInfo(info);
	}
	
	public KaasJarInfo popJarInfo() {
		/*Date date = map.firstKey();
		String jarName = map.get(date);
		JarInfo info = new JarInfo();
		info.setExpiredDate(date);
		info.setJarName(jarName);
		map.remove(date);
		return info;*/
		KaasJarInfo info = jarService.getFirstJar();
		String name = jarService.getFirstJar().getJarName();
		jarService.deleteJarInfo(name);
		return info;
	}
	
	public List<KaasJarInfo> popJarInfos() {
		List<KaasJarInfo> list = jarService.getFirstJars();
		Iterator<KaasJarInfo> ite = list.iterator();
		while(ite.hasNext()) {
			jarService.deleteJarInfo(ite.next().getJarName());
		}
		return list;
	}
	
	public KaasJarInfo getFirstJarInfo() {
		/*if(map.size()==0) {
			return null;
		}
		Date date = map.firstKey();
		String jarName = map.get(date);
		JarInfo info = new JarInfo();
		info.setExpiredDate(date);
		info.setJarName(jarName);
		return info;*/
		KaasJarInfo info = jarService.getFirstJar();
		return info;
	}
	
}
