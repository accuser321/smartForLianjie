package com.nh.smart.configuration;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nh.smart.annotation.Log;

/**
 * 
 * @ClassName: LogAspect
 * @Description: 日志切面(使用Logback MDC)
 * @Author Demo
 * @DateTime 2020年1月20日 上午9:36:42
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	SimpleDateFormat df = new SimpleDateFormat(dateFormat);//设置日期格式
	Date date = new Date();
	String formateDate = df.format(date);
	
	private final String STRING_START = "\n>---------------项目最近的启动时间 " + formateDate + "---------------------\n";
	private static final String STRING_END =   "\n-------------------------------------\n";

	@Pointcut("execution(* com.nh..*Controller.*(..))")
	public void serviceLog() {
	}

	@Around("serviceLog()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Class<?> targetClass = method.getDeclaringClass();

		StringBuffer classAndMethod = new StringBuffer();
 
		Log classAnnotation = targetClass.getAnnotation(Log.class);
		Log methodAnnotation = method.getAnnotation(Log.class);
 
		if (classAnnotation != null) {
			if (classAnnotation.ignore()) {
				return joinPoint.proceed();
			}
			classAndMethod.append(classAnnotation.value()).append("-");
		}

		if (methodAnnotation != null) {
			if (methodAnnotation.ignore()) {
				return joinPoint.proceed();
			}
			classAndMethod.append(methodAnnotation.value());
		}

		String target = targetClass.getName() + "####" + method.getName();
		
		//=====增加web访问的日志监控(对应需要修改@Pointcut切入点到controller),否则RequestContextHolder.getRequestAttributes()会为null=====
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
 
        // 记录下web端访问请求内容
        log.info("================攻城狮:查看每一次调用的日志开始================");
        log.info("地址URL : " + request.getRequestURL().toString());
        log.info("请求方法 :  " + request.getMethod());
        log.info("访问IP : " + request.getRemoteAddr());
        log.info("类方法 :  " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("参数内容 :  " + Arrays.toString(joinPoint.getArgs()));
        
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);//设置日期格式
    	Date date = new Date();
    	String formateDate = df.format(date);
    	
		log.info("访问接口时间 :  " + formateDate);
		
		//显示并记录访问web的控制层类名和方法名
		log.info("请求访问的接口地址 == >" + target); 
		
		
		//==================================接口耗时监控==begin================================
		//String params = null;
		//params = JSONObject.toJSONString(SerializerFeature.WriteDateUseDateFormat);
		//log.info(STRING_START + "{} 开始调用--> {} 参数:{}", classAndMethod.toString(), target,params);
		log.info(STRING_START + ">>>>>>>>>>>>>>开始调用--> {} 参数内容:{}", target ,Arrays.toString(joinPoint.getArgs()));
		
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long timeConsuming = System.currentTimeMillis() - start;

		//String paramss = JSONObject.toJSONString(SerializerFeature.WriteMapNullValue);
		//log.info("{} 调用结束<-- {} 返回值:{} 耗时:{}ms" + STRING_END, classAndMethod.toString(), target,paramss,timeConsuming);
		//log.info(STRING_END + ">>>>>>>>>>>>>>调用结束<-- 返回值:{} 耗时:{}ms", target,timeConsuming); //双参数完整格式
		log.info(STRING_END + ">>>>>>>>>>>>>>调用结束<-- 耗时:{}ms", timeConsuming);	//简化一下格式
		//==================================接口耗时监控==end================================
		return result;
	}
}
