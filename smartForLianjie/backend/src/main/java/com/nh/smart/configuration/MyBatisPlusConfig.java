package com.nh.smart.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * 
 * @ClassName: MyBatisPlusConfig
 * @Description: MpbatisPlus配置类
 * @Author Demo
 * @DateTime 2020年1月9日 下午5:50:17 
 * 			/**补充描述：2020-03-10 多数据源项目的修改内容：
 *           	*由于项目改造为多数据源的项目构建,打破了原有架构中,在此MyBatisPlusConfig.java中统一配置扫描包的做法,
 *           **故,去掉@MapperScan("com.nh.keel.dao*"),而在启动类中加入扫描包配置
 */
@EnableTransactionManagement
@Configuration
//@MapperScan("com.nh.smart.dao*")	
public class MyBatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setDialectType("mysql");
		return paginationInterceptor;
	}
}
