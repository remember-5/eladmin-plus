package com.remember5.openapi.modules.apiuser.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fly
 * @description
 * @date 2021/8/20 17:53
 */
@Data
public class ApiUserDto implements Serializable {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 创建日期
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 1 表示删除，0 表示未删除
     */
    private Boolean isDeleted;
}
