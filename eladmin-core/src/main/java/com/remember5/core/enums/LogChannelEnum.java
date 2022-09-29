package com.remember5.core.enums;

/**
 * @author wangjiahao
 * @date 2022/9/29 22:18
 */
public enum LogChannelEnum {

    // 后台系统
    Sys(1),
    // App渠道
    App(2);


    public final Integer channelId;

    LogChannelEnum(Integer channelId) {
        this.channelId = channelId;
    }

}
