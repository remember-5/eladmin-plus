package com.remember5.system.modules.tool.websocket;

import com.alibaba.fastjson.JSONException;
import com.remember5.core.config.NettyConfiguration;
import com.remember5.core.utils.TokenProvider;
import com.remember5.system.modules.tool.repository.MessageNotificationRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


/**
 * 自定义消息处理器 TextWebSocketFrame类型， 表示一个文本帧
 *
 * @author fly
 * @date 2021-12-24
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final MessageNotificationRepository messageNotificationRepository;
    private final TokenProvider tokenProvider;


    /**
     * 一旦连接，第一个被执行
     *
     * @param ctx /
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("handlerAdded 被调用" + channel.id().asLongText());
        log.info("有客户端建立连接");
        log.info("客户端address: " + channel.remoteAddress().toString());
        log.info("客户端channel Id:" + channel.id().asLongText());
        // 添加到channelGroup 通道组，
        // 【因为要鉴权】 这个位置不能直接加入到channelGroup
        // NettyConfig.getChannelGroup().add(ctx.channel());
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息：{}", msg.text());
        Channel channel = ctx.channel();
        // 关联channel
        NettyConfiguration.getUserChannelMap().put(ctx.channel().id().toString(), channel);
        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器连接成功！"));
    }

    @Override
    public void handlerRemoved(@NotNull ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved 被调用" + ctx.channel().id().asLongText());
        // 删除通道
        NettyConfiguration.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端断开连接... 客户端 address: " + channel.remoteAddress());
        NettyConfiguration.getChannelGroup().remove(channel);
        NettyConfiguration.getUserChannelMap().remove(channel.id().toString(), channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常：{}", cause.getMessage());
        if (cause instanceof JSONException) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("服务端仅支持JSON格式体消息"));
            return;
        }
        Channel channel = ctx.channel();
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务端500 关闭连接"));
        ctx.channel().closeFuture();
        // 删除通道
        NettyConfiguration.getChannelGroup().remove(ctx.channel());
        NettyConfiguration.getUserChannelMap().remove(channel.id().toString(), channel);
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     *
     * @param ctx /
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        NettyConfiguration.getUserChannelMap().remove(ctx.channel().id().toString());
    }
}
