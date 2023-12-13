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
package com.remember5.websocket.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * netty 发送的消息实体
 *
 * @author wangjiahao
 * @date 2023/12/13 13:53
 */
@Data
public class NettyPushMessageBody implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 发送消息
     */
    private String message;
}
