package com.nh.smart.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 
 * @ClassName: CaffeineCacheConfig
 * @Description: 使用 springboot - caffeine 高性能缓存库,替换 Guava
 * @Author Demo
 * @DateTime 2020年2月27日 上午10:52:49
 */
@Configuration
public class CaffeineCacheConfig {

	@Bean
	public Cache<String, Object> caffeineCache() {
		return Caffeine.newBuilder()
				// 设置最后一次写入或访问后经过固定时间过期
				.expireAfterWrite(60, TimeUnit.SECONDS)
				// 初始的缓存空间大小
				.initialCapacity(100)
				// 缓存的最大条数
				.maximumSize(1000).build();
	}

}
