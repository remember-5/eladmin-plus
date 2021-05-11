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
package me.zhengjie.modules.sms.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.sms.domain.SmsRecord;
import me.zhengjie.modules.sms.repository.SmsRecordRepository;
import me.zhengjie.modules.sms.service.SmsRecordService;
import me.zhengjie.modules.sms.service.dto.SmsRecordQueryCriteria;
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
* @author wh
* @date 2021-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "短信信息管理")
@RequestMapping("/api/smsRecord")
public class SmsRecordController {

    private final SmsRecordService smsRecordService;
    private final SmsRecordRepository smsRecordRepository;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('smsRecord:list')")
    public void download(HttpServletResponse response, SmsRecordQueryCriteria criteria) throws IOException {
        smsRecordService.download(smsRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询短信信息")
    @ApiOperation("查询短信信息")
    @PreAuthorize("@el.check('smsRecord:list')")
    public ResponseEntity<Object> query(SmsRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(smsRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增短信信息")
    @ApiOperation("新增短信信息")
    @PreAuthorize("@el.check('smsRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SmsRecord resources){
        return new ResponseEntity<>(smsRecordService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改短信信息")
    @ApiOperation("修改短信信息")
    @PreAuthorize("@el.check('smsRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SmsRecord resources){
        smsRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除短信信息")
    @ApiOperation("删除短信信息")
    @PreAuthorize("@el.check('smsRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        smsRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/test")
    public void test(String key){
        if (!key.equals("")&&key.equals("860f349a24b3c1d962ad71b3f3f22e1c")) {
            smsRecordRepository.select();
        }else {
            System.out.println("值为空");
        }
    }
}
