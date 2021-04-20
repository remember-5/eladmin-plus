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
package me.zhengjie.modules.tool.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.modules.tool.service.MessageNotificationService;
import me.zhengjie.modules.tool.service.dto.MessageNotificationQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author fly
* @date 2021-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "消息通知管理")
@RequestMapping("/api/messageNotification")
public class MessageNotificationController {

    private final MessageNotificationService messageNotificationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('messageNotification:list')")
    public void download(HttpServletResponse response, MessageNotificationQueryCriteria criteria) throws IOException {
        messageNotificationService.download(messageNotificationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询消息通知")
    @ApiOperation("查询消息通知")
    @PreAuthorize("@el.check('messageNotification:list')")
    public ResponseEntity<Object> query(MessageNotificationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(messageNotificationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/getMessage")
    @Log("查询未处理消息通知")
    @ApiOperation("查询未处理消息通知")
    public ResponseEntity<Object> findMessageByUserId(){
        return new ResponseEntity<>(messageNotificationService.findMessageByUserId(),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增消息通知")
    @ApiOperation("新增消息通知")
    @PreAuthorize("@el.check('messageNotification:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MessageNotification resources){
        return new ResponseEntity<>(messageNotificationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改消息通知")
    @ApiOperation("修改消息通知")
    @PreAuthorize("@el.check('messageNotification:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MessageNotification resources){
        messageNotificationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除消息通知")
    @ApiOperation("删除消息通知")
    @PreAuthorize("@el.check('messageNotification:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        messageNotificationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
