package com.boot.system.service;

/**
 * <pre>
 * 	token接口。提供token统一管理
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

public interface TokenService {
	   
    boolean saveToken(String id, String token);
   
    boolean deleteToken(String id, String token);
    
    boolean checkToken(String id, String token);
    
    String getTokenById(String id);

	boolean saveToken(String id, String token, int timeout);
       
}
