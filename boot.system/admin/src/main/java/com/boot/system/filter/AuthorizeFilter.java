package com.boot.system.filter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.boot.system.dataorm.config.ResultMsg;
import com.boot.system.dataorm.enums.ResultStatusCode;
import com.boot.system.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * token校验过滤器
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
 
public class AuthorizeFilter implements Filter { 

	@Autowired
    private TokenService tokenService;
	
	@Autowired
    private FilterPatternUrl filterPatternUrl;
    
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
        // TODO Auto-generated method stub  
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
                filterConfig.getServletContext());  
          
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  
            throws IOException, ServletException {  
        // TODO Auto-generated method stub  
          
        ResultMsg resultMsg = null;  
        HttpServletRequest httpRequest = (HttpServletRequest)request;  
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String id = httpRequest.getHeader("id");
        String token = httpRequest.getHeader("token");        
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        //如果是属于排除的URL，比如登录，注册，验证码等URL，则直接通行
        if (isInclude(url)){                     
            chain.doFilter(httpRequest, httpResponse);
            return;
        } 
        
        // 如果token有效，则直接通行
        if (StringUtils.hasText(id) && StringUtils.hasText(token)) {  
        	boolean bool = tokenService.checkToken(id, token);
   			if (bool) {
	   			 chain.doFilter(request, response);  
	             return;
            }     			
        }  
        
        //验证不通过
        httpResponse.setCharacterEncoding("UTF-8");    
        httpResponse.setContentType("application/json; charset=utf-8");   
        httpResponse.setStatus(HttpServletResponse.SC_OK);  
  
        //将验证不通过的错误返回  
        ObjectMapper mapper = new ObjectMapper();       
        resultMsg = new ResultMsg(ResultStatusCode.INVALID_VALIDATE_TOKEN.getErrorCode(), 
        		ResultStatusCode.INVALID_VALIDATE_TOKEN.getErrorMsg(), null);  
        httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));  
        return;  
    }  
  
    @Override  
    public void destroy() {  
        // TODO Auto-generated method stub  
    }  
    
    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {   	
		Pattern pattern = Pattern.compile("(/gateway/boot/)");
		Matcher matcher = pattern.matcher(url);//正则匹配网关地址，不过滤
		
		if (matcher.find()) {			
			return true;
		}
    	
    	if (filterPatternUrl.getUrlPatterns().size() > 0) {
    		List<String> patternUrls = filterPatternUrl.getUrlPatterns();
    		for (String patternUrl : patternUrls) {
    			Pattern p = Pattern.compile(patternUrl);
    			Matcher m = p.matcher(url);
    			if (m.find()) {
    				return true;
    			}
    		}
    	}
        return false;
    }
	
}  
