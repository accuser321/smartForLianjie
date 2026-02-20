package com.nh.smart.log4j2;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: Log4jTest2
 * @Description: 常用日志框架:
 * 				 	java.util.logging:是JDK在1.4版本中引入的Java原生日志框架
				 	Log4j:Apache的一个开源项目,可以控制日志信息输送的目的地是控制台、文件、GUI组件等,控制每一条日志的输出格式,已经停止维护.
				 	LogBack:是Log4j的一个改良版本
				 	Log4j2:Log4j2已经不仅仅是Log4j的一个升级版本了,它从头到尾都被重写了.
				 日志门面slf4j:
				 	不是一个真正的日志实现,而是一个抽象层,它允许你在后台使用任意一个日志实现.
				 Log4j2的应用:
					log4j2优越的性能其原因在于log4j2使用了LMAX,一个无锁的线程间通信库代替了,logback和log4j之前的队列. 并发性能大大提升
					在多线程环境下,性能高于logback等10倍以上;利用jdk1.5并发的特性,减少了死锁的发生;
				 ============================================================	
				 springboot默认使用logback的日志框架,所有要在依赖中排除logback,否则会冲突.
				 
				 当使用lombok时,节约代码量
 * @Author Demo
 * @DateTime 2020年2月5日 下午1:27:02
 */
@Slf4j
public class Log4j2 {
	//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j2.class);
	public static void main(String... args) {
	    log.info("Something else is wrong here");
	  }
	
}
