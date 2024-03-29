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
package com.remember5.system.modules.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.remember5.core.exception.BadRequestException;
import com.remember5.core.utils.FileUtil;
import com.remember5.security.entity.PageResult;
import com.remember5.security.utils.PageUtil;
import com.remember5.core.utils.StringUtils;
import com.remember5.system.modules.generator.domain.ColumnInfo;
import com.remember5.system.modules.generator.domain.GenConfig;
import com.remember5.system.modules.generator.domain.vo.TableInfo;
import com.remember5.system.modules.generator.mapper.ColumnInfoMapper;
import com.remember5.system.modules.generator.service.AutoPermissionService;
import com.remember5.system.modules.generator.service.GeneratorService;
import com.remember5.system.modules.generator.utils.GenUtil;
import com.remember5.system.properties.GeneratorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl extends ServiceImpl<ColumnInfoMapper, ColumnInfo> implements GeneratorService {

    private final ColumnInfoMapper columnInfoMapper;
    private final AutoPermissionService autoPermissionService;
    private final DynamicDataSourceProperties dynamicDataSourceProperties;
    private static final String CONFIG_MESSAGE = "请先配置生成器";


    @Override
    public List<String> getDatasource() {
        final Map<String, DataSourceProperty> datasource = dynamicDataSourceProperties.getDatasource();
        return datasource.keySet().stream().toList();
    }

    @Override
    public PageResult<TableInfo> getTables(String name, Page<Object> page) {
        // todo 使用druid获取当前连接的数据库类型, 获取primary 数据库
        return switch (GeneratorProperties.DATABASE_TYPE) {
            case GeneratorProperties.POSTGRES -> {
                // 尝试从druid中获取schema
                String dbSchema = StringUtils.isNotEmpty(System.getenv("DB_SCHEMA")) ? System.getenv("DB_SCHEMA") : "eladmin";
                yield PageUtil.toPage(columnInfoMapper.getPostgresTables(dbSchema, name, page));
            }
            case GeneratorProperties.MYSQL -> PageUtil.toPage(columnInfoMapper.getTables(name, page));
            default -> PageUtil.toPage(Lists.newArrayList());
        };
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ColumnInfo> getColumns(String tableName) {
        List<ColumnInfo> columnInfos = columnInfoMapper.findByTableNameOrderByIdAsc(tableName);
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            return columnInfos;
        } else {
            columnInfos = query(tableName);
            saveBatch(columnInfos);
            return columnInfos;
        }
    }

    @Override
    public List<ColumnInfo> query(String tableName) {
        List<ColumnInfo> columnInfos = switch (GeneratorProperties.DATABASE_TYPE) {
            case GeneratorProperties.POSTGRES -> columnInfoMapper.getPostgresColumns(tableName);
            case GeneratorProperties.MYSQL -> columnInfoMapper.getColumns(tableName);
            default -> Lists.newArrayList();
        };
        for (ColumnInfo columnInfo : columnInfos) {
            columnInfo.setTableName(tableName);
            if (GenUtil.PK.equalsIgnoreCase(columnInfo.getKeyType())
                    && GenUtil.EXTRA.equalsIgnoreCase(columnInfo.getExtra())) {
                columnInfo.setNotNull(false);
            }
        }
        return columnInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(List<ColumnInfo> columnInfos, List<ColumnInfo> columnInfoList) {
        // 第一种情况，数据库类字段改变或者新增字段
        for (ColumnInfo columnInfo : columnInfoList) {
            // 根据字段名称查找
            List<ColumnInfo> columns = columnInfos.stream().filter(c -> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList());
            // 如果能找到，就修改部分可能被字段
            if (CollectionUtil.isNotEmpty(columns)) {
                ColumnInfo column = columns.get(0);
                column.setColumnType(columnInfo.getColumnType());
                column.setExtra(columnInfo.getExtra());
                column.setKeyType(columnInfo.getKeyType());
                if (StringUtils.isBlank(column.getRemark())) {
                    column.setRemark(columnInfo.getRemark());
                }
                saveOrUpdate(column);
            } else {
                // 如果找不到，则保存新字段信息
                save(columnInfo);
            }
        }
        // 第二种情况，数据库字段删除了
        for (ColumnInfo columnInfo : columnInfos) {
            // 根据字段名称查找
            List<ColumnInfo> columns = columnInfoList.stream().filter(c -> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList());
            // 如果找不到，就代表字段被删除了，则需要删除该字段
            if (CollectionUtil.isEmpty(columns)) {
                removeById(columnInfo);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<ColumnInfo> columnInfos) {
        saveOrUpdateBatch(columnInfos);
    }

    @Override
    public void generator(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BadRequestException(CONFIG_MESSAGE);
        }
        try {
            GenUtil.generatorCode(columns, genConfig, autoPermissionService);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("生成失败，请手动处理已生成的文件");
        }
    }

    @Override
    public ResponseEntity<Object> preview(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BadRequestException(CONFIG_MESSAGE);
        }
        List<Map<String, Object>> genList = GenUtil.preview(columns, genConfig);
        return new ResponseEntity<>(genList, HttpStatus.OK);
    }

    @Override
    public void download(GenConfig genConfig, List<ColumnInfo> columns, HttpServletRequest request, HttpServletResponse response) {
        if (genConfig.getId() == null) {
            throw new BadRequestException(CONFIG_MESSAGE);
        }
        try {
            File file = new File(GenUtil.download(columns, genConfig));
            String zipPath = file.getPath() + ".zip";
            ZipUtil.zip(file.getPath(), zipPath);
            FileUtil.downloadFile(request, response, new File(zipPath), true);
        } catch (IOException e) {
            throw new BadRequestException("打包失败");
        }
    }
}
