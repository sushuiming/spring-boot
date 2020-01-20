package com.boot.system.service;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * BaseService<T 为表对应的实现类, ID 为该表中id的数据类型（一般为String）> 接口：提供常用的数据库操。
 * 
 * 1. 添加
 *   1.1 普通添加 insert(onlineId, model)
 *   1.2 根据key添加  insertSelective(onlineId, model)
 * 2. 修改
 *   2.1 普通修改 update(onlineId, model)
 *   2.2 根据key修改 updateSelective(onlineId, model)
 * 3. 删除
 *   3.1 根据ID删除一条记录 delete(id)
 *   3.2 根据IDS删除多条记录  deleteBatch(ids)
 * 4. 查询
 *   4.1 根据ID查询一条记录 getEntityById(id)
 *   4.2 获取全部记录 getList()
 *   4.3 模糊查询获取全部记录 getListByMap(map)
 * 5. 统计
 *   5.1 获取表中总记录数 getCount()
 *   5.2 模糊查询获取总记录数 getCountByMap(map)
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
public interface BaseService<T extends Object, ID extends Serializable> {
	
	int insert(String onlineId, T model);
	
	int insertSelective(String onlineId, T model);
	
	int update(String onlineId, T model);
		
	int updateSelective(String onlineId, T model);
	
	int delete(ID id);
	
	int deleteBatch(List<ID> ids);
	
	T getEntityById(ID id);		
		
	List<T> getList(Map<String, Object> map);
	
	Map<String, Object> getListByPage(Map<String, Object> map,
			int currentPage, int pageSize);

	Long getCount(Map<String, Object> map);
		
}
