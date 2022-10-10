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
package com.remember5.system.modules.generator.rest;

import com.remember5.core.exception.BadRequestException;
import com.remember5.core.utils.PageUtil;
import com.remember5.system.modules.generator.domain.ColumnInfo;
import com.remember5.system.modules.generator.service.GenConfigService;
import com.remember5.system.modules.tool.service.impl.MysqlGeneratorServiceImpl;
import com.remember5.system.modules.tool.service.impl.PgGeneratorServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/generator")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final PgGeneratorServiceImpl pgGeneratorService;
    private final MysqlGeneratorServiceImpl mysqlGeneratorService;

    private final GenConfigService genConfigService;

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;
    @Value("${generator.database-type}")
    private String databaseType;

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables/all")
    public ResponseEntity<Object> queryAllTables() {
        switch(databaseType){
            case "postgres":
                return new ResponseEntity<>(pgGeneratorService.getTables(), HttpStatus.OK);
            case "mysql":
                return new ResponseEntity<>(mysqlGeneratorService.getTables(), HttpStatus.OK);
            default:
               return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables")
    public ResponseEntity<Object> queryTables(@RequestParam(defaultValue = "") String name,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        // TODO 当用public的schema的时候 并不设置环境变量DB_SCHEMA 这里的分页可能有问题。
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        switch(databaseType){
            case "postgres":
                return new ResponseEntity<>(pgGeneratorService.getTables(name, startEnd), HttpStatus.OK);
            case "mysql":
                return new ResponseEntity<>(mysqlGeneratorService.getTables(name, startEnd), HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @ApiOperation("查询字段数据")
    @GetMapping(value = "/columns")
    public ResponseEntity<Object> queryColumns(@RequestParam String tableName) {
        switch(databaseType){
            case "postgres":
                List<ColumnInfo> pgColumnInfos = pgGeneratorService.getColumns(tableName);
                return new ResponseEntity<>(PageUtil.toPage(pgColumnInfos, pgColumnInfos.size()), HttpStatus.OK);
            case "mysql":
                List<ColumnInfo> mysqlColumnInfos = mysqlGeneratorService.getColumns(tableName);
                return new ResponseEntity<>(PageUtil.toPage(mysqlColumnInfos, mysqlColumnInfos.size()), HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @ApiOperation("保存字段数据")
    @PutMapping
    public ResponseEntity<HttpStatus> saveColumn(@RequestBody List<ColumnInfo> columnInfos) {
        switch(databaseType){
            case "postgres":
                pgGeneratorService.save(columnInfos);
                break;
            case "mysql":
                mysqlGeneratorService.save(columnInfos);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("同步字段数据")
    @PostMapping(value = "sync")
    public ResponseEntity<HttpStatus> syncColumn(@RequestBody List<String> tables) {
        switch(databaseType){
            case "postgres":
                for (String table : tables) {
                    pgGeneratorService.sync(pgGeneratorService.getColumns(table), pgGeneratorService.query(table));
                }
                break;
            case "mysql":
                for (String table : tables) {
                    mysqlGeneratorService.sync(mysqlGeneratorService.getColumns(table), mysqlGeneratorService.query(table));
                }
                break;
            default:
                return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public ResponseEntity<Object> generatorCode(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        switch(databaseType){
            case "postgres":
                switch (type) {
                    // 生成代码
                    case 0:
                        pgGeneratorService.generator(genConfigService.find(tableName), pgGeneratorService.getColumns(tableName));
                        break;
                    // 预览
                    case 1:
                        return pgGeneratorService.preview(genConfigService.find(tableName), pgGeneratorService.getColumns(tableName));
                    // 打包
                    case 2:
                        pgGeneratorService.download(genConfigService.find(tableName), pgGeneratorService.getColumns(tableName), request, response);
                        break;
                    default:
                        throw new BadRequestException("没有这个选项");
                }
                break;
            case "mysql":
                switch (type) {
                    // 生成代码
                    case 0:
                        mysqlGeneratorService.generator(genConfigService.find(tableName), mysqlGeneratorService.getColumns(tableName));
                        break;
                    // 预览
                    case 1:
                        return mysqlGeneratorService.preview(genConfigService.find(tableName), mysqlGeneratorService.getColumns(tableName));
                    // 打包
                    case 2:
                        mysqlGeneratorService.download(genConfigService.find(tableName), mysqlGeneratorService.getColumns(tableName), request, response);
                        break;
                    default:
                        throw new BadRequestException("没有这个选项");
                }
                break;
            default:
                return new ResponseEntity<>(HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
