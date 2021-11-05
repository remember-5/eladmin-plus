package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;

/**
 * 需要用到主包里的东西这里使用Service来解耦
 */
public interface AutoPermissionService {

    String genAutoPermission(GenConfig genConfig);
}
