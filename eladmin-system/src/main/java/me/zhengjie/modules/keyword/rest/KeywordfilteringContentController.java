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
package me.zhengjie.modules.keyword.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.keyword.domain.KeywordfilteringContent;
import me.zhengjie.modules.keyword.service.KeywordfilteringContentService;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringContentQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @website https://el-admin.vip
* @author tianhh
* @date 2021-04-21
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "关键字详情管理")
@RequestMapping("/api/keywordfilteringContent")
public class KeywordfilteringContentController {

    private final KeywordfilteringContentService keywordfilteringContentService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('keywordfilteringContent:list')")
    public void download(HttpServletResponse response, KeywordfilteringContentQueryCriteria criteria) throws IOException {
        keywordfilteringContentService.download(keywordfilteringContentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询关键字详情")
    @ApiOperation("查询关键字详情")
    @PreAuthorize("@el.check('keywordfilteringContent:list')")
    public ResponseEntity<Object> query(KeywordfilteringContentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(keywordfilteringContentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增关键字详情")
    @ApiOperation("新增关键字详情")
    @PreAuthorize("@el.check('keywordfilteringContent:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody KeywordfilteringContent resources){
        return new ResponseEntity<>(keywordfilteringContentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改关键字详情")
    @ApiOperation("修改关键字详情")
    @PreAuthorize("@el.check('keywordfilteringContent:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody KeywordfilteringContent resources){
        keywordfilteringContentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除关键字详情")
    @ApiOperation("删除关键字详情")
    @PreAuthorize("@el.check('keywordfilteringContent:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        keywordfilteringContentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
