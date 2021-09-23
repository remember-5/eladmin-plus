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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.keyword.domain.Keywordfiltering;
import me.zhengjie.modules.keyword.domain.KeywordfilteringContent;
import me.zhengjie.modules.keyword.service.KeywordfilteringContentService;
import me.zhengjie.modules.keyword.service.KeywordfilteringService;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringContentDto;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringContentQueryCriteria;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringDto;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringQueryCriteria;
import me.zhengjie.modules.keyword.service.utils.KeywordListenr;
import me.zhengjie.modules.minio.utils.UrlTurnMultipartFileUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @website https://el-admin.vip
* @author tianhh
* @date 2021-04-21
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "关键词过滤管理")
@RequestMapping("/api/keywordfiltering")
public class KeywordfilteringController {

    private final KeywordfilteringService keywordfilteringService;
    private final KeywordfilteringContentService keywordfilteringContentService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('keywordfiltering:list')")
    public void download(HttpServletResponse response, KeywordfilteringQueryCriteria criteria) throws IOException {
        keywordfilteringService.download(keywordfilteringService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询关键词过滤")
    @ApiOperation("查询关键词过滤")
    @PreAuthorize("@el.check('keywordfiltering:list')")
    public ResponseEntity<Object> query(KeywordfilteringQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(keywordfilteringService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增关键词过滤")
    @ApiOperation("新增关键词过滤")
    @PreAuthorize("@el.check('keywordfiltering:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Keywordfiltering resources) throws IOException {
        KeywordfilteringDto keywordfilteringDto = keywordfilteringService.create(resources);
        addItem(resources.getFileUrl(),keywordfilteringDto.getId());
        return new ResponseEntity<>(keywordfilteringDto,HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改关键词过滤")
    @ApiOperation("修改关键词过滤")
    @PreAuthorize("@el.check('keywordfiltering:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Keywordfiltering resources) throws IOException {
        if (resources.getUpdateType()!=null){
            if ("1".equals(resources.getUpdateType())) //全量
            {
                KeywordfilteringContentQueryCriteria keywordfilteringContentQueryCriteria = new KeywordfilteringContentQueryCriteria();
                keywordfilteringContentQueryCriteria.setKeywordfilteringId(resources.getId());
                List<KeywordfilteringContentDto> keywordfilteringContentDtos = keywordfilteringContentService.queryAll(keywordfilteringContentQueryCriteria);
                List<Long> longList=new ArrayList<>();
                keywordfilteringContentDtos.forEach(item->{
                    longList.add(item.getId());
                });
                keywordfilteringContentService.deleteAll(longList.toArray(new Long[0]));
            }
            addItem(resources.getFileUrl(), resources.getId());
        }
        keywordfilteringService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private  void addItem(String fileUrl,Long blacklistId) throws IOException {
        MultipartFile multipartFile= UrlTurnMultipartFileUtil.createFileItem(fileUrl,blacklistId.toString());
        ExcelReader excelReader = null;
        excelReader= EasyExcel.read(multipartFile.getInputStream(),
                KeywordfilteringContent.class,
                new KeywordListenr(keywordfilteringContentService,blacklistId,fileUrl)).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        multipartFile.getInputStream().close();

    }
    @Log("删除关键词过滤")
    @ApiOperation("删除关键词过滤")
    @PreAuthorize("@el.check('keywordfiltering:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        keywordfilteringService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
