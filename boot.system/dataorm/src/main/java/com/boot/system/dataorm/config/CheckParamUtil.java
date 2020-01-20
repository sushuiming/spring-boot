package com.boot.system.dataorm.config;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 	校验参数工具类
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

public class CheckParamUtil {	
		
	/**
    *
    * <pre>
    *      校验的集合map
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param map 需校验的集合
	* @param field map中的某个字段
	* @param required 根据接口需求是否必须填写	
    * @return Object	
    * @throws Exception
    */
	public static Object checkMap(Map<String, Object> map, String field, boolean required) 
			throws ParamException {		
		Object result = null;	
		if (required) { //必须
			if (!map.containsKey(field)) { // 没有key
				throw new ParamException("请求字段" + field + "不存在");
			}
			boolean isEmpty = map.get(field) == null 
					|| "".equals(map.get(field)) 
					|| ((Collection<?>) map.get(field)).isEmpty();
			
			if (isEmpty) { // 没有value
				throw new ParamException(field + "的值不能为空");
			}
			
			result = map.get(field);
		} else { //不必须
			if (map.containsKey(field)) { // 有key
				result = map.get(field);
			} 
		}
		return result;
	}
	
	/**
    *
    * <pre>
    *      校验的实体类obj
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param obj 需校验的实体类
    * @return void	
    * @throws Exception
    */
	public static void checkObject(Object obj) throws ParamException {
		int flag = 0;
		final Class<?> objClass = obj.getClass();
		Field[] fields = objClass.getDeclaredFields();
		Class<IsCheckField> isCheckField = IsCheckField.class;
		String fieldName = null;
		String fileType = null;
		for (Field field : fields) {	
			// 获取字段是否需要填写
			boolean required = field.isAnnotationPresent(isCheckField) 
					&& field.getAnnotation(isCheckField).required(); 
			
			if (required) {
				fieldName = field.getName();				
				field.setAccessible(true);
				fileType = field.getType().getTypeName();
				if ("String".contains(fileType)) {						
					try {
						String fValue = (String) field.get(obj);// 转换异常
						if (fValue == null || "".equals(fValue)) {
							flag = -1;
						}
					} catch (Exception e) {
						flag = -2;
					}					
				} else if ("Double".contains(fileType)) {
					try {
						Double fValue = (Double) field.get(obj);
						if (fValue == null) {
							flag = -1;
						}
					} catch (Exception e) {
						flag = -2;
					}
				} else if ("Integer".contains(fileType)) {
					try {
						Integer fValue = (Integer) field.get(obj);
						if (fValue == null) {
							flag = -1;
						}
					} catch (Exception e) {
						flag = -2;
					}
				} else if ("Date".contains(fileType)) {
					try {
						Date fValue = (Date) field.get(obj);
						if (fValue == null) {
							flag = -1;
						}
					} catch (Exception e) {
						flag = -2;
					}
				} else if ("List".contains(fileType)) {
					try {
						List<?> fValue = (List<?>) field.get(obj);								
						if (fValue == null || fValue.isEmpty()) {
							flag = -1;
						}
					} catch (Exception e) {
						flag = -2;
					}
				} else {
					try {
						Object fValue = (Object) field.get(obj);								
						if (fValue == null) {
							flag = -1;
						}
					} catch (Exception e) {
						flag = -2;
					}
				}
				
				if (flag == -1) {
					throw new ParamException(fieldName + "必须填写");
				} else if (flag == -2) {
					// 目标double类型，请求Srtring,强转失败
					throw new ParamException(fieldName + "输入格式不正确");
				}
				
			}	
		}
	}		
	
}
