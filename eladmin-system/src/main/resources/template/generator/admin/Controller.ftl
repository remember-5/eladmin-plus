/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package ${package}.rest;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.remember5.core.utils.FileUtil;
import com.remember5.security.entity.PageResult;
import com.remember5.system.modules.logging.annotation.Log;
import ${package}.domain.${className};
import ${package}.domain.vo.${className}QueryCriteria;
import ${package}.service.${className}Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author ${author}
* @date ${date}
**/
@RestController
@RequiredArgsConstructor
@Tag(name = "${apiAlias}管理")
@RequestMapping("/api/${changeClassName}")
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;

    @GetMapping
    @Log("查询${apiAlias}")
    @Operation(summary = "查询${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:list')")
    public ResponseEntity<PageResult<${className}>> query${className}(${className}QueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(${changeClassName}Service.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增${apiAlias}")
    @Operation(summary = "新增${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:add')")
    public ResponseEntity<Object> create${className}(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改${apiAlias}")
    @Operation(summary = "修改${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:edit')")
    public ResponseEntity<Object> update${className}(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除${apiAlias}")
    @Operation(summary = "删除${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:del')")
    public ResponseEntity<Object> delete${className}(@RequestBody List<${pkColumnType}> ids) {
        ${changeClassName}Service.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("导入数据模板")
    @Operation(summary = "导入数据模板")
    @GetMapping(value = "/downloadTemplate")
    @PreAuthorize("@el.check('${changeClassName}:importData')")
    public void downloadImport${className}Template(HttpServletResponse response) throws IOException {
        List<Object> rows = CollUtil.newArrayList(new ${className}());
        FileUtil.downloadTemplate(rows,response);
    }

    @Log("导入数据")
    @Operation(summary = "导入数据")
    @PostMapping(value = "/importData")
    @PreAuthorize("@el.check('${changeClassName}:importData')")
    public ResponseEntity<Object> import${className}(@RequestParam("file") MultipartFile file) throws IOException{
        ${changeClassName}Service.importData(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("导出数据")
    @Operation(summary = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('${changeClassName}:list')")
    public void export${className}(HttpServletResponse response, ${className}QueryCriteria criteria) throws IOException {
        ${changeClassName}Service.download(${changeClassName}Service.queryAll(criteria), response);
    }

}
