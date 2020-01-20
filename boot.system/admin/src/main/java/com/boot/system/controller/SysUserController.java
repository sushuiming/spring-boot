package com.boot.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.system.dataorm.config.CheckParamUtil;
import com.boot.system.dataorm.config.ParamException;
import com.boot.system.dataorm.config.ResultMsg;
import com.boot.system.dataorm.entity.SysUserEntity;
import com.boot.system.dataorm.enums.ResultStatusCode;
import com.boot.system.service.SysUserService;


@Controller
@RequestMapping(value = "/sysUser")
public class SysUserController extends BaseController<SysUserEntity, String> { // 

	private final static Log log = LogFactory.getLog(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    
    /**
    *
    * <pre>
    *      根据openId，获取一条记录
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map {"id": "this is get entity id"}	
    * @return resultMsg
    *  	resultMsg =	{errorCode: 0, errorMsg: "success", body: {entity: {}}}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/getUserByOpenId", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg getUserByOpenId(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;
		try {	
			Map<String, Object> body = new HashMap<String, Object> ();
			String openId = (String) CheckParamUtil.checkMap(map, "openId", true);
			SysUserEntity userInfo = sysUserService.getUserByOpenId(openId);
			body.put("userInfo", userInfo);
			resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
				ResultStatusCode.SUCCESS.getErrorMsg(), body);
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}	
	
	/**
    *
    * <pre>
    *      根据手机号码获取4位验证码，并保存在redis中
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param mobile	
    * @return resultMsg
    *	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/getCodeByMobile", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg getCodeByMobile(HttpServletRequest request, @RequestBody Map<String, Object> map) {	
		ResultMsg resultMsg = null;
		try {				
			String onlineId = request.getHeader("id");
			String mobile = (String) CheckParamUtil.checkMap(map, "mobile", true);	
			
			if (sysUserService.getUserByMobile(mobile) != null) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);
				return resultMsg;
			}
			
			// 发送验证码，并保存到redis中，有效期为60秒
			String resultCode = sysUserService.getCodeByMobile(onlineId, mobile);
			if ("0".equals(resultCode)) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
					    ResultStatusCode.SUCCESS.getErrorMsg(), null);	
			} else {
				Map<String, Object> body = new HashMap<String, Object> ();
				body.put("resultCode", resultCode);
				resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					    ResultStatusCode.SYSTEM_ERR.getErrorMsg(), body);	
			}				
		} catch (ParamException e) {
			log.error(String.format("Param error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.PARAM_ERR.getErrorCode(), 
					e.getMessage(), null);
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}

}
