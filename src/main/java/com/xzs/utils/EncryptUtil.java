package com.xzs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * @Description:TODO
 * @exception:
 * @author: 徐正顺
 * @time:2017年10月31日 下午3:23:03
 */
public class EncryptUtil {

	/**
	 * sha256
	 * @param strText
	 * @return
	 */
	public static String sha256(String strText){
		return encrypt(strText, "SHA-256");
	}
	
	/**
	 * 加密方法
	 * @param strText	需要加密的字符串
	 * @param strType	加密类型
	 * @return
	 */
	public static String encrypt(String strText, String strType){
		if(strText == null || "".equals(strText)){
			return "";
		}
		
		try {
			MessageDigest md = MessageDigest.getInstance(strType);
			md.update(strText.getBytes());
			byte[] strBuffer = md.digest();
			
			StringBuffer strHexString = new StringBuffer();  
	        for (int i = 0; i < strBuffer.length; i++)  
	        {  
	          String hex = Integer.toHexString(0xff & strBuffer[i]);  
	          if(hex.length() == 1){  
	            strHexString.append('0');
	          }  
	          strHexString.append(hex);
	        }
	        return strHexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
