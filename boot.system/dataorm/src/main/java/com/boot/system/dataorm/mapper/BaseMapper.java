package com.boot.system.dataorm.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <pre>
 * BaseMapper 接口：提供常用的数据库操。T 为表对应的实现类，ID 为该表中id的数据类型（一般为String）
 * 
 * 1. 添加
 *   1.1 普通添加 insert(model)
 *   1.2 选择性添加  insertSelective(model)
 *   1.3 添加多条记录  insertBatch(list)
 * 2. 修改
 *   2.1 普通修改 update(model)
 *   2.2 选择性修改 updateSelective(model)
 * 3. 删除
 *   3.1 根据ID删除一条记录 delete(id)
 *   3.2 根据IDS删除多条记录  deleteBatch(ids)
 * 4. 查询
 *   4.1 根据ID查询一条记录 selectByPrimaryKey(id)
 *   4.2 获取全部记录 getList()
 *   4.3 模糊查询获取全部记录 getListByMap(map)
 * 5. 统计
 *   5.1 获取表中总记录数 getCount()
 *   5.2 模糊查询获取总记录数 getList()
 * 6. 更多数据库操作，需要根据各层子接口实现       
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

public interface BaseMapper<T extends Object, ID extends Serializable> {    	
			
	int insert(T model);
	
	int insertSelective(T model);
	
	int insertBatch(List<T> list);
	
	int update(T model);
	
	int updateSelective(T model);
	
	int delete(@Param("id") ID id);
	
	int deleteBatch(@Param("ids") List<ID> ids);
	
	T getEntityById(@Param("id") ID id);
		
	List<T> getList(Map<String, Object> map);

	Long getCount(Map<String, Object> map);			    
    
}
