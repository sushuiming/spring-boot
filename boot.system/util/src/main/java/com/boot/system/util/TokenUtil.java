package com.boot.system.util;


/**
 * <pre>
 * 	加密token工具类
 * </pre>
 *
 * @author ssm
 * @date 2019-05-01
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class TokenUtil {
	
	public static final String TOKEN_KEY = "sushuiming";
	
	public static String createToken(String id, String password) {
		
		long expires = System.currentTimeMillis();
		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(id);
		tokenBuilder.append(":");
		tokenBuilder.append(password);
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TOKEN_KEY);		
		
		return MD5Utils.getMD5(tokenBuilder.toString());
	}
		
}
