package me.zhengjie.modules.tool.service.impl;

import com.remember5.redis.config.NettyConfig;
import com.remember5.redis.entity.NettyPushMessageBody;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.tool.service.PushService;
import me.zhengjie.utils.CacheKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fly
 * @date 2021-12-24
 */
@Service
@RequiredArgsConstructor
public class PushServiceImpl implements PushService {

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void pushMsgToOne(String userId, String msg) {
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        if (!Objects.isNull(channel)) {
            // 如果该用户的客户端是与本服务器建立的channel,直接推送消息
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        } else {
            // 发布，给其他服务器消费
            NettyPushMessageBody pushMessageBody = new NettyPushMessageBody();
            pushMessageBody.setUserId(userId);
            pushMessageBody.setMessage(msg);
            redisTemplate.convertAndSend(CacheKey.PUSH_MESSAGE_TO_ONE, pushMessageBody);
        }
    }

    @Override
    public void pushMsgToAll(String msg) {
        // 发布，给其他服务器消费
        redisTemplate.convertAndSend(CacheKey.PUSH_MESSAGE_TO_ALL, msg);
//        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
