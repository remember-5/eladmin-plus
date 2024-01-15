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
package com.remember5.system.modules.generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 代码生成配置
 *
 * @author Zheng Jie
 * @date 2019-01-03
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("code_gen_config")
public class GenConfig implements Serializable {

    public GenConfig(String tableName) {
        this.tableName = tableName;
    }

    @Schema(description = "ID", hidden = true)
    @TableId(value = "config_id", type = IdType.AUTO)
    private Long id;

    @NotBlank
    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "接口名称")
    private String apiAlias;

    @NotBlank
    @Schema(description = "包路径")
    private String pack;

    @NotBlank
    @Schema(description = "模块名")
    private String moduleName;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "表前缀")
    private String prefix = "t_";

    @Schema(description = "是否覆盖")
    private Boolean cover = false;

    @NotBlank
    @Schema(description = "前端文件路径")
    private String path;

    @TableField(value = "api_path")
    @Schema(description = "前端接口文件路径")
    private String apiPath;

    @TableField(value = "component_path")
    @Schema(description = "组件相对路径")
    private String componentPath;

    @TableField(value = "menu_headline")
    @Schema(description = "菜单标题")
    private String menuHeadline;

    @TableField(value = "routing_address")
    @Schema(description = "路由地址")
    private String routingAddress;

    @TableField(value = "admin_jurisdiction")
    @Schema(description = "是否添加到管理员权限上")
    private Boolean adminJurisdiction = false;

    @TableField(value = "auto_generate_menu")
    @Schema(description = "自动生成菜单")
    private Boolean autoGenerateMenu = false;
}
