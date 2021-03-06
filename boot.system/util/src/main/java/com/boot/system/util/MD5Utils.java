package com.boot.system.util;

import java.security.MessageDigest;

public class MD5Utils {
	
	/**
	  * @功能:MD5加密算法(大写)
      * @参数:需要加密的字符串
	  */
	public final static String getMD5(String s) {
		return getMD5(s, true);
    }
	
	/**
	  * @功能:MD5加密算法
      * @参数:需要加密的字符串
      * @参数:是否为大写
	  */
	public final static String getMD5(String s, boolean isUpperCase) {		
		String result = null;
		char[] hexDigits = getHexDigits(isUpperCase);       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            return null;
        }
        return result;		
	}
	
	private final static char[] getHexDigits(boolean isUpperCase) {		
		if (isUpperCase) {
			return new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		}
		return new char[] {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};	
	}

}
