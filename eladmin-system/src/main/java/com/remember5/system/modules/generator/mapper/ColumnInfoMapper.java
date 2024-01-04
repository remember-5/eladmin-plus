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
package com.remember5.system.modules.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.remember5.system.modules.generator.domain.ColumnInfo;
import com.remember5.system.modules.generator.domain.vo.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2023-06-26
 */
@Mapper
public interface ColumnInfoMapper extends BaseMapper<ColumnInfo> {

    /**
     * 获取mysql的tables
     *
     * @param tableName 表名
     * @param page      分页
     * @return 表
     */
    IPage<TableInfo> getTables(@Param("tableName") String tableName, Page<Object> page);

    /**
     * 获取pg的tables
     *
     * @param schemaName schemas
     * @param tableName  表名
     * @param page       分页
     * @return 表
     */
    IPage<TableInfo> getPostgresTables(@Param("schemaName") String schemaName, @Param("tableName") String tableName, Page<Object> page);

    List<ColumnInfo> findByTableNameOrderByIdAsc(@Param("tableName") String tableName);

    /**
     * 获取mysql的column详情
     *
     * @param tableName 表名
     * @return column
     */
    List<ColumnInfo> getColumns(@Param("tableName") String tableName);

    /**
     * 获取postgres的column详情
     *
     * @param tableName 表名
     * @return column
     */
    List<ColumnInfo> getPostgresColumns(@Param("tableName") String tableName);


}
