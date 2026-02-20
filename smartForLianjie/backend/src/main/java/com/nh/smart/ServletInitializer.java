package com.nh.smart;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @ClassName: ServletInitializer
 * @Description: 继承SpringBootServletInitializer类,覆盖其config方法
 * 				 打成war包，使springboot微架构项目转成Tomcat启动的web项目
 * @Author Demo
 * @DateTime 2020年1月9日 下午6:05:01
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SmartApplication.class);
	}
}
