package com.boot.system.dataorm.mapper;

import com.boot.system.dataorm.entity.SysUserEntity;

public interface SysUserMapper extends BaseMapper<SysUserEntity, String> {

	SysUserEntity getUserByOpenId(String openId);

	SysUserEntity getUserByMobile(String mobile);
   
}
