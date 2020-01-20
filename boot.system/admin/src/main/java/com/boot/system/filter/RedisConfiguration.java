package com.boot.system.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <pre>
 * redis数据库连接池配置
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

@Configuration
@EnableCaching
public class RedisConfiguration {
    Logger log = LoggerFactory.getLogger(RedisConfiguration.class);
    //服务器地址
    @Value("${spring.redis.host}")
    private String host;
    
    //端口号
    @Value("${spring.redis.port}")
    private int port;

    //超时时间
    @Value("${spring.redis.timeout}")
    private int timeout;
    
    //最大连接数
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    //最大等待时间
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
    
    //密码
    @Value("${spring.redis.password}")
    private String password;
   
    @Bean
    public JedisPool redisPoolFactory() {
    	log.info("JedisPool注入成功！！");
    	log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }

}
