package me.zhengjie.modules.generator.service;

import me.zhengjie.modules.generator.domain.GenConfig;

/**
 * 需要用到主包里的东西这里使用Service来解耦
 *
 * @author wangjiahao
 */
public interface AutoPermissionService {

    /**
     * 生成权限
     *
     * @param genConfig /
     * @return /
     */
    String genAutoPermission(GenConfig genConfig);
}
