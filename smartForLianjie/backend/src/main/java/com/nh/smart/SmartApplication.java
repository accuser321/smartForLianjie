package com.nh.smart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.nh.smart.interceptor.LogbackMDCInterceptor;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableTransactionManagement //启动事务注解
@EnableAsync				 //开启异步支持
@MapperScan(basePackages = {"com.nh.smart.dao.**"})	//补充描述：2020-03-10 多数据源项目的修改内容,增加在启动类中扫描包的动作
@Slf4j
public class SmartApplication extends WebMvcConfigurerAdapter
{

	@Autowired
    private LogbackMDCInterceptor logInterceptor;

    public static void main( String[] args ){
    	log.info("--------------SmartApplication--------------start-----------");
		SpringApplication.run(SmartApplication.class, args);
    }

	/**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        super.addInterceptors(registry);
    }

  /**
   * 解决在方法中使用(ServletRequestAttributes) RequestContextHolder.getRequestAttributes()空指针问题
   *
   * @return
   */
  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

}



