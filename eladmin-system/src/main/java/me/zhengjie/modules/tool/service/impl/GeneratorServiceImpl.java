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
package me.zhengjie.modules.tool.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.generator.domain.GenConfig;
import me.zhengjie.modules.generator.domain.ColumnInfo;
import me.zhengjie.modules.generator.domain.vo.TableInfo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.generator.repository.ColumnInfoRepository;
import me.zhengjie.modules.generator.service.AutoPermissionService;
import me.zhengjie.modules.generator.service.GeneratorService;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.generator.utils.GenUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {
    private static final Logger log = LoggerFactory.getLogger(GeneratorServiceImpl.class);
    @PersistenceContext
    private EntityManager em;

    private final ColumnInfoRepository columnInfoRepository;
    private final AutoPermissionService autoPermissionService;
    @Override
    public Object getTables() {
        // ?????????????????????sql??????
        String sql = "select table_name ,create_time , engine, table_collation, table_comment from information_schema.tables " +
                "where table_schema = (select database()) " +
                "order by create_time desc";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public Object getTables(String name, int[] startEnd) {
        // ?????????????????????sql??????
        String dbSchema= StringUtils.isNotEmpty(System.getenv("DB_SCHEMA"))?System.getenv("DB_SCHEMA"):"eladmin";

        String sql = "SELECT pt.tablename AS table_name, pc.comment as table_comment from pg_tables pt " +
                " LEFT JOIN (SELECT relname,cast (obj_description (relfilenode, 'pg_class') as varchar) as comment  from pg_class ) pc " +
                " ON pc.relname = pt.tablename " +
                "where schemaname= '"+dbSchema+"'"+
                "AND tablename LIKE ?";
        Query query = em.createNativeQuery(sql);
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1] - startEnd[0]);
        query.setParameter(1, StringUtils.isNotBlank(name) ? ("%" + name + "%") : "%%");
        List result = query.getResultList();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            tableInfos.add(new TableInfo(arr[0], null, null, null, ObjectUtil.isNotEmpty(arr[1]) ? arr[1] : "-"));
        }
        Query query1 = em.createNativeQuery("SELECT COUNT(*) FROM pg_tables WHERE schemaname='eladmin_template_schema'");
        Object totalElements = query1.getSingleResult();
        return PageUtil.toPage(tableInfos, totalElements);
    }

    @Override
    public List<ColumnInfo> getColumns(String tableName) {
        List<ColumnInfo> columnInfos = columnInfoRepository.findByTableNameOrderByIdAsc(tableName);
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            return columnInfos;
        } else {
            columnInfos = query(tableName);
            return columnInfoRepository.saveAll(columnInfos);
        }
    }

    @Override
    public List<ColumnInfo> query(String tableName) {
        // ?????????????????????sql??????
        String sql = "SELECT pg_attribute.attname as column_name,pg_attribute.attnotnull as is_nullable,pt.typname as data_type,col_description (pg_attribute.attrelid, pg_attribute.attnum) as column_comment,CASE WHEN (pg_attribute.attnum = ANY(pi.indkey)) THEN 'PRI' ELSE '' END as column_key,('') as extra " +
                "FROM pg_attribute " +
                "LEFT JOIN pg_class pc ON pc.oid = pg_attribute.attrelid " +
                "LEFT JOIN pg_index pi ON pi.indrelid =  pg_attribute.attrelid " +
                "LEFT JOIN pg_type pt ON pt.oid = pg_attribute.atttypid " +
                "WHERE pc.relname = ? AND pg_attribute.attnum > 0";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, tableName);
        List result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            columnInfos.add(
                    new ColumnInfo(
                            tableName,
                            arr[0].toString(),
                            "t".equals(arr[1]),
                            arr[2].toString(),
                            ObjectUtil.isNotNull(arr[3]) ? arr[3].toString() : "",
                            ObjectUtil.isNotNull(arr[4]) ? arr[4].toString() : null,
                            ObjectUtil.isNotNull(arr[5]) ? arr[5].toString() : null)
            );
        }
        return columnInfos;
    }

    @Override
    public void sync(List<ColumnInfo> columnInfos, List<ColumnInfo> columnInfoList) {
        // ????????????????????????????????????????????????????????????
        for (ColumnInfo columnInfo : columnInfoList) {
            // ????????????????????????
            List<ColumnInfo> columns = columnInfos.stream().filter(c -> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList());
            // ????????????????????????????????????????????????
            if (CollectionUtil.isNotEmpty(columns)) {
                ColumnInfo column = columns.get(0);
                column.setColumnType(columnInfo.getColumnType());
                column.setExtra(columnInfo.getExtra());
                column.setKeyType(columnInfo.getKeyType());
                if (StringUtils.isBlank(column.getRemark())) {
                    column.setRemark(columnInfo.getRemark());
                }
                columnInfoRepository.save(column);
            } else {
                // ??????????????????????????????????????????
                columnInfoRepository.save(columnInfo);
            }
        }
        // ??????????????????????????????????????????
        for (ColumnInfo columnInfo : columnInfos) {
            // ????????????????????????
            List<ColumnInfo> columns = columnInfoList.stream().filter(c -> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList());
            // ????????????????????????????????????????????????????????????????????????
            if (CollectionUtil.isEmpty(columns)) {
                columnInfoRepository.delete(columnInfo);
            }
        }
    }

    @Override
    public void save(List<ColumnInfo> columnInfos) {
        columnInfoRepository.saveAll(columnInfos);
    }

    @Override
    public void generator(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BadRequestException("?????????????????????");
        }
        try {
            GenUtil.generatorCode(columns, genConfig, autoPermissionService);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("????????????????????????????????????????????????");
        }
    }

    @Override
    public ResponseEntity<Object> preview(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BadRequestException("?????????????????????");
        }
        List<Map<String, Object>> genList = GenUtil.preview(columns, genConfig);
        return new ResponseEntity<>(genList, HttpStatus.OK);
    }

    @Override
    public void download(GenConfig genConfig, List<ColumnInfo> columns, HttpServletRequest request, HttpServletResponse response) {
        if (genConfig.getId() == null) {
            throw new BadRequestException("?????????????????????");
        }
        try {
            File file = new File(GenUtil.download(columns, genConfig));
            String zipPath = file.getPath() + ".zip";
            ZipUtil.zip(file.getPath(), zipPath);
            FileUtil.downloadFile(request, response, new File(zipPath), true);
        } catch (IOException e) {
            throw new BadRequestException("????????????");
        }
    }
}
