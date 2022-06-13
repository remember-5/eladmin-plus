package com.remember5.push;

import com.remember5.push.entity.BasePushClient;

import java.util.Set;

/**
 * @author wangjiahao
 * @date 2022/5/27 19:23
 */
public interface PushService<T extends BasePushClient> {
    /**
     * 推送
     *
     * @param cid /
     * @return /
     */
    boolean push(T cid);

    /**
     * 批量推送
     *
     * @param cids /
     * @return /
     */
    boolean batchPush(Set<T> cids);
}
