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
package me.zhengjie.modules.cms.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.cms.domain.Cms;
import me.zhengjie.modules.cms.service.CmsService;
import me.zhengjie.modules.cms.service.dto.CmsDto;
import me.zhengjie.modules.cms.service.dto.CmsQueryCriteria;
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
@Api(tags = "cms文章管理")
@RequestMapping("/api/cms")
public class CmsController {

    private final CmsService cmsService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cms:list')")
    public void download(HttpServletResponse response, CmsQueryCriteria criteria) throws IOException {
        cmsService.download(cmsService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询cms文章")
    @ApiOperation("查询cms文章")
    @PreAuthorize("@el.check('cms:list')")
    public ResponseEntity<Object> query(CmsQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(cmsService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping("esQuery")
    @Log("查询cms文章")
    @ApiOperation("查询cms文章")
    @PreAuthorize("@el.check('cms:list')")
    public ResponseEntity<Object> query(@RequestBody String es) {
        CmsQueryCriteria cmsQueryCriteria = new CmsQueryCriteria();
        return new ResponseEntity<>(cmsService.queryAll(cmsQueryCriteria), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增cms文章")
    @ApiOperation("新增cms文章")
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
    @ApiOperation("修改cms文章")
    @PreAuthorize("@el.check('cms:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Cms resources) {
        cmsService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/audit")
    @Log("审核cms文章")
    @ApiOperation("审核cms文章")
    @PreAuthorize("@el.check('cms:edit')")
    public ResponseEntity<Object> audit(@Validated @RequestBody Cms resources) {
        cmsService.audit(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/publish")
    @Log("发布cms文章")
    @ApiOperation("发布cms文章")
    @PreAuthorize("@el.check('cms:edit')")
    public ResponseEntity<Object> publish(@Validated @RequestBody Cms resources) {
        cmsService.publish(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除cms文章")
    @ApiOperation("删除cms文章")
    @PreAuthorize("@el.check('cms:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        cmsService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @Log("查询cms")
    @ApiOperation("查询cms")
    public void query() {
        System.out.println("1111111111111111111");
    }
}
