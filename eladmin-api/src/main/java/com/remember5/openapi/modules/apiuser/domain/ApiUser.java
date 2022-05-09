package com.remember5.openapi.modules.apiuser.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fly
 * @description
 * @date 2021/8/20 17:46
 */
@Entity
@Data
@Table(name="m_user")
public class ApiUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ApiModelProperty(value = "自增主键")
    private Long id;

    @Column(name = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Column(name = "ex1")
    @ApiModelProperty(value = "备用字段1")
    private String ex1;

    @Column(name = "ex2")
    @ApiModelProperty(value = "备用字段2")
    private String ex2;

    @Column(name = "ex3")
    @ApiModelProperty(value = "备用字段3")
    private String ex3;

    @Column(name = "ex4")
    @ApiModelProperty(value = "备用字段4")
    private String ex4;

    @Column(name = "ex5")
    @ApiModelProperty(value = "备用字段5")
    private String ex5;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "1 表示删除，0 表示未删除")
    private Boolean isDeleted;

    public void copy(ApiUser source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
