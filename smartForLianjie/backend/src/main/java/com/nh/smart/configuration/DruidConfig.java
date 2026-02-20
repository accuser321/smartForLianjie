package com.nh.smart.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfig {

	/**
	 * 
	 * @Title: druidServlet
	 * @Description: 通过web端调用Druid的web监控
	 *               访问路径==>http://127.0.0.1/keel/druid/index.html 代码配置优先级 >
	 *               application.yml中的配置
	 * @Author Demo
	 * @DateTime 2020年1月9日 下午5:24:23
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidServlet() { // 主要实现WEB监控的配置处理
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*"); // 现在要进行druid监控的配置处理操作
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1,10.50.22.185"); // 白名单
		servletRegistrationBean.addInitParameter("deny", "192.168.28.200"); // 黑名单
		servletRegistrationBean.addInitParameter("loginUsername", "nh"); // 用户名
		servletRegistrationBean.addInitParameter("loginPassword", "NHdruid123"); // 密码
		servletRegistrationBean.addInitParameter("resetEnable", "false"); // 是否可以重置数据源
		return servletRegistrationBean;
	}

	/**
	 * 
	 * @Title: filterRegistrationBean
	 * @Description: 处理监控注册内容
	 * @Author Demo
	 * @DateTime 2020年1月9日 下午5:40:45
	 * @return
	 */
	// @Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*"); // 所有请求进行监控处理
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");// 排除监控内容
		return filterRegistrationBean;
	}

	/**
	 * 
	 * @Title: druidDataSource
	 * @Description: 表示使用application.yml中spring.datasource的配置内容
	 * @Author Demo
	 * @DateTime 2020年1月9日 下午5:41:05
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}
}
