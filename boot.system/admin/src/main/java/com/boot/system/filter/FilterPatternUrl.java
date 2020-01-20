package com.boot.system.filter;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 * 自定义配置文件的解析类
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

/*
 * 自定义配置文件的解析类
 */
@ConfigurationProperties(prefix = "filter.exclude")
public class FilterPatternUrl {
	
    private List<String> urlPatterns;

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }
}
