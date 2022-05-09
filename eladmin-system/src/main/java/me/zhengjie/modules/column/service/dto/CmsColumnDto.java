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
package me.zhengjie.modules.column.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author fly
* @date 2021-03-02
**/
@Data
public class CmsColumnDto implements Serializable {

    private Long id;

    /** 父id */
    private Long fid;

    /** 栏目名 */
    private String columnName;

    /** 代理商id */
    private Long agentId;

    /** 创建时间 */
    private Timestamp createTime;

    /** 修改时间 */
    private Timestamp updateTime;

    /** 1 表示删除，0 表示未删除 */
    private Boolean isDeleted;

    /** 备用字段 */
    private String by1;

    /** 备用字段 */
    private String by2;

    /** 备用字段 */
    private String by3;

    /** 备用字段 */
    private String by4;

    /** 备用字段 */
    private String by5;

    /** 栏目子集 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CmsColumnDto> columns;
}
