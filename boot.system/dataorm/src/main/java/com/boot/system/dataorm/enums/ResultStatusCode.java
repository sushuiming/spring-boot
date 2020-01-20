package com.boot.system.dataorm.enums;

/**
 * <pre>
 * 返回结果描述枚举
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
public enum ResultStatusCode {  
	
	SUCCESS(0, "success"),  //成功
		
    SYSTEM_ERR(4001, "异常处理错误"),	//异常处理错误
	
	ERROR_ADD(4020, "添加失败"),  //添加失败
	ERROR_UPDATE(4021, "修改失败"),  //修改失败
	ERROR_DELETE(4022, "删除失败"),  //删除失败
	
	INVALID_WECHAT_CODE(4039, "微信网页授权code失效"),//微信网页授权code失效
    
    INVALID_SAVE_TOKEN(5001, "保存token失败"),	//保存token不成功
    INVALID_VALIDATE_TOKEN(5002, "校验token失效"),	//校验token失效
    INVALID_DELETE_TOKEN(5003, "token删除失败"),	//token删除失败

	PARAM_ERR(6001, "参数入参错误"),//参数入参错误

    INVALID_USER_EMPTY(7001, "用户名不能为空"),	//登录用户不能为空
    INVALID_USER_NOT(7002, "用户不存在"),	//用户不存在
    INVALID_LOGIN_PASSWORD(7003, "登录密码错误"),	//登录密码不正确
    INVALID_USER_DISABLE(7004, "该用户账号状态被禁用"),	//该用户账号状态被禁用
    INVALID_MOBILE_ERROR(7005, "手机号码格式不正确"),	//手机号码格式不正确
    INVALID_PHONE_ERROR(7006, "电话格式不正确"),	//电话格式不正确
    INVALID_EMAIL_ERROR(7007, "邮箱格式不正确"),	//邮箱格式不正确
    INVALID_LOGINNAME_EXIST(7008, "登录名已存在"),	//登录名已存在
    INVALID_PHONE_EXISTS(7009, "手机号已存在"),       //手机号已存在
    INVALID_USER_LEVEL(7010, "登陆用户权限不足"),	//登陆用户权限不足
    INVALID_UPDATE_PASSWORD(7011, "只能修改自己的密码"),	//只能修改自己的密码
    INVALID_ORIGINAL_PASSWORD(7012, "原密码不正确"),	//原密码不正确    
    INVALID_ID_NUMBER_ERROR(7013, "身份证号格式不正确"),	//身份证号格式不正确
    INVALID_ID_NUMBER_EXIST(7014, "身份证号已存在"),	//身份证号格式已存在

    INVALID_RECORD_NULL(11001, "该记录不存在");   //该记录不存在

    private int errorCode;
    private String errorMsg;  
    
    private ResultStatusCode(int ErrorCode, String ErrorMsg) {  
        this.errorCode = ErrorCode;  
        this.errorMsg = ErrorMsg;  
    }  
    
    public int getErrorCode() {  
        return errorCode;  
    }  
  
    public void setErrorCode(int errorCode) {  
        this.errorCode = errorCode;  
    }  
  
    public String getErrorMsg() {  
        return errorMsg;  
    }  
  
    public void setErrorMsg(String errorMsg) {  
        this.errorMsg = errorMsg;  
    } 
    
    
}  
