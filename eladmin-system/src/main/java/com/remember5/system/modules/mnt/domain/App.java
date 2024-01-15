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
package com.remember5.system.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.remember5.security.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Getter
@Setter
@TableName("mnt_app")
public class App extends BaseEntity implements Serializable {


    @TableId(value = "app_id", type = IdType.AUTO)
    @Schema(description = "ID", hidden = true)
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "端口")
    private int port;

    @Schema(description = "上传路径")
    private String uploadPath;

    @Schema(description = "部署路径")
    private String deployPath;

    @Schema(description = "备份路径")
    private String backupPath;

    @Schema(description = "启动脚本")
    private String startScript;

    @Schema(description = "部署脚本")
    private String deployScript;

    public void copy(App source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
