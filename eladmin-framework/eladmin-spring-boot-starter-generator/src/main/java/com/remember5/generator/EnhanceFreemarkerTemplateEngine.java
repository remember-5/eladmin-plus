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
package com.remember5.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器支持自定义[DTO\VO等]模版
 *
 * @author wangjiahao
 * @date 2024/3/6 14:55
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    @Override
    protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo, Map<String, Object> objectMap) {
        final String entityName = tableInfo.getEntityName();
        final String lowerEntityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        objectMap.put("lowerEntityName", lowerEntityName);
        customFiles.forEach(customFile -> {
            String fileName = null;
            final String packageName = customFile.getPackageName();
            if ("vo".equals(packageName) || "dto".equals(packageName)) {
                fileName = String.format(this.getPathInfo(OutputFile.entity) + File.separator + entityName + "%s", customFile.getFileName());
            } else if ("mapstruct".equals(packageName)) {
                objectMap.put("mapstructPackages", this.getConfigBuilder().getPackageConfig().getParent() + ".mapstruct");
                fileName = String.format(this.getPathInfo(OutputFile.parent) + File.separator + "mapstruct" + File.separator + entityName + "%s", customFile.getFileName());
            }
            this.outputFile(new File(fileName), objectMap, customFile.getTemplatePath(), false);
        });
    }
}
