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
package me.zhengjie.modules.smsServer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.smsServer.repository.SmsRecordaRepository;
import me.zhengjie.modules.smsServer.service.SmsRecordaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @website https://el-admin.vip
* @author wh
* @date 2021-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "短信信息管理")
@RequestMapping("/api/smsRecord")
public class SmsRecordaController {

    private final SmsRecordaService smsRecordaService;
    private final SmsRecordaRepository smsRecordaRepository;
//
//    @Log("导出数据")
//    @ApiOperation("导出数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('smsRecord:list')")
//    public void download(HttpServletResponse response, SmsRecordaQueryCriteria criteria) throws IOException {
//        smsRecordaService.download(smsRecordaService.queryAll(criteria), response);
//    }
//
//    @GetMapping
//    @Log("查询短信信息")
//    @ApiOperation("查询短信信息")
//    @PreAuthorize("@el.check('smsRecord:list')")
//    public ResponseEntity<Object> query(SmsRecordaQueryCriteria criteria, Pageable pageable){
//        return new ResponseEntity<>(smsRecordaService.queryAll(criteria,pageable),HttpStatus.OK);
//    }
//
//    @PostMapping
//    @Log("新增短信信息")
//    @ApiOperation("新增短信信息")
//    @PreAuthorize("@el.check('smsRecord:add')")
//    public ResponseEntity<Object> create(@Validated @RequestBody SmsaRecord resources){
//        return new ResponseEntity<>(smsRecordaService.create(resources),HttpStatus.CREATED);
//    }
//
//    @PutMapping
//    @Log("修改短信信息")
//    @ApiOperation("修改短信信息")
//    @PreAuthorize("@el.check('smsRecord:edit')")
//    public ResponseEntity<Object> update(@Validated @RequestBody SmsaRecord resources){
//        smsRecordaService.update(resources);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @Log("删除短信信息")
//    @ApiOperation("删除短信信息")
//    @PreAuthorize("@el.check('smsRecord:del')")
//    @DeleteMapping
//    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
//        smsRecordaService.deleteAll(ids);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @ApiOperation("查询近7日数据")
    @GetMapping("/getrecordday")
    public ResponseEntity<Object> getrecordday() {
        return new ResponseEntity<>(smsRecordaService.getrecordday(), HttpStatus.OK);
    }
    @ApiOperation("查询近几个周数据")
    @GetMapping("/getrecordweek")
    public ResponseEntity<Object> getrecordweek() {
        return new ResponseEntity<>(smsRecordaService.getrecordweek(), HttpStatus.OK);
    }

    @ApiOperation("查询近几月的数据")
    @GetMapping("/getrecordmonth")
    public ResponseEntity<Object> getrecordmonth() {
        return new ResponseEntity<>(smsRecordaService.getrecordmonth(), HttpStatus.OK);
    }
    @ApiOperation("查询近几月的数据")
    @GetMapping("/getcustomTime")
    public ResponseEntity<Object> getcustomTime(String start,String  end ) {
        return new ResponseEntity<>(smsRecordaService.getcustomTime(start,end), HttpStatus.OK);
    }
}
