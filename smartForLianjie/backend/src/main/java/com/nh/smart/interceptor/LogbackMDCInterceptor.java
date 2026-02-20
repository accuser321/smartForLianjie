package com.nh.smart.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Logback日志拦截器:
 * Logback MDC(requestUUID)在分布式环境下的追踪web请求的日志ID
 * (基础日志记录功能)
 * @author Demo
 *
 */

@Component
public class LogbackMDCInterceptor extends HandlerInterceptorAdapter {
	
	private Logger LOGGER = LoggerFactory.getLogger(LogbackMDCInterceptor.class);
	
	String requestUUID = null;
	
    //在请求处理之前回调方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String clientID = request.getRemoteAddr();
        
        requestUUID = MDC.get("requestUUID");
        if (requestUUID == null || "".equals(requestUUID)) {
            String uuid = UUID.randomUUID().toString();
            
            uuid = uuid.replaceAll("-", "").toUpperCase();
            MDC.put("requestUUID", uuid);
            LOGGER.debug("【****** Logback日志拦截器调用开始 ******】");
            LOGGER.debug("requestUUID({}) to logger,反向代理到了服务器IP({}),X-Forwarded-For(ClientIP)({}) == >" ,uuid,clientID,xForwardedForHeader);
        }

        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    //请求处理之后回调方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /* 线程结束后需要清除,否则当前线程会一直占用这个requestId值 */
    	String uuid = MDC.get("requestUUID");
    	LOGGER.debug("【****** Logback日志拦截器执行({}) ******】",uuid);
        //LOGGER.info("remove requestUUID({}) form logger",uuid);
        MDC.remove("requestUUID");
    }

    //整个请求处理完毕回调方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*整个请求线程结束后需要清除,否则当前线程会一直占用这个requestId值 */
    	LOGGER.debug("【****** Logback日志拦截器线程清除requestUUID ******】");
        //LOGGER.info("clear requestUUID");
        MDC.clear();

    }
}
