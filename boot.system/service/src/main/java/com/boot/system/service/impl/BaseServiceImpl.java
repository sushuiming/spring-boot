package com.boot.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.boot.system.dataorm.config.PageBean;
import com.boot.system.dataorm.entity.BaseEntity;
import com.boot.system.dataorm.mapper.BaseMapper;
import com.boot.system.service.BaseService;


/**
* <pre>
* BaseServiceImpl<T 为表对应的实现类, ID 为该表中id的数据类型（一般为String）类，
* 是BaseService接口的实现类
*    
* 
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
public class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> 
	implements BaseService<T, ID> {
	
	@Autowired
	private BaseMapper<T, ID> baseMapper;
		
	@Override
	public int insert(String onlineId, T model) {
		model.initIdForInsert(onlineId);
		return baseMapper.insert(model);
	}

	@Override
	public int insertSelective(String onlineId, T model) {
		model.initIdForInsert(onlineId);
		return baseMapper.insertSelective(model);
	}

	@Override
	public int update(String onlineId, T model) {
		model.initCommonFiledsForUpdate(onlineId);
		return baseMapper.update(model);
	}

	@Override
	public int updateSelective(String onlineId, T model) {
		model.initCommonFiledsForUpdate(onlineId);
		return baseMapper.updateSelective(model);
	}

	@Override
	public int delete(ID id) {
		return baseMapper.delete(id);
	}

	@Override
	public int deleteBatch(List<ID> ids) {
		return baseMapper.deleteBatch(ids);
	}

	@Override
	public T getEntityById(ID id) {
		return baseMapper.getEntityById(id);
	}	

	@Override
	public List<T> getList(Map<String, Object> map) {
		return baseMapper.getList(map);
	}
	
	@Override
	public Map<String, Object> getListByPage(Map<String, Object> map, 
			int currentPage, int pageSize) {
		
		Map<String, Object> body = new HashMap<String, Object> ();
		List<T> list = new ArrayList<T> ();

		int totalRecord = baseMapper.getCount(map).intValue(); //总记录数       
        if (totalRecord == 0) {  // 当总记录为0时，直接返回
        	body.put("totalPage", 0);
            body.put("totalRecord", 0);
            body.put("list", list);
            return body;
        }

        PageBean pb = new PageBean(currentPage, pageSize, totalRecord);
        map.put("startIndex", pb.getStartIndex());
        map.put("pageSize", pageSize);       
        list = baseMapper.getList(map);
		// pb = new PageBean(currentPage, pageSize, totalRecord); // 重新计算totalPage
        body.put("totalPage", pb.getTotalPage());
        body.put("totalRecord", totalRecord);
        body.put("list", list);
        
		return body;
	}
	
	@Override
	public Long getCount(Map<String, Object> map) {
		return baseMapper.getCount(map);
	}
		
}
