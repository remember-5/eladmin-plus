package com.remember5.core.eneity;

import lombok.Data;

/**
 * App信息
 * @author wangjiahao
 * @date 2022/9/29 22:32
 */
@Data
public class AppInformation {

    /**
     * App名称
     */
    private String name;
    /**
     * Appid
     */
    private String appid;
    /**
     * App更新描述
     */
    private String description;
    /**
     * x.y.z格式版本号
     */
    private String version;
    /**
     * 打包号 500
     */
    private String build;

}
