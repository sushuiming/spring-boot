package com.boot.system.dataorm.entity;

import java.util.Date;
import java.util.UUID;

/**
 * <pre>
 * BaseEntity 类：
   *     提供数据库表中常用字段id, createUser等抽象的get/set方法。   
   *     提供了   initIdForInsert
   *     	  initCommonFiledsForInsert
   *     	  initCommonFiledsForUpdate            
 * </pre>
 *
 * @author sushuiming
 * @date 2019-05-01
 * @version 1.00.00
 * 
 */

public abstract class BaseEntity {
		
	public abstract String getId();
	
	public abstract void setId(String id);
	
	public abstract String getCreateUser();
	
	public abstract void setCreateUser(String createUser);
	
    public abstract Date getCreateDate();

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    public abstract void setCreateDate(Date createDate);

    public abstract String getUpdateUser();

    public abstract void setUpdateUser(String updateUser);

    public abstract Date getUpdateDate();

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    public abstract void setUpdateDate(Date updateDate);
    
    public void initIdForInsert(String onlineId) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        this.setId(uuid);
        this.initCommonFiledsForInsert(onlineId);
    }

    public void initCommonFiledsForInsert(String onlineId) {		
        if (onlineId != null && !"".contentEquals(onlineId)) {
            setCreateUser(onlineId);
            setUpdateUser(onlineId);
			setCreateDate(new Date());
            setUpdateDate(new Date());			
        } 
    }

    public void initCommonFiledsForUpdate(String onlineId) {		
        if (onlineId != null && !"".contentEquals(onlineId)) {
            setUpdateUser(onlineId);
            setUpdateDate(new Date());            
        }
    }		
	
}
