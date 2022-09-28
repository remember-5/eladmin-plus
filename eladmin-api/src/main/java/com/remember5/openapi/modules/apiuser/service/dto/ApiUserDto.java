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
     * 备用字段1
     */
    private String ex1;

    /**
     * 备用字段2
     */
    private String ex2;

    /**
     * 备用字段3
     */
    private String ex3;

    /**
     * 备用字段4
     */
    private String ex4;

    /**
     * 备用字段5
     */
    private String ex5;

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
