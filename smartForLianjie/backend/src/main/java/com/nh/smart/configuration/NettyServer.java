package com.nh.smart.configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * netty服务器
 *
 * @Author: 张宁
 * @Date: 2019/11/5
 */
@Component
public class NettyServer {

    @Resource
    private WebSocketHandler webSocketHandler;

    private final Logger log = LoggerFactory.getLogger(NettyServer.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    private final EventLoopGroup group = new NioEventLoopGroup();

    public void destroy() {
        bossGroup.shutdownGracefully();
        group.shutdownGracefully();
    }

    public void start(int post) {
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            sb.group(group, bossGroup) // 绑定线程池
                    .channel(NioServerSocketChannel.class) // 指定使用的channel
                    .localAddress("0.0.0.0",post)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));// 心跳反应
                            ch.pipeline().addLast(webSocketHandler);
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
                        }
                    });
            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
            log.info(NettyServer.class + " 启动正在监听： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        } catch (Exception e) {
            log.info("释放线程池资源," + e.getMessage());
        } finally {
            try {
                group.shutdownGracefully().sync();// 释放线程池资源
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.info("释放线程池资源," + e.getMessage());
            }
        }
    }

}
