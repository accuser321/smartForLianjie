package com.nh.smart.configuration;


import com.nh.smart.entity.vo.UserAction;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储channle
 *
 * @Author: 张宁
 * @Date: 2019/11/5
 */
public class MyChannelHandlerPool {
    public MyChannelHandlerPool(){}
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketService对象。
    public static ConcurrentHashMap<String, UserAction> itemUsers = new ConcurrentHashMap();

    /*// 获取Redis Bean
    public static RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");

    // 获取AbtComKjActionRecordServiceImpl Bean
    public static AbtComKjActionRecordService abtComKjActionRecordService = (AbtComKjActionRecordService) SpringContextUtil.getBean("abtComKjActionRecordServiceImpl");*/
}
