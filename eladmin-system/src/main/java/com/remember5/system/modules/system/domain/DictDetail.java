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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Zheng Jie
 * @date 2019-04-10
 */
@Getter
@Setter
@TableName("sys_dict_detail")
public class DictDetail extends BaseEntity implements Serializable {

    @NotNull(groups = Update.class)
    @Schema(description = "ID", hidden = true)
    @TableId(value = "detail_id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "dict_id")
    @Schema(hidden = true)
    private Long dictId;

    @TableField(exist = false)
    private Dict dict;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "排序")
    private Integer dictSort = 999;
}
