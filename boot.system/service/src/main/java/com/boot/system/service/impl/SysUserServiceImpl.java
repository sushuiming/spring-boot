package com.boot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.system.dataorm.entity.SysUserEntity;
import com.boot.system.dataorm.mapper.SysUserMapper;
import com.boot.system.service.SysUserService;
import com.boot.system.service.TokenService;
import com.boot.system.util.MD5Utils;
import com.boot.system.util.ProduceUtil;
import com.boot.system.util.SendMessageUtil;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, String> 
	implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private TokenService tokenService;

	@Override
	public SysUserEntity getUserByOpenId(String openId) {
		return sysUserMapper.getUserByOpenId(openId);
	}

	@Override
	public SysUserEntity getUserByMobile(String mobile) {
		return sysUserMapper.getUserByMobile(mobile);
	}

	@Override
	public String getCodeByMobile(String onlineId, String mobile) {
		String result = "-1";		
		String code = ProduceUtil.randomNum(4); // 获取4位数，随机码
		String id = MD5Utils.getMD5(mobile + "_" + code);
		String token = MD5Utils.getMD5(code);		
		if (tokenService.saveToken(id, token, 60)) { // 有限时长为60s	
			String message = "【XX系统】您的验证码是" + code + ", 请在60秒内输入。";
			result = SendMessageUtil.sendSms(mobile, message);
		}		
		return result;
	}

	@Override
	public boolean checkCodeByMobile(String mobile, String code) {
		String id = MD5Utils.getMD5(mobile + "_" + code);
		String token = MD5Utils.getMD5(code);
		return tokenService.checkToken(id, token);
	}
    
}
