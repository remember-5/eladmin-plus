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
import com.remember5.security.entity.BaseEntity;
import com.remember5.core.enums.DataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 角色
 *
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Getter
@Setter
@TableName("sys_role")
public class Role extends BaseEntity implements Serializable {

    @NotNull(groups = {Update.class})
    @TableId(value = "role_id", type = IdType.AUTO)
    @Schema(description = "ID", hidden = true)
    private Long id;

    @TableField(exist = false)
    @Schema(description = "用户", hidden = true)
    private Set<User> users;

    @TableField(exist = false)
    @Schema(description = "菜单", hidden = true)
    private Set<Menu> menus;

    @TableField(exist = false)
    @Schema(description = "部门", hidden = true)
    private Set<Dept> depts;

    @NotBlank
    @Schema(description = "名称", hidden = true)
    private String name;

    @Schema(description = "数据权限，全部 、 本级 、 自定义")
    private String dataScope = DataScopeEnum.THIS_LEVEL.getValue();

    @Schema(description = "级别，数值越小，级别越大")
    private Integer level = 3;

    @Schema(description = "描述")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
