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
package com.remember5.system.modules.logging.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.remember5.security.entity.PageResult;
import com.remember5.system.modules.logging.annotation.Log;
import com.remember5.system.modules.logging.domain.SysLog;
import com.remember5.system.modules.logging.domain.vo.SysLogQueryCriteria;
import com.remember5.system.modules.logging.service.SysLogService;
import com.remember5.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Tag(name = "系统：日志管理")
public class SysLogController {

    private final SysLogService sysLogService;

    @Log("导出数据")
    @Operation(summary = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('logs:list')")
    public void exportLog(HttpServletResponse response, SysLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("INFO");
        sysLogService.download(sysLogService.queryAll(criteria), response);
    }

    @Log("导出错误数据")
    @Operation(summary = "导出错误数据")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@el.check('errorlogs:list')")
    public void exportErrorLog(HttpServletResponse response, SysLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("ERROR");
        sysLogService.download(sysLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Operation(summary = "日志查询")
    @PreAuthorize("@el.check('logs:list')")
    public ResponseEntity<PageResult<SysLog>> queryLog(SysLogQueryCriteria criteria, Page<SysLog> page) {
        criteria.setLogType("INFO");
        return new ResponseEntity<>(sysLogService.queryAll(criteria, page), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    @Operation(summary = "用户日志查询")
    public ResponseEntity<PageResult<SysLog>> queryUserLog(SysLogQueryCriteria criteria, Page<SysLog> page) {
        criteria.setLogType("INFO");
        criteria.setUsername(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(sysLogService.queryAllByUser(criteria, page), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @Operation(summary = "错误日志查询")
    @PreAuthorize("@el.check('errorlogs:list')")
    public ResponseEntity<PageResult<SysLog>> queryErrorLog(SysLogQueryCriteria criteria, Page<SysLog> page) {
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(sysLogService.queryAll(criteria, page), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @Operation(summary = "日志异常详情查询")
    @PreAuthorize("@el.check('errorlogs:details')")
    public ResponseEntity<Object> queryErrorLogDetail(@PathVariable Long id) {
        return new ResponseEntity<>(sysLogService.findByErrDetail(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @Operation(summary = "删除所有ERROR日志")
    @PreAuthorize("@el.check('errorlogs:del')")
    public ResponseEntity<Object> delAllErrorLog() {
        sysLogService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @Operation(summary = "删除所有INFO日志")
    @PreAuthorize("@el.check('logs:del')")
    public ResponseEntity<Object> delAllInfoLog() {
        sysLogService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
