package com.nh.smart.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听netty服务启动
 *
 * @Author: 张宁
 * @Date: 2019/11/12
 */
@Component
public class NettyServerListener implements ServletContextListener {

    @Resource
    private NettyServer nettyServer;

    private Integer nettyserverport=14367;

    private final Logger log = LoggerFactory.getLogger(NettyServerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new Thread() {
            @Override
            public void run() {
                try {
                    log.info(NettyServerListener.class + "websocket开启监听");
                    nettyServer.start(nettyserverport);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info(NettyServerListener.class + "websocket停止监听");
        nettyServer.destroy();
    }
}
