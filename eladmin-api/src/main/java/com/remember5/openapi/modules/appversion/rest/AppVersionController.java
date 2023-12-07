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
package com.remember5.openapi.modules.appversion.rest;

import com.remember5.biz.appversion.domain.AppVersion;
import com.remember5.biz.appversion.service.AppVersionService;
import com.remember5.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author fly
* @date 2022-12-07
**/
@RestController
@RequiredArgsConstructor
@Tag(name = "app版本管理")
@RequestMapping("/api/appVersion")
public class AppVersionController {

    private final AppVersionService appVersionService;

    @GetMapping()
    @Operation(summary = "更新app")
    public R<AppVersion> update(){
        return null;
    }

}
