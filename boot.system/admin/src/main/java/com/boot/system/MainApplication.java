package com.boot.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.boot.system.filter.AuthorizeFilter;
import com.boot.system.filter.FilterPatternUrl;

@Configuration
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = { "com.boot.*.*" })
@MapperScan("com.boot.system.dataorm.mapper")
@EnableScheduling
@EnableConfigurationProperties({ FilterPatternUrl.class })
@EnableZuulProxy
public class MainApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	/**
	 * 
	 * 文件上传配置
	 * 
	 * @return MultipartConfigElement
	 * 
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 文件大小限制
		factory.setMaxFileSize("20MB"); // KB,20MB		
		return factory.createMultipartConfig();

	}

	/**
	 * 
	 * 注册过滤器类和过滤的url
	 * 
	 * @return FilterRegistrationBean
	 * 
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		AuthorizeFilter authorizeFilter = new AuthorizeFilter();
		filterRegistrationBean.setFilter(authorizeFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		return filterRegistrationBean;
	}	
	
	/**
	 * 
	 * 注册启用WebSocket的支持
	 * 
	 * @return ServerEndpointExporter
	 * 
	 */
	@Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }  
	
}