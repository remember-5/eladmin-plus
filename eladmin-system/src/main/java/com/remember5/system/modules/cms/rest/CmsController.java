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
package com.remember5.system.modules.cms.rest;

import com.remember5.system.modules.cms.domain.Cms;
import com.remember5.system.modules.cms.service.CmsService;
import com.remember5.system.modules.cms.service.dto.CmsDto;
import com.remember5.system.modules.cms.service.dto.CmsQueryCriteria;
import com.remember5.security.logging.annotation.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangenrong
 * @website https://el-admin.vip
 * @date 2021-03-01
 **/
@RestController
@RequiredArgsConstructor
@Tag(name = "cms文章管理")
@RequestMapping("/api/cms")
public class CmsController {

    private final CmsService cmsService;

    @Log("导出数据")
    @Operation(summary = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cms:list')")
    public void download(HttpServletResponse response, CmsQueryCriteria criteria) throws IOException {
        cmsService.download(cmsService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询cms文章")
    @Operation(summary = "查询cms文章")
    @PreAuthorize("@el.check('cms:list')")
    public ResponseEntity<Object> query(CmsQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(cmsService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping("esQuery")
    @Log("查询cms文章")
    @Operation(summary = "查询cms文章")
    @PreAuthorize("@el.check('cms:list')")
    public ResponseEntity<Object> query(@RequestBody String es) {
        CmsQueryCriteria cmsQueryCriteria = new CmsQueryCriteria();
        return new ResponseEntity<>(cmsService.queryAll(cmsQueryCriteria), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增cms文章")
    @Operation(summary = "新增cms文章")
    @PreAuthorize("@el.check('cms:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Cms resources) {
        // 设置状态
        resources.setCmsStatus(0);
        resources.setAuditStatus(0);
        //设置数据状态，0为有效，1为删除
        resources.setIsDeleted(false);
        CmsDto cmsDto = cmsService.create(resources);
        return new ResponseEntity<>(cmsDto, HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改cms文章")
    @Operation(summary = "修改cms文章")
    @PreAuthorize("@el.check('cms:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Cms resources) {
        cmsService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/audit")
    @Log("审核cms文章")
    @Operation(summary = "审核cms文章")
    @PreAuthorize("@el.check('cms:edit')")
    public ResponseEntity<Object> audit(@Validated @RequestBody Cms resources) {
        cmsService.audit(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/publish")
    @Log("发布cms文章")
    @Operation(summary = "发布cms文章")
    @PreAuthorize("@el.check('cms:edit')")
    public ResponseEntity<Object> publish(@Validated @RequestBody Cms resources) {
        cmsService.publish(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除cms文章")
    @Operation(summary = "删除cms文章")
    @PreAuthorize("@el.check('cms:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        cmsService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @Log("查询cms")
    @Operation(summary = "查询cms")
    public void query() {
        System.out.println("1111111111111111111");
    }
}
