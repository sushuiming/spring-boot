package com.boot.system.dataorm.config;

import java.util.List;

/**
 * <pre>
 * 树行接口
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
public interface Tree<T> {

	String getId();

	void setId(String id);

	String getParentId();

	void setParentId(String parentId);

	List<T> getChildrens();

	void setChildrens(List<T> children);
	
}
