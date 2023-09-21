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
package com.remember5.system.modules.generator.rest;

import cn.hutool.system.SystemUtil;
import com.remember5.system.modules.generator.domain.GenConfig;
import com.remember5.system.modules.generator.domain.Node;
import com.remember5.system.modules.generator.service.GenConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genConfig")
@Tag(name = "系统：代码生成器配置管理")
public class GenConfigController {

    private final GenConfigService genConfigService;

    @Operation(summary = "查询")
    @GetMapping(value = "/{tableName}")
    public ResponseEntity<Object> queryGenConfig(@PathVariable String tableName) {
        return new ResponseEntity<>(genConfigService.find(tableName), HttpStatus.OK);
    }

    @Operation(summary = "修改")
    @PutMapping
    public ResponseEntity<Object> updateGenConfig(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity<>(genConfigService.update(genConfig.getTableName(), genConfig), HttpStatus.OK);
    }

    @Operation(summary = "查看模块名称")
    @GetMapping(value = "/modules")
    public ResponseEntity<Object> findModules() {
        // 获取目录下的子目录
        File[] subDirs = new File(System.getProperty("user.dir")).listFiles(File::isDirectory);
        ArrayList<String> modulesNames = new ArrayList<>();
        for (File subDir : subDirs) {
            modulesNames.add(subDir.getName());
        }
        return new ResponseEntity<>(modulesNames, HttpStatus.OK);
    }

    @Operation(summary = "查看包名称")
    @GetMapping(value = "/package")
    public ResponseEntity<Object> findPackage(String moduleName) {
        //获取当前项目目录
        String projectPath = System.getProperty("user.dir");
        boolean isWindows = SystemUtil.getOsInfo().isWindows();
        final String path = projectPath + File.separator + moduleName;
        List<String> dirList = new ArrayList<>();
        Node root = new Node("", "");
        Node current = root;
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths
                    .filter(Files::isDirectory)
                    .forEach(e -> {
                        if (!e.toString().equals(path)) {
                            dirList.add(e.toString().replace(path + File.separator, ""));

                        }
                    });
            for (String item : dirList) {
                String[] parts = isWindows ? item.split("\\\\") : item.split(File.separator);
                for (String part : parts) {
                    boolean foundChild = false;
                    for (Node child : current.getChildren()) {
                        if (child.getLabel().equals(part)) {
                            current = child;
                            foundChild = true;
                            break;
                        }
                    }
                    if (!foundChild) {
                        Node newNode = isWindows ? new Node(part, item.replaceAll("\\\\", ".")) : new Node(part, item.replaceAll(File.separator, "."));
                        current.getChildren().add(newNode);
                        current = newNode;
                    }
                }
                current = root;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!isWindows){
            root.reverseList();
        }
        return new ResponseEntity<>(root , HttpStatus.OK);
    }

}
