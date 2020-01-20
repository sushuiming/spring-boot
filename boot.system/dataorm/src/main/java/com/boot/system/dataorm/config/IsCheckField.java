package com.boot.system.dataorm.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 	是否需要填写字段注解
 * </pre>
 *
 * @author sushuiming
 * @date 2019-05-01
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  //指定了注解保留的周期 
public @interface IsCheckField {

	/**
	 * 字段是否需要填写,默认为true
	 */
	boolean required() default true;
	
}
