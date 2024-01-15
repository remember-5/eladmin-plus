/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.remember5.system.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.remember5.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Getter
@Setter
@TableName("sys_user")
public class User extends BaseEntity implements Serializable {

    @NotNull(groups = Update.class)
    @TableId(value = "user_id", type = IdType.AUTO)
    @Schema(description = "ID", hidden = true)
    private Long id;

    @TableField(exist = false)
    @Schema(description = "用户角色")
    private Set<Role> roles;

    @TableField(exist = false)
    @Schema(description = "用户岗位")
    private Set<Job> jobs;

    @TableField(value = "dept_id")
    @Schema(hidden = true)
    private Long deptId;

    @Schema(description = "用户部门")
    @TableField(exist = false)
    private Dept dept;

    @NotBlank
    @Schema(description = "用户名称")
    private String username;

    @NotBlank
    @Schema(description = "用户昵称")
    private String nickName;

    @Email
    @NotBlank
    @Schema(description = "邮箱")
    private String email;

    @NotBlank
    @Schema(description = "电话号码")
    private String phone;

    @Schema(description = "用户性别")
    private String gender;

    @Schema(description = "头像真实名称", hidden = true)
    private String avatarName;

    @Schema(description = "头像存储的路径", hidden = true)
    private String avatarPath;

    @Schema(description = "密码")
    private String password;

    @NotNull
    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "是否为admin账号", hidden = true)
    private Boolean isAdmin = false;

    @Schema(description = "最后修改密码的时间", hidden = true)
    private Date pwdResetTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
