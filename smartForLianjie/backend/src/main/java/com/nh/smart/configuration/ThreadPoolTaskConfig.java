package com.nh.smart.configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ThreadPoolTaskConfig
 * @Description: 线程池配置
 * @Author Demo
 * @DateTime 2020年2月20日 上午11:17:03
 */
@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolTaskConfig {

	/**
	 * 默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
	 * 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
	 * 当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝
	 */
	/** 核心线程数（默认线程数） */
	private  final int corePoolSize = 20;
	/** 最大线程数 */
	private  final int maxPoolSize = 100;
	/** 允许线程空闲时间（单位：默认为秒） */
	private  final int keepAliveTime = 10;
	/** 缓冲队列大小 */
	private  final int queueCapacity = 200;
	/** 线程池名前缀 */
	private  final String threadNamePrefix = "Async-Service-";
	
	@Resource
    DataSource dataSource;

	@Bean("taskExecutor") // bean的名称，在需要调用线程池的地方，调用@Async("taskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveTime);
		executor.setThreadNamePrefix(threadNamePrefix);

		log.info("~~~~~~~~初始化调用到了线程池配置~~~~~~~~");
		
		log.info("数据源加载类>>>>>>" + dataSource.getClass());
        Connection connection;
		try {
			connection = dataSource.getConnection();
			log.info("数据源连接>>>>>" + connection);
			log.info("数据库连接>>>>>" + connection.getMetaData().getURL());
		    connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
       
		
		// 线程池对拒绝任务的处理策略
		// CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 初始化
		executor.initialize();
		return executor;
	}
}
