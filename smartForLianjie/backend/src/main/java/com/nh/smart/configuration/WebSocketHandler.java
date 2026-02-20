package com.nh.smart.configuration;

import com.nh.smart.entity.vo.UserAction;
import com.nh.smart.service.record.SmartComKjActionRecordService;
import com.zaxxer.hikari.util.SuspendResumeLock;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * websocket 实际处理类
 *
 * @Author: 张宁
 * @Date: 2019/11/5
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmartComKjActionRecordService smartComKjActionRecordService;


    /**
     * 开启连接
     *
     * @Param: [ctx]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("与客户端建立连接，通道开启！");
        // 添加到channelGroup通道组
        MyChannelHandlerPool.channelGroup.add(ctx.channel());
        // 添加用户信息
        redisTemplate.opsForValue().set(ctx.channel().id().asLongText(), UserAction.builder().begin(new Date()).build());
    }

    /**
     * 关闭连接
     *
     * @Param: [ctx]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端失去连接，通道关闭！");
        // 计算时间差 执行行为记录浏览时间更新操作
        UserAction userAction = (UserAction) redisTemplate.opsForValue().get(ctx.channel().id().asLongText());
        userAction.setOver(new Date());
        userAction.setTimeByBeginAndOver();
        String comid = userAction.getComid();
        if (userAction.getId() != null && comid != null) {
          smartComKjActionRecordService.updateActionCordTime(userAction);
        }
        redisTemplate.delete(ctx.channel().id().asLongText());
        ctx.channel().close();
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
    }

    /**
     * 接受参数
     *
     * @Param: [ctx, msg]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/11/5
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest，处理参数
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Map paramMap = getUrlParams(uri);
            System.out.println("接收到的comid参数为=======================" + paramMap.get("comid"));
            // 添加行为记录唯一标识
            Object object = redisTemplate.opsForValue().get(ctx.channel().id().asLongText());
            UserAction userAction = (UserAction) object;
            userAction.setId(paramMap.get("id") == null ? null : Integer.valueOf((String) paramMap.get("id")));
            userAction.setComid(paramMap.get("comid") == null ? null : (String) paramMap.get("comid"));
            redisTemplate.opsForValue().set(ctx.channel().id().asLongText(), userAction);
            //如果url包含参数，需要处理
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                request.setUri(newUri);
            }

        } else if (msg instanceof TextWebSocketFrame) {
            //正常的TEXT消息类型
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            log.info("心跳反应：" + frame.text());
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 心跳业务
     *
     * @Param: [ctx, evt]
     * @return: void
     * @Author: 张宁
     * @Date: 2019/11/6
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
                MyChannelHandlerPool.channelGroup.remove(ctx.channel());
                log.info("客户端未反应，关闭通道！");
                super.userEventTriggered(ctx, evt);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private static Map getUrlParams(String url) {
        Map<String, String> map = new HashMap<>();
        url = url.replace("?", ";");
        if (!url.contains(";")) {
            return map;
        }
        if (url.split(";").length > 0) {
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr) {
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key, value);
            }
            return map;

        } else {
            return map;
        }
    }
}
