/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.core.constants;

/**
 * @author: liaojinlong
 * @date: 2020/6/11 15:49
 * @apiNote: 关于缓存的Key集合
 */
public interface CacheKeyConstant {

    /**
     * 用户
     */
    String USER_ID = "user::id:";
    /**
     * 数据
     */
    String DATA_USER = "data::user:";
    /**
     * 菜单
     */
    String MENU_ID = "menu::id:";
    String MENU_USER = "menu::user:";
    /**
     * 角色授权
     */
    String ROLE_AUTH = "role::auth:";
    /**
     * 角色信息
     */
    String ROLE_ID = "role::id:";
    /**
     * 部门
     */
    String DEPT_ID = "dept::id:";
    /**
     * 岗位
     */
    String JOB_ID = "job::id:";
    /**
     * 数据字典
     */
    String DICT_NAME = "dict::name:";

    /**
     * redis发布订阅topic：发送给指定用户
     */
    String PUSH_MESSAGE_TO_ONE = "PushMessageToOne";

    /**
     * redis发布订阅topic：发送给所有用户
     */
    String PUSH_MESSAGE_TO_ALL = "PushMessageToAll";

    /**
     * websocket 在线用户列表
     */
    String REDIS_WEB_SOCKET_USER_SET = "WEBSOCKET:USER";

    /**
     * 微信小程序登录的session_key，
     */
    String WX_MINI_APP_LOGIN_SESSION_KEY = "wx:mini:app:login:session:key:";
    /**
     * 微信小程序登录的session_key的失效时间 3天
     */
    Long EXPIRE_WX_MINI_APP_LOGIN_SESSION_KEY = 60 * 24 * 3L;



    String APP_VERSION_PREFIX = "APP:VERSION:";



}
