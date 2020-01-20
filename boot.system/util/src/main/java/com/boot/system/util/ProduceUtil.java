package com.boot.system.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ProduceUtil {
	
	/** 
	 * @功能:生成随机n位随机数
     */
	public static String randomNum(int n) {		
		int length = n - 1;
		int number = (int) ((Math.random() * 9 + 1) * Math.pow(10, length));
		return String.valueOf(number);
	}	
	    
    /**
     * @功能:生成32位UUID字符串(大写)
     */
    public static String UUID(){
    	return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
	
    
    /**
     * @功能:生成yyyyMMddHHmmssSSS字符串
     */
    public static String nowDate(){
    	SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String nowDate = s.format(new Date());
		return nowDate;
    }
    
    /**
     * @功能:生成yyyyMMddHHmmssSSS + UUID.hashCode字符串,可用于订单号
     */
    public synchronized String outTradeNo() {
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        if (uuidHashCode < 0) {
            uuidHashCode = uuidHashCode * (-1);
        }
        String date = nowDate();
        return date + uuidHashCode;
    }
    
}

