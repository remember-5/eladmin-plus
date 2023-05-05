package com.remember5.system.modules.tool.websocket.controller.service;

import com.remember5.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author sixiaojie
 * @date 2020-03-30-20:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushServiceImpl implements PushService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisUtils redisUtils;

    @Override
    public void localPush2User(String userId, String message) {
//        ConcurrentHashMap<String, Channel> userChannelMap = NettyProperties.getUserChannelMap();
//        Channel channel = userChannelMap.get(userId);
//        // 如果该用户的客户端是与本服务器建立的channel,直接推送消息
//        channel.writeAndFlush(new TextWebSocketFrame(message));
    }

    @Override
    public void localPushAllUser(String message) {
//        NettyProperties.getChannelGroup().writeAndFlush(new TextWebSocketFrame(message));
    }

    @Override
    public void pushMsg2User(String userId, String msg) {
//        ConcurrentHashMap<String, Channel> userChannelMap = NettyProperties.getUserChannelMap();
//        if (userChannelMap.containsKey(userId)) {
//            // 如果该用户的客户端是与本服务器建立的channel,直接推送消息
//            Channel channel = userChannelMap.get(userId);
//            if (!Objects.isNull(channel)) {
//                channel.writeAndFlush(new TextWebSocketFrame(msg));
//                log.info("用户在本节点,直接发送消息");
//            } else {
//                log.warn("用户在本节点,获取channel出错");
//            }
//            return;
//        }
//
//        final Boolean member = redisUtils.sHasKey(CacheKeyConstant.REDIS_WEB_SOCKET_USER_SET, userId);
//        if (!member) {
//            log.warn("用户不在线");
//            return;
//        }
//
//        log.info("跨节点消息");
//        // 发布，给其他服务器消费
//        NettyPushMessageBody pushMessageBody = new NettyPushMessageBody();
//        pushMessageBody.setUserId(userId);
//        pushMessageBody.setMessage(msg);
//        redisTemplate.convertAndSend(CacheKeyConstant.PUSH_MESSAGE_TO_ONE, pushMessageBody);
    }

    @Override
    public void pushMsg2AllUser(String msg) {
//        // 发布，给其他服务器消费
//        redisTemplate.convertAndSend(CacheKeyConstant.PUSH_MESSAGE_TO_ALL, msg);
////        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
