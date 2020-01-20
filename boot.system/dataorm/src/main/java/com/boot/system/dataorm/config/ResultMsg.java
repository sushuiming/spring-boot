package com.boot.system.dataorm.config;

import java.util.Map;

/**
 * <pre>
 * 结果描述类型
 * </pre>
 *
 * @author sushuiming
 * @date 2018年6月9日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class ResultMsg { 
	
	private int errorCode;  
    private String errorMsg;  
    private Map<String, Object> body;  
    
    public ResultMsg() {}	
    
    public ResultMsg(int errorCode, String errorMsg, Map<String, Object> body) {  
        this.errorCode = errorCode;  
        this.errorMsg = errorMsg;  
        this.body = body;  
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
    
    public Map<String, Object> getBody() {  
        return body;  
    }  
    
    public void setBody(Map<String, Object> body) {  
        this.body = body;  
    }  
    
}  
