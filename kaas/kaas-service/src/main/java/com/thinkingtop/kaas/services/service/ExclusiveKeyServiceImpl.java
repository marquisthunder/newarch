package com.thinkingtop.kaas.services.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.manage.ExclusiveKeyManage;
import com.thinkingtop.kaas.services.manage.KebsiteManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;
import com.thinkingtop.kaas.services.tasks.AprioriRunner;

/**
 * This is a ExclusiveKeyService implementation class
 * External exposure method, allows users to call
 * @author roadahead
 *
 */
@Component("exclusiveKeyServiceImpl")
@WebService(endpointInterface = "com.thinkingtop.kaas.services.service.ExclusiveKeyService")
public class ExclusiveKeyServiceImpl implements ExclusiveKeyService{
	private ExclusiveKeyManage exclusiveKeyManage;
	private KebsiteManage kebsiteManage;
	private AprioriRunner aprioriRunner;

	/**
	 * External exposure method, verification of account and password,
	 * and then give the user the recommended products
	 * @param kebsiteName: User address or username 
	 * @param APIKey: User APIKey
	 * @param BasisGoods: Recommend products origin
	 * @param BasisSize: To recommend a product
	 */
	public String getGoods(String kebsiteName,String APIKey,String BasisGoods,int BasisSize) {
		if(!kebsiteManage.isHold(kebsiteName)){
			System.out.println("The user does not exist");
			return null;
		}
		if(!exclusiveKeyManage.isHold(kebsiteName,APIKey)){
			System.out.println("The user does not have the APIKey");
			return null;
		}
		if(exclusiveKeyManage.isActivation(APIKey)){
			aprioriRunner.runIt();
			String mapGoods = aprioriRunner.getGoods(BasisGoods,BasisSize);
			return mapGoods;
		}
		return null;
	}
	
	/**
	 * External exposure method, the user request and returns a APIKey,
	 * and put it into the database
	 * @param kebsiteName: User address or username
	 */
	public String getAPIKey(String kebsiteName) {
		Kebsite kebsite =kebsiteManage.getKebsite(kebsiteName);
		if(kebsite==null){
			return "User does not exist!";
		}
		StringBuffer keyString = getAPIKey();
		boolean e = exclusiveKeyManage.isHold(keyString);
		System.out.println(e);
		while(exclusiveKeyManage.isHold(keyString)){
			keyString = getAPIKey();
		}
		exclusiveKeyManage.add(kebsite,keyString);
		return keyString.toString();
	}
	
	
	
	/**
	 * 创建一个APIKey并返回
	 * @return StringBuffer 所要返回的APIKey的StringBuffer
	 */
	public StringBuffer getAPIKey(){
		StringBuffer timeString = getTimeString();
		StringBuffer upsetTimeString = getUpsetTimeString(timeString);
		StringBuffer keyString = getKeyString(upsetTimeString);
		return keyString;
	} 
	
	public static void main(String[] args) {
		new ExclusiveKeyServiceImpl().getAPIKey();
	}
	
	/**
	 * 将混乱好的包含时间信息的字符串传进来，然后创建一个包含有时间信息的APIKey
	 * @param upsetTimeString 包含时间信息的字符串
	 * @return
	 */
	private StringBuffer getKeyString(StringBuffer upsetTimeString) {
		Random random = new Random();
		String keyS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#$%^_~-+";
		char[] cKey = keyS.toCharArray();
		StringBuffer KeyString = new StringBuffer();
		for( int i = 0; i < 40; i ++) { 
			if((i==10||i==20||i==30)&&upsetTimeString.length()>0){
				System.out.println(upsetTimeString.substring(0, 13));
				KeyString.append(upsetTimeString.substring(0, 13));
				upsetTimeString.delete(0, 13);
				System.out.println(upsetTimeString.length());
			}
			KeyString.append(cKey[random.nextInt(cKey.length)]);
        }
System.out.println(KeyString.toString());
System.out.println("APIKey长度："+KeyString.length());
System.out.println("可选字符长度："+keyS.length());
System.out.println();
		return KeyString;
	}

	/**
	 * 返回一个跟时间混合混乱排序的39位字符串，
	 * @param timeString 所要跟时间字符串混合的26位字符串
	 * @return
	 */
	private StringBuffer getUpsetTimeString(StringBuffer timeString) {
		Random random = new Random();
		StringBuffer time = new StringBuffer();
		time.append(new Date().getTime());
		System.out.println(time.toString());
		StringBuffer upset = new StringBuffer();
		String[] str = new String[39];
		for(int i=0;i<13;i++){
			int j = random.nextInt(str.length);
			while(str[j]!=null){
				j = random.nextInt(str.length);
			}
			str[j]="yes";
		}
		for(int i=0;i<str.length;i++){
			if(str[i]==null){
				upset.append(timeString.charAt(0));
				timeString.deleteCharAt(0);
			}else{
				upset.append(time.charAt(0));
				time.deleteCharAt(0);
			}
		}
		System.out.println(upset.toString());
		return upset;
	}

	/**
	 * 返回一个26位随机产生的字符串
	 * 所要返回的字符串从"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$%^_~-+"中获取
	 * @return StringBuffer
	 */
	public StringBuffer getTimeString(){
		Random random = new Random();
		
		//System.out.println(time.toString());
		String timecharacter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$%^_~-+";
		char[] timechar = timecharacter.toCharArray();
		StringBuffer timeString = new StringBuffer();
		for( int i = 0; i < 26; i ++) { 
			timeString.append(timechar[random.nextInt(timechar.length)]);
        }
		System.out.println(timeString.toString());
		return timeString;
	}

	public ExclusiveKeyManage getExclusiveKeyManage() {
		return exclusiveKeyManage;
	}
	@Resource(name="exclusiveKeyManage")
	public void setExclusiveKeyManage(ExclusiveKeyManage exclusiveKeyManage) {
		this.exclusiveKeyManage = exclusiveKeyManage;
	}

	public KebsiteManage getKebsiteManage() {
		return kebsiteManage;
	}
	@Resource(name="kebsiteManage")
	public void setKebsiteManage(KebsiteManage kebsiteManage) {
		this.kebsiteManage = kebsiteManage;
	}

	public AprioriRunner getAprioriRunner() {
		return aprioriRunner;
	}

	@Resource(name="aprioriRunner")
	public void setAprioriRunner(AprioriRunner aprioriRunner) {
		this.aprioriRunner = aprioriRunner;
	}

}
