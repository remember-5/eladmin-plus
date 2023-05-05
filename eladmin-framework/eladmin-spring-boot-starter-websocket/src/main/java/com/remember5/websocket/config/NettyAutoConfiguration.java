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

import com.remember5.websocket.MessageReceive;
import com.remember5.websocket.WebSocketChannelInitializer;
import com.remember5.websocket.constant.RedisKeyConstant;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiahao
 * @date 2023/5/6 15:17
 */
public class NettyAutoConfiguration {

    /**
     * 定义一个channel组，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
     */
    private static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存放用户与Chanel的对应信息，用于给指定用户发送消息
     */
    private static ConcurrentHashMap<String, Channel> USER_CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 获取channel组
     *
     * @return /
     */
    public static ChannelGroup getChannelGroup() {
        return CHANNEL_GROUP;
    }

    /**
     * 获取用户channel map
     *
     * @return /
     */
    public static ConcurrentHashMap<String, Channel> getUserChannelMap() {
        return USER_CHANNEL_MAP;
    }


    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param redisConnectionFactory /
     * @param listenerAdapter1       /
     * @param listenerAdapter2       /
     * @return /
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                                   MessageListenerAdapter listenerAdapter1,
                                                   MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        // 订阅频道(发送给指定用户)
        redisMessageListenerContainer.addMessageListener(listenerAdapter1, new PatternTopic(RedisKeyConstant.PUSH_MESSAGE_TO_ONE));

        // 订阅频道(发送给所有用户)
        redisMessageListenerContainer.addMessageListener(listenerAdapter2, new PatternTopic(RedisKeyConstant.PUSH_MESSAGE_TO_ALL));


        //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
        Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        seria.setObjectMapper(objectMapper);
        redisMessageListenerContainer.setTopicSerializer(seria);
        return redisMessageListenerContainer;
    }


    /**
     * 表示监听一个频道（发送给指定用户）
     */
    @Bean
    public MessageListenerAdapter listenerAdapter1(MessageReceive messageReceive) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“MessageReceive#getMessageToOne”
        return new MessageListenerAdapter(messageReceive, "getMessageToOne");
    }

    /**
     * 表示监听一个频道（发送给所有用户）
     */
    @Bean
    public MessageListenerAdapter listenerAdapter2(MessageReceive messageReceive) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“MessageReceive#getMessageToAll ”
        return new MessageListenerAdapter(messageReceive, "getMessageToAll");
    }

    @Bean
    public WebSocketChannelInitializer webSocketChannelInitializer(RedisTemplate<String, Object> redisTemplate) {
        return new WebSocketChannelInitializer(redisTemplate);
    }

    @Bean
    public NettyServer initNettyServer(WebSocketChannelInitializer webSocketChannelInitializer) {
        return new NettyServer(webSocketChannelInitializer);
    }

}
