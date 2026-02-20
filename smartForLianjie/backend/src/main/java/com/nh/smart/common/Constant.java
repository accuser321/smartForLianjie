package com.nh.smart.common;

public class Constant {

	/**
	 * 
	 * @ClassName: Redis
	 * @Description: Redis常量内容
	 * @Author Demo
	 * @DateTime 2020年1月9日 下午5:21:47
	 */
	public interface Redis {
        String OK = "OK";
        Integer EXPIRE_TIME_MINUTE = 60;// 过期时间, 60s, 一分钟
        Integer EXPIRE_TIME_HOUR = 60 * 60;// 过期时间, 一小时
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;// 过期时间, 一天
        String TOKEN_PREFIX = "token:";
        String MSG_CONSUMER_PREFIX = "consumer:";
        String ACCESS_LIMIT_PREFIX = "accessLimit:";
    }

	
}
