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
import com.remember5.system.modules.generator.service.GeneratorService;
import com.remember5.system.properties.GeneratorProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "系统：代码生成管理")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final GenConfigService genConfigService;


    @Operation(summary = "查询数据库数据")
    @GetMapping(value = "/tables/all")
    public ResponseEntity<Object> queryAllTables() {
        return new ResponseEntity<>(generatorService.getTables(), HttpStatus.OK);
    }

    @Operation(summary = "查询数据库数据")
    @GetMapping(value = "/tables")
    public ResponseEntity<Object> queryTables(@RequestParam(defaultValue = "") String name,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        // TODO 当用public的schema的时候 并不设置环境变量DB_SCHEMA 这里的分页可能有问题。
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return new ResponseEntity<>(generatorService.getTables(name, startEnd), HttpStatus.OK);
    }

    @Operation(summary = "查询字段数据")
    @GetMapping(value = "/columns")
    public ResponseEntity<Object> queryColumns(@RequestParam String tableName) {
        List<ColumnInfo> pgColumnInfos = generatorService.getColumns(tableName);
        return new ResponseEntity<>(PageUtil.toPage(pgColumnInfos, pgColumnInfos.size()), HttpStatus.OK);
    }

    @Operation(summary = "保存字段数据")
    @PutMapping
    public ResponseEntity<HttpStatus> saveColumn(@RequestBody List<ColumnInfo> columnInfos) {
        generatorService.save(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "同步字段数据")
    @PostMapping(value = "sync")
    public ResponseEntity<HttpStatus> syncColumn(@RequestBody List<String> tables) {
        for (String table : tables) {
            generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public ResponseEntity<Object> generatorCode(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (!GeneratorProperties.ENABLED && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        switch (type) {
            // 生成代码
            case 0:
                generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                break;
            // 预览
            case 1:
                return generatorService.preview(genConfigService.find(tableName), generatorService.getColumns(tableName));
            // 打包
            case 2:
                generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName), request, response);
                break;
            default:
                throw new BadRequestException("没有这个选项");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
