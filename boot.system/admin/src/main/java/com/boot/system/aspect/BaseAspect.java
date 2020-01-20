package com.boot.system.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.boot.system.dataorm.config.ResultMsg;

@Component
@Aspect
public class BaseAspect {

	/*
	 * @Autowired HttpSession session;
	 * 
	 */
											
	/********** 记录操作日志end ***********/
	
	private final String MY_EXECUTION = "execution(* com.boot.system.controller.*Controller.insert*(..))"
              + " or execution(* com.boot.system.controller.*Controller.update*(..))"
		 	  + " or execution(* com.boot.system.controller.*Controller.delete*(..))";
	
	@Before(value = MY_EXECUTION)
	public void beforeMethodForOperateLogs(JoinPoint jp) { //
		System.out.println("操作之前-----------");
		String className = jp.getSignature().getDeclaringTypeName();
		String menthodName = jp.getSignature().getName(); 
		HttpServletRequest request = (HttpServletRequest) jp.getArgs()[0];
		String onlineId = request.getHeader("id");
		
		String paramClassName1 = jp.getArgs()[0].getClass().toString();
		String paramClassName2 = jp.getArgs()[1].getClass().toString();
		
		System.out.println("onlineId: " + onlineId);
		System.out.println("className: " + className);
		System.out.println("menthodName: " + menthodName);	
		System.out.println("paramClassName1: " + paramClassName1);
		System.out.println("paramClassName2: " + paramClassName2);
	
	}
											
	@After(value = MY_EXECUTION)
	public void afterMethodForOperateLogs(JoinPoint jp) { //
		System.out.println("操作成功后------"); 
	}
											 
	@AfterThrowing(value = MY_EXECUTION, throwing = "e")
	public void afterThrowForOperateLogs(Exception e) { //
		System.out.println("操作失败，抛出异常------" + e.getMessage());
	}
											  
	@AfterReturning(value = MY_EXECUTION, returning = "result")
	public void afterReturnForOperateLogs(Object result) { 
		ResultMsg resultMsg = (ResultMsg) result;
		System.out.println("操作成功，并返回结果--------------");
		System.out.println("errorCode: " + resultMsg.getErrorCode()); 
		System.out.println("errorMsg: " + resultMsg.getErrorMsg()); 
		System.out.println("body: " + resultMsg.getBody());
	}

}
