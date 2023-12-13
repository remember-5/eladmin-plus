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
package com.remember5.websocket.config;

import com.remember5.websocket.constant.NettyRedisConstants;
import com.remember5.websocket.consume.MessageReceive;
import com.remember5.websocket.handler.AuthHandler;
import com.remember5.websocket.handler.ClientMsgHandler;
import com.remember5.websocket.handler.HeartBeatHandler;
import com.remember5.websocket.handler.RateLimitHandler;
import com.remember5.websocket.properties.WebSocketProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * netty自动装配
 *
 * @author wangjiahao
 * @date 2023/5/6 15:17
 */
@AutoConfiguration
@EnableConfigurationProperties(WebSocketProperties.class)
public class NettyAutoConfiguration {

    @Configuration
    public static class WebSocketHandlerConfiguration {
        @Bean
        public AuthHandler authHandler(RedisTemplate<String, Object> redisTemplate, WebSocketProperties webSocketProperties) {
            return new AuthHandler(redisTemplate, webSocketProperties);
        }

        @Bean
        public ClientMsgHandler clientMsgHandler(RedisTemplate<String, Object> redisTemplate, WebSocketProperties webSocketProperties) {
            return new ClientMsgHandler(redisTemplate, webSocketProperties);
        }


        @Bean
        public HeartBeatHandler heartBeatHandler() {
            return new HeartBeatHandler();
        }

        @Bean
        public RateLimitHandler rateLimitHandler() {
            return new RateLimitHandler();
        }

    }


    /**
     * 集群模式
     */
    @Configuration
    @ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "enable-cluster", havingValue = "true")
    public static class ClusterConfiguration {
        /**
         * 注入redis 消息监听器
         *
         * @param redisConnectionFactory redis连接工厂
         * @return /
         */
        @Bean
        @ConditionalOnMissingBean(name = "redisTemplate")
        @ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "enable-cluster", havingValue = "true")
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);

            // 使用 String 序列化方式，序列化 KEY 。
            template.setKeySerializer(RedisSerializer.string());
            template.setHashKeySerializer(RedisSerializer.string());

            // 使用 JSON 序列化方式（库是 Fastjson ），序列化 VALUE 。
            template.setValueSerializer(RedisSerializer.json());
            template.setHashValueSerializer(RedisSerializer.json());
            return template;
        }


        /**
         * redis消息监听器容器
         * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
         * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
         *
         * @param redisConnectionFactory redis连接工厂类
         * @param listenerAdapter1       监听器1-发送给指定用户
         * @param listenerAdapter2       监听器2-发送给所有用户
         * @return /
         */
        @Bean
        public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter listenerAdapter1, MessageListenerAdapter listenerAdapter2) {
            RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
            redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);

            // 订阅频道(发送给指定用户)
            redisMessageListenerContainer.addMessageListener(listenerAdapter1, new PatternTopic(NettyRedisConstants.PUSH_MESSAGE_TO_ONE));
            // 订阅频道(发送给所有用户)
            redisMessageListenerContainer.addMessageListener(listenerAdapter2, new PatternTopic(NettyRedisConstants.PUSH_MESSAGE_TO_ALL));
            redisMessageListenerContainer.setTopicSerializer(RedisSerializer.string());
            return redisMessageListenerContainer;
        }

        @Bean
        public MessageReceive messageReceive() {
            return new MessageReceive();
        }


        /**
         * 表示监听一个频道（发送给指定用户）
         * 给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“MessageReceive ”
         */
        @Bean
        public MessageListenerAdapter listenerAdapter1(MessageReceive messageReceive) {
            return new MessageListenerAdapter(messageReceive, "getMessageToOne");
        }

        /**
         * 表示监听一个频道（发送给所有用户）
         * 给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“MessageReceive ”
         */
        @Bean
        public MessageListenerAdapter listenerAdapter2(MessageReceive messageReceive) {
            return new MessageListenerAdapter(messageReceive, "getMessageToAll");
        }
    }


}
