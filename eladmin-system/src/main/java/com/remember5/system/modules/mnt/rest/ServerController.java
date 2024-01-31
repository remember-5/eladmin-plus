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
package com.remember5.system.modules.mnt.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.remember5.security.entity.PageResult;
import com.remember5.system.modules.logging.annotation.Log;
import com.remember5.system.modules.mnt.domain.Server;
import com.remember5.system.modules.mnt.domain.vo.ServerQueryCriteria;
import com.remember5.system.modules.mnt.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@RestController
@Tag(name = "运维：服务器管理")
@RequiredArgsConstructor
@RequestMapping("/api/serverDeploy")
public class ServerController {

    private final ServerService serverService;

    @Operation(summary = "导出服务器数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('serverDeploy:list')")
    public void exportServerDeploy(HttpServletResponse response, ServerQueryCriteria criteria) throws IOException {
        serverService.download(serverService.queryAll(criteria), response);
    }

    @Operation(summary = "查询服务器")
    @GetMapping
    @PreAuthorize("@el.check('serverDeploy:list')")
    public ResponseEntity<PageResult<Server>> queryServerDeploy(ServerQueryCriteria criteria, Page<Object> page) {
        return new ResponseEntity<>(serverService.queryAll(criteria, page), HttpStatus.OK);
    }

    @Log("新增服务器")
    @Operation(summary = "新增服务器")
    @PostMapping
    @PreAuthorize("@el.check('serverDeploy:add')")
    public ResponseEntity<Object> createServerDeploy(@Validated @RequestBody Server resources) {
        serverService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改服务器")
    @Operation(summary = "修改服务器")
    @PutMapping
    @PreAuthorize("@el.check('serverDeploy:edit')")
    public ResponseEntity<Object> updateServerDeploy(@Validated @RequestBody Server resources) {
        serverService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除服务器")
    @Operation(summary = "删除Server")
    @DeleteMapping
    @PreAuthorize("@el.check('serverDeploy:del')")
    public ResponseEntity<Object> deleteServerDeploy(@RequestBody Set<Long> ids) {
        serverService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("测试连接服务器")
    @Operation(summary = "测试连接服务器")
    @PostMapping("/testConnect")
    @PreAuthorize("@el.check('serverDeploy:add')")
    public ResponseEntity<Object> testConnectServerDeploy(@Validated @RequestBody Server resources) {
        return new ResponseEntity<>(serverService.testConnect(resources), HttpStatus.CREATED);
    }
}
