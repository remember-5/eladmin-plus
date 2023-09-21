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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@Entity
@NoArgsConstructor
@Table(name = "code_gen_config")
public class GenConfig implements Serializable {

    public GenConfig(String tableName) {
        this.tableName = tableName;
        this.prefix = "t_";
    }

    @Id
    @Column(name = "config_id")
    @Schema(description = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotBlank
    @Schema(description = "前端文件路径")
    private String path;

    @Schema(description = "前端接口文件路径")
    private String apiPath;
    @Schema(description = "是否相对路径")
    private Boolean relativePath = false;

    @Schema(description = "是否添加到管理员权限上")
    private Boolean adminJurisdiction = false;
    @Schema(description = "组件相对路径")
    private String componentPath;
    @Schema(description = "菜单标题")
    private String menuHeadline;

    @Schema(description = "路由地址")
    private String routingAddress;
    @Schema(description = "自动生成菜单")
    private Boolean autoGenerateMenu = false;
    @Schema(description = "作者")
    private String author;

    @Schema(description = "表前缀")
    private String prefix;

    @Schema(description = "是否覆盖")
    private Boolean cover = false;
}
