package me.zhengjie.modules.tool.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.remember5.redis.config.NettyConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.security.security.TokenProvider;
import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.modules.tool.repository.MessageNotificationRepository;
import me.zhengjie.modules.tool.service.dto.SocketBody;
import me.zhengjie.modules.tool.service.dto.SocketHead;
import org.springframework.stereotype.Component;


/**
 * TextWebSocketFrame类型， 表示一个文本帧
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
        log.info("handlerAdded 被调用" + ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息：{}", msg.text());

        // 获取消息内容
        JSONObject jsonObject = JSONUtil.parseObj(msg.text());
        String head = jsonObject.getStr("head");
        SocketHead socketHead = JSON.toJavaObject(JSON.parseObject(head), SocketHead.class);
        // 类型为0 登录 类型为 1 消息通知
        if (socketHead.getType() == 0) {
            String token = socketHead.getToken().replace("Bearer ", "");
            if (!tokenProvider.checkToken(token)) {
                log.info("Token异常：{}", token);
                // 删除通道
                NettyConfig.getChannelGroup().remove(ctx.channel());
                ctx.close();
                return;
            }

            // 获取用户ID
            String uid = socketHead.getUid();
            // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
            AttributeKey<String> key = AttributeKey.valueOf("userId");
            ctx.channel().attr(key).setIfAbsent(uid);
            // 关联channel
            NettyConfig.getUserChannelMap().put(uid, ctx.channel());
            // 回复消息
            ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器连接成功！"));
        } else if (socketHead.getType() == 1) {
            // 获取消息请求体中的内容
            String body = jsonObject.getStr("body");
            SocketBody socketBody = JSON.toJavaObject(JSON.parseObject(body), SocketBody.class);
            MessageNotification msgn = JSON.toJavaObject(JSON.parseObject(socketBody.getContent()), MessageNotification.class);
            if (ObjectUtil.isNotNull(msgn)) {
                messageNotificationRepository.updateById(2, msgn.getId());
            }
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved 被调用" + ctx.channel().id().asLongText());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常：{}", cause.getMessage());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     *
     * @param ctx /
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        NettyConfig.getUserChannelMap().remove(userId);
    }
}
