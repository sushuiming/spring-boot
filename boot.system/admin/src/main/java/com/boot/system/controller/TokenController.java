package com.boot.system.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.system.dataorm.config.CheckParamUtil;
import com.boot.system.dataorm.config.ResultMsg;
import com.boot.system.dataorm.entity.AccessTokenEntity;
import com.boot.system.dataorm.enums.ResultStatusCode;
import com.boot.system.service.TokenService;

/**
 * 
 * <pre>
 * Token公共服务主入口。
 * </pre>
 * 
 * @author sushuiming
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

@Controller
@RequestMapping(value = "/token")
public class TokenController {

	private final static Log log = LogFactory.getLog(TokenController.class);
	
	 //超时时间
    @Value("${spring.redis.timeout}")
    private long timeout;

	@Autowired
	private TokenService tokenService;
	
	/**
    *
    * <pre>
    *      根据id和token保存token
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map 	
    * @return resultMsg 
    * 	resultMsg =	{errorCode: 0, errorMsg: "success", body: {accessToken: }}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/saveToken", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg saveToken(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;		
		try {			
			String id = (String) CheckParamUtil.checkMap(map, "id", true);
			String token = (String) CheckParamUtil.checkMap(map, "token", true);
			
			boolean res = tokenService.saveToken(id, token);
			if (!res) { // 保存token失败
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_SAVE_TOKEN.getErrorCode(), 
						ResultStatusCode.INVALID_SAVE_TOKEN.getErrorMsg(), null);
				return resultMsg;
			} 
			Map<String, Object> body = new HashMap<String, Object> ();
			AccessTokenEntity accessToken = new AccessTokenEntity(id, token, timeout);
			body.put("accessToken", accessToken);
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
    *      根据id删除token接口
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map 	
    * @return resultMsg 
    * 	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/deleteToken", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg deleteToken(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;		
		try {			
			String id = (String) CheckParamUtil.checkMap(map, "id", true);
			String token = (String) CheckParamUtil.checkMap(map, "token", true);
			boolean res = tokenService.deleteToken(id, token);
			if (res) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
						ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_DELETE_TOKEN.getErrorCode(), 
						ResultStatusCode.INVALID_DELETE_TOKEN.getErrorMsg(), null);
			}			
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
    *      token定时检查接口
    * </pre>
    *
    *<pre>
    * @author sushuiming
    * @date 2019-05-01
    * @version 1.00.01
    * </pre>    
    * @param request
    * @param map 	
    * @return resultMsg 
    * 	resultMsg =	{errorCode: 0, errorMsg: "success", body: null}
    * @throws 
    */
	@ResponseBody
	@RequestMapping(value = "/checkToken", method = RequestMethod.POST, produces = "application/json")
	public ResultMsg checkToken(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		ResultMsg resultMsg = null;		
		try {			
			String id = (String) CheckParamUtil.checkMap(map, "id", true);
			String token = (String) CheckParamUtil.checkMap(map, "token", true);
			boolean res = tokenService.checkToken(id, token);
			if (res) {
				resultMsg = new ResultMsg(ResultStatusCode.SUCCESS.getErrorCode(), 
						ResultStatusCode.SUCCESS.getErrorMsg(), null);
			} else {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_VALIDATE_TOKEN.getErrorCode(), 
						ResultStatusCode.INVALID_VALIDATE_TOKEN.getErrorMsg(), null);
			}			
		} catch (Exception e) {
			log.error(String.format("System error: %s", e.getMessage()));
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrorCode(), 
					e.getMessage(), null);
		}
		return resultMsg;
	}


}
