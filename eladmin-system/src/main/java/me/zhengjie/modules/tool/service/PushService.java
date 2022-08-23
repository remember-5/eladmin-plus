package me.zhengjie.modules.tool.service;


/**
 * @author fly
 * @date 2021-12-24
 */
public interface PushService {
    /**
     * 推送给指定用户
     *
     * @param userId 用户id
     * @param msg    消息
     */
    void pushMsgToOne(String userId, String msg);

    /**
     * 推送给所有用户
     *
     * @param msg 消息
     */
    void pushMsgToAll(String msg);
}
