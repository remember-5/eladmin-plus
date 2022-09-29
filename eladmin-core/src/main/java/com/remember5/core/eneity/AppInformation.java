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
     * App描述
     */
    private String description;
    /**
     * x.y.z格式版本号
     */
    private String versionCode;
    /**
     * 打包号
     */
    private String buildCode;

}
