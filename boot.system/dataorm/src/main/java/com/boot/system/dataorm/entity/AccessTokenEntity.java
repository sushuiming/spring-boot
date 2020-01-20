package com.boot.system.dataorm.entity;

/**
 * 
 * <pre>
 * Token的返回结果。
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

public class AccessTokenEntity {
	
	private String accessId;
	
    private String accessToken;  
     
    private long expiresIn;
    
    public AccessTokenEntity() {}
    
    public AccessTokenEntity(String accessId, String accessToken, long expiresIn) {
    	this.accessId = accessId;
    	this.accessToken = accessToken;
    	this.expiresIn = expiresIn;
    }
      
    public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessToken() {  
        return accessToken;  
    }  
    
    public void setAccessToken(String accessToken) {  
        this.accessToken = accessToken;  
    }   
    
    public long getExpiresIn() {  
        return expiresIn;  
    } 
    
    public void setExpiresIn(long expiresIn) {  
        this.expiresIn = expiresIn;  
    } 
    
}