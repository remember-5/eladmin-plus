/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.websocket.handler;

import com.remember5.websocket.constant.RedisKeyConstant;
import com.remember5.websocket.properties.NettyProperties;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权handler
 *
 * @author wangjiahao
 * @date 2022/12/13 17:31
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    private static final String AUTHORIZATION = "Authorization";

    private RedisTemplate<String, Object> redisTemplate;

    public AuthHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 一旦连接，第一个被执行
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("[handlerAdded] 逻辑处理器被添加：handlerAdded() {}", ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        NettyProperties.getChannelGroup().add(ctx.channel());
//        super.handlerAdded(ctx);
    }


    /**
     * 注册
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("[channelRegistered] channel 绑定到线程(NioEventLoop)：channelRegistered()");
        super.channelRegistered(ctx);
    }

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[channelActive] channel 准备就绪：channelActive()");
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("链接报告开始");
        log.info("链接报告信息：本客户端链接到服务端。channelId：{}", channel.id());
        log.info("链接报告IP: {}", channel.localAddress().getHostString());
        log.info("链接报告Port: {}", channel.localAddress().getPort());
        log.info("链接报告完毕");
        String str = "通知服务端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(str);
    }


    /**
     * 读取消息
     * 如果websocket的uri需要接收参数，则需要取参后，重制uri地址，参考https://cloud.tencent.com/developer/article/2144183
     *
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("[channelRead] channel 有数据可读：channelRead()");
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            final String uri = httpRequest.uri();
            final Map<String, String> params = getParams(uri);
            final String userid = params.get("userId");

            //根据请求头header方式 AUTHORIZATION 进行鉴权操作 但是浏览器不支持header，看需求定义吧
//            final String userid = httpRequest.headers().get("userId");
//            log.info("鉴权操作");
//            if (null == userid || "".equals(userid)) {
//                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED));
//                ctx.channel().close();
//                return;
//            }
            // 判断是否包含参数，如果包含参数，需要重制uri
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                httpRequest.setUri(newUri);
            }

            //鉴权成功，添加channel用户组
            NettyProperties.getChannelGroup().add(ctx.channel());

            // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
            AttributeKey<String> key = AttributeKey.valueOf("userId");
            ctx.channel().attr(key).setIfAbsent(userid);
            NettyProperties.getUserChannelMap().put(userid, ctx.channel());

            // save redis
            redisTemplate.opsForSet().add(RedisKeyConstant.REDIS_WEB_SOCKET_USER_SET, userid);
            // 鉴权完成删除这个header
            ctx.pipeline().remove(this);
            // 对事件进行传播，知道完成WebSocket连接。
            ctx.fireChannelRead(msg);
        } else {
            ctx.channel().close();
        }
    }


    /**
     * 消息读取完成
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("[channelReadComplete] channel 某次数据读完：channelReadComplete()");
        super.channelReadComplete(ctx);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("[channelInactive] channel 被关闭：channelInactive()");
        log.info("断开链接 {}", ctx.channel().localAddress().toString());
        removeRedisUserId(ctx);
    }

    /**
     * 注销
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("[channelUnregistered] channel 取消线程(NioEventLoop) 的绑定: channelUnregistered()");
        removeRedisUserId(ctx);
    }

    /**
     * 发生异常 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     *
     * @param ctx   /
     * @param cause /
     * @throws Exception /
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("[exceptionCaught] 异常：{}", cause.getMessage());
        cause.printStackTrace();
        removeRedisUserId(ctx);
    }


    public void removeRedisUserId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        redisTemplate.opsForSet().remove(RedisKeyConstant.REDIS_WEB_SOCKET_USER_SET, userId);
        ctx.channel().close();
    }

    /**
     * 获取uri中的参数
     *
     * @param uri uri
     * @return 参数map
     */
    public static Map<String, String> getParams(String uri) {
        // /ws?name=123  /?name=123
        if (null == uri || uri.isEmpty()) {
            return Collections.emptyMap();
        }
        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        if (uri.indexOf("?") > 0) {
            uri = uri.substring(uri.indexOf("?"));
        }

        uri = uri.replace("/", "").replace("?", "");
        HashMap<String, String> result = new HashMap<>();

        if (uri.indexOf("&") > 0) {
            final String[] split = uri.split("&");
            for (String s : split) {
                result.put(s.split("=")[0], s.split("=")[1]);
            }
        } else if (uri.length() > 0 && uri.indexOf("=") > 0) {
            result.put(uri.split("=")[0], uri.split("=")[1]);
        }

        return result;

    }


    public static void main(String[] args) {
        String params = "/ws?name=123";
        String params1 = "/ws?userId=1234";
        String params2 = "/?name=123&age=132";
//        System.err.println(getParams(params));
        System.err.println(getParams(params1));
//        System.err.println(getParams(params2));
    }
}
