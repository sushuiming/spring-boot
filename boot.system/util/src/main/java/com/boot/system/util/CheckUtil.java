package com.boot.system.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 工具校验类
 * </pre>
 *
 */

public class CheckUtil {
	
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean isNotEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	  * 身份证号码
	  * @param email
	  * @return 格式正确返回true，否则返回false
	  */
	 public static boolean isIdNumber(String idNumber) {		 
	     boolean flag = false;
	     try {	    		  
	    	 Pattern regex = Pattern.compile("(^\\\\d{15}$)|(\\\\d{17}(?:\\\\d|x|X)$)");
	    	 Matcher matcher = regex.matcher(idNumber);
	    	 flag = matcher.matches();
	   } catch(Exception e) {		   
		   flag = false;
	   }
	   return flag;
	 }
	
	/**
	  * 验证邮箱
	  * @param email
	  * @return 格式正确返回true，否则返回false
	  */
	 public static boolean isEmail(String email) {		 
	     boolean flag = false;
	     try {	    		  
	    	 Pattern regex = Pattern
	    			 .compile("^([a-z0-9A-Z]+[-|_|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$");
	    	 Matcher matcher = regex.matcher(email);
	    	 flag = matcher.matches();
	   } catch(Exception e) {		   
		   flag = false;
	   }
	   return flag;
	 }
	
	 /**
	  * 验证手机号码
	  * @param mobile
	  * @return 格式正确返回true，否则返回false
	  */
	 public static boolean isMobile(String mobile) {
		 boolean flag = false;
		 try {
		     Pattern regex = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
		     Matcher matcher = regex.matcher(mobile);
		     flag = matcher.matches();
		  } catch (Exception e) {			 
		      flag = false;
		  }
		  return flag;
	 }

	 /**
	  * 验证电话号码
	  * @param phoner
	  * @return 格式正确返回true，否则返回false
	  */
	 public static boolean isPhone(String phone) {
		 boolean flag = false;
		 try {
		     Pattern regex = Pattern.compile("^([0-9]+[-|+|_]?)+");
		     Matcher matcher = regex.matcher(phone);
		     flag = matcher.matches();
		 } catch (Exception e) {			 
		     flag = false;
		 }
		 return flag;
	 }
	 
	/**
	 * 判断字符串是否是正整数
	 * @param value
	 * @return 格式正确返回true，否则返回false
	 */
	public static boolean isInteger(String value) {
		return value.matches("[0-9]+");		
	}
	
	/**
	 * 判断字符串是否是浮点数
	 * @param value
	 * @return 格式正确返回true，否则返回false
	 */
	public static boolean isDouble(String value) {
		boolean flag = false;
		try {
		  Double.parseDouble(value);
		  if (value.contains(".")) {
			  flag = true;
		  }		  
		} catch (NumberFormatException e) {
			flag = false;
	  	}
		return flag;
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param value
	 * @return 格式正确返回true，否则返回false
	 */
	public static boolean isNumber(String value) {
	    return isInteger(value) || isDouble(value);
	}
	
}
