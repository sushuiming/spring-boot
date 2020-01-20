package com.boot.system.util;

import java.math.BigDecimal;

/**
 * <pre>
 * 	Double计算工具类，用于精确计算
 * </pre>
 *
 * @author sushuiming
 * @date 2019-10-28
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class DoubleUtil {
	
	private final static int DEFAULT_SACLE = 2;  // 默认保留后2位小数
	
	/**
    *
    * <pre>
    *      两个Double相加
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param a
    * @param b
    * @return a + b
    * @throws 
    */
	public static Double add(Double a, Double b) {
		return adds(a, b);
	}	
	
	/**
    *
    * <pre>
    *      多个Double相加
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param values
    * @return values[0] + values[1] + ...
    * @throws 
    */
	public static Double adds(Double... values) {
		BigDecimal result = new BigDecimal(Double.toString(values[0]));
		for (int i = 1, length = values.length; i < length; i++) {
			result = result.add(new BigDecimal(Double.toString(values[i])));
		}		
		return result.doubleValue();
	}
	
	/**
    *
    * <pre>
    *      两个Double相减
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param a
    * @param b
    * @return a - b
    * @throws 
    */
	public static Double subtract(Double a, Double b) {
		return subtracts(a, b);
	}
	
	/**
    *
    * <pre>
    *      多个Double相减
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param values
    * @return values[0] - values[1] - ...
    * @throws 
    */
	public static Double subtracts(Double... values) {
		BigDecimal result = new BigDecimal(Double.toString(values[0]));
		for (int i = 1, length = values.length; i < length; i++) {
			result = result.subtract(new BigDecimal(Double.toString(values[i])));
		}		
		return result.doubleValue();
	}
	
	/**
    *
    * <pre>
    *      两个Double相乘
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param a
    * @param b
    * @return a * b
    * @throws 
    */
	public static Double multiply(Double a, Double b) {
		return multiplys(a, b);
	}
	
	/**
    *
    * <pre>
    *      多个Double相乘
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param values
    * @return values[0] * values[1] + ...
    * @throws 
    */
	public static Double multiplys(Double... values) {
		BigDecimal result = new BigDecimal(Double.toString(values[0]));
		for (int i = 1, length = values.length; i < length; i++) {
			result = result.multiply(new BigDecimal(Double.toString(values[i])));
		}		
		return result.doubleValue();
	}
	
	/**
    *
    * <pre>
    *      两个Double相除, 保留两为小数
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param a
    * @param b
    * @return a / b
    * @throws 
    */
	public static Double divide(Double a, Double b) {
		return divide(a, b, DEFAULT_SACLE);
	}
	
	/**
    *
    * <pre>
    *      两个Double相除，保留scale位小数
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param a
    * @param b
    * @param scale
    * @return a / b
    * @throws 
    */
	public static Double divide(Double a, Double b, int scale) {
		return divides(scale, a, b);
	}
	
	/**
    *
    * <pre>
    *      多个Double相除，保留scale位小数
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>  
    * @param scale 保留scale位小数  
    * @param values
    * @return values[0] / values[1] / ...
    * @throws 
    */
	public static Double divides(int scale, Double... values) {
		BigDecimal result = new BigDecimal(Double.toString(values[0]));
		for (int i = 1, length = values.length; i < length; i++) {
			result = result.divide(new BigDecimal(Double.toString(values[i])), 
					scale, BigDecimal.ROUND_HALF_UP);
		}		
		return result.doubleValue();
	}
	
	/**
    *
    * <pre>
    *      保留scale位小数,四舍五入
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-10-28
    * @version 1.00.01
    * </pre>    
    * @param value 需要处理的Double
    * @param scale 保留scale位小数
    * @return value
    * @throws IllegalArgumentException
    */
	public static Double round(Double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale必须大于0");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
}
