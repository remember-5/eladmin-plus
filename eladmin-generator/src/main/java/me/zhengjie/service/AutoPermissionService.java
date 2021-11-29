package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;

/**
 * 需要用到主包里的东西这里使用Service来解耦
 * @author wangjiahao
 */
public interface AutoPermissionService {

    /**
     * /
     * @param genConfig /
     * @return /
     */
    String genAutoPermission(GenConfig genConfig);
}
