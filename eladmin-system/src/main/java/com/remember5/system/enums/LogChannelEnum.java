package com.remember5.system.enums;

/**
 * @author wangjiahao
 * @date 2022/9/29 22:18
 */
public enum LogChannelEnum {

    // 后台系统
    Sys(1,"System"),
    // App渠道
    App(2,"App");


    public final Integer channelId;
    public final String channelName;

    LogChannelEnum(Integer channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }

}
