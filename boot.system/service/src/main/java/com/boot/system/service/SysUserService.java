package com.boot.system.service;

import com.boot.system.dataorm.entity.SysUserEntity;

public interface SysUserService extends BaseService<SysUserEntity, String> {

	SysUserEntity getUserByOpenId(String openId);

	SysUserEntity getUserByMobile(String mobile);

	String getCodeByMobile(String onlineId, String mobile);
	
	boolean checkCodeByMobile(String mobile, String code);
	
}
