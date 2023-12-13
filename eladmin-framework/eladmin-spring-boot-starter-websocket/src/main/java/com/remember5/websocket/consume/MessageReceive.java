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
package com.remember5.websocket.consume;

import com.alibaba.fastjson2.JSON;
import com.remember5.websocket.constant.NettyChannelManager;
import com.remember5.websocket.entity.NettyPushMessageBody;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息消费者
 *
 * @author wangjiahao
 * @date 2023/12/13 13:55
 */
@Slf4j
public class MessageReceive {

    /**
     * 订阅消息,发送给指定用户
     *
     * @param message /
     */
    public void getMessageToOne(String message) {
        NettyPushMessageBody pushMessageBody = JSON.to(NettyPushMessageBody.class, JSON.parseObject(message));
        log.info("订阅消息,发送给指定用户：{}", pushMessageBody.toString());

        // 推送消息
        String userId = pushMessageBody.getUserId();
        if (NettyChannelManager.getUserChannelMap().containsKey(userId)) {
            ConcurrentHashMap<String, Channel> userChannelMap = NettyChannelManager.getUserChannelMap();
            Channel channel = userChannelMap.get(userId);
            if (!Objects.isNull(channel)) {
                // 如果该用户的客户端是与本服务器建立的channel,直接推送消息
                channel.writeAndFlush(new TextWebSocketFrame(pushMessageBody.getMessage()));
            }
        } else {
            log.warn("未找到userId {} 对应的channel", userId);
        }
    }

    /**
     * 订阅消息，发送给所有用户
     *
     * @param message /
     */
    public void getMessageToAll(String message) {
        log.info("订阅消息，发送给所有用户：{}", message);
        NettyChannelManager.getChannelGroup().writeAndFlush(new TextWebSocketFrame(message));
    }
}
