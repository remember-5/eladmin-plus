package com.remember5.core.enums;

/**
 * @author wangjiahao
 * @date 2022/9/29 22:18
 */
public enum LogChannelEnum {

    Sys(1),
    App(2);


    public final Integer channelId;

    LogChannelEnum(Integer channelId) {
        this.channelId = channelId;
    }

}
