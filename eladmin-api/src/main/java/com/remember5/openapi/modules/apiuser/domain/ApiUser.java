package com.remember5.openapi.modules.apiuser.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "m_user")
public class ApiUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Schema(description = "自增主键")
    private Long id;

    @Column(name = "username")
    @Schema(description = "用户名")
    private String username;

    @Column(name = "password")
    @Schema(description = "密码")
    private String password;

    @Column(name = "phone")
    @Schema(description = "手机号")
    private String phone;

    @Column(name = "create_time")
    @CreationTimestamp
    @Schema(description = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @Schema(description = "更新时间")
    private Timestamp updateTime;

    @Column(name = "is_deleted")
    @Schema(description = "1 表示删除，0 表示未删除")
    private Boolean isDeleted;

    public void copy(ApiUser source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
