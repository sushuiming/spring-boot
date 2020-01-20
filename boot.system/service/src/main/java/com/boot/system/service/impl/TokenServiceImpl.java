package com.boot.system.service.impl;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.boot.system.service.TokenService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class TokenServiceImpl implements TokenService {

	private final static Log log = LogFactory.getLog(TokenServiceImpl.class);
	
	@Value("${spring.redis.timeout}")
	private int timeout;	
	
	@Autowired
	private JedisPool jedisPool;


    @Override
    public boolean saveToken(String id, String token) {
    	return saveToken(id, token, this.timeout);
    }
    
    @Override
    public boolean saveToken(String id, String token, int timeout) {
    	boolean result = false;
        try {     
        	Jedis jedis = getJedis();        	
        	jedis.set(id, token);            
        	jedis.expire(id, timeout); //设置生存时间
            log.debug("redis保存成功");
            result = true;
        } catch (Exception e) {        
            log.debug("redis保存失败：" + e.getMessage());
            result = false;
        } 
        return result;
    }


    @Override
    public boolean deleteToken(String id, String token) {    
    	boolean result = false;       
        String tempToken = getToken(id); //redis 查询
        if (tempToken != null && tempToken.equalsIgnoreCase(token)) {
        	result = delJedis(id);          	
        } 
        return result;
    }

    @Override
    public boolean checkToken(String id, String token) {
    	boolean result = false;
        if (!StringUtils.hasText(id) || !StringUtils.hasText(token)) {
            return result;
        }
    
        String tempToken = getToken(id); //redis 查询
        if (tempToken != null && tempToken.equalsIgnoreCase(token)) {
        	result = true;
        }
        return result;
    }

    @Override
    public String getTokenById(String id) {
        String token = null;       
        String tempToken = getToken(id); //redis 查询
        if (tempToken != null && !"".equals(tempToken)) {
            token = tempToken;
            setExpire(id);
        }
        return token;
    }
 
    /**
     * 
     * 设置生效时间
     * @return
     * @throws Exception Jedis
     */
    private boolean setExpire(String key) {
        boolean result = false;
        try {
            //设置生存时间
        	 getJedis().expire(key, timeout);
            log.error("redis设置生效时间成功：key=" + key);
            result = true;
        } catch (Exception e) {
            log.error("redis设置生效时间失败：key=" + key);
            result = false;
        }  
        return result;
    }

    /**
     *
     * 删除token
     * @return boolean
     * @throws 
     */
    private boolean delJedis(String key) {
        boolean result = false;
        try {                       
        	getJedis().del(key); //删除token
        	log.error("redis删除token成功：key=" + key); 
            result = true;
        } catch (Exception e) {       	
            log.error("redis删除token失败：key=" + key);
            result = false;
        } 
        return result;
    }

    /**
    *
    * 根据key获取Jedis中的value
    * @return String
    * @throws 
    */
    private String getToken(String key) {     
        String token = null;
        try {                       
            token = getJedis().get(key);    
            log.debug("redis查询成功：token=" + token);
        } catch (Exception e) {
            log.debug("redis查询失败：" + e.getMessage());           
        } 
        return token;

    }

    /**
    *
    * 获取Jedis
    * @return Jedis
    * @throws 
    */
    private Jedis getJedis() {    	
    	return jedisPool.getResource();
    }

}
