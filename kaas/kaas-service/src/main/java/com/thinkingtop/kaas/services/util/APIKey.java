package com.thinkingtop.kaas.services.util;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("apiKey")
public class APIKey {
	static Logger logger=Logger.getLogger(APIKey.class);
	/**
	 * Create a APIKey and returns
	 * @return StringBuffer:The returned APIKey
	 */
	public StringBuffer getAPIKey(){
		StringBuffer timeString = getTimeString();
		StringBuffer upsetTimeString = getUpsetTimeString(timeString);
		StringBuffer keyString = getKeyString(upsetTimeString);
		return keyString;
	} 
	
	
	/**
	 * Create a APIKey containing specific information
	 * @param upsetTimeString:String containing the message
	 * @return APIKey
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
logger.info(KeyString.toString());
logger.info("");
		return KeyString;
	}

	/**
	 * Returns a string of 39 mixed with information
	 * @param timeString A mixture of 26 characters
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
logger.info(upset.toString());
		return upset;
	}

	/**
	 * Returns a 26 bit random string
	 * Strings on "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$%^_~-+"
	 * @return StringBuffer
	 */
	public StringBuffer getTimeString(){
		Random random = new Random();
		
		String timecharacter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$%^_~-+";
		char[] timechar = timecharacter.toCharArray();
		StringBuffer timeString = new StringBuffer();
		for( int i = 0; i < 26; i ++) { 
			timeString.append(timechar[random.nextInt(timechar.length)]);
        }
logger.info(timeString.toString());
		return timeString;
	}
}
