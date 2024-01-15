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
import com.remember5.core.base.BaseEntity;
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
@TableName("mnt_database")
public class Database extends BaseEntity implements Serializable {

    @TableId(value = "db_id", type = IdType.AUTO)
    @Schema(description = "ID", hidden = true)
    private String id;

    @Schema(description = "数据库名称")
    private String name;

    @Schema(description = "数据库连接地址")
    private String jdbcUrl;

    @Schema(description = "数据库密码")
    private String pwd;

    @Schema(description = "用户名")
    private String userName;

    public void copy(Database source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
