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
package com.remember5.openapi.modules.app.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.remember5.openapi.modules.app.service.AppVersionService;
import com.remember5.openapi.modules.app.service.dto.AppVersionDto;
import com.remember5.openapi.modules.app.service.dto.AppVersionQueryCriteria;
import me.zhengjie.annotation.Log;
import me.zhengjie.result.R;
import me.zhengjie.result.REnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @date 2021-05-03
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "app版本管理管理")
@RequestMapping("/api/appVersion")
public class AppVersionController {

    private final AppVersionService appVersionService;


    public static String getHeader(String name) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request != null ? request.getHeader(name) : null;
    }

    @GetMapping("getwgtversion")
    @Log("查询app wgt最新版本")
    @ApiOperation("查询app wgt最新版本")
    public R query() {
        AppVersionQueryCriteria criteria = new AppVersionQueryCriteria();
        criteria.setVersionName(getHeader("version"));
        criteria.setBuildCode(getHeader("versionCode"));
        List<AppVersionDto> appVersionDtos = appVersionService.queryAll(criteria);
        if (appVersionDtos.size() > 0) {
            if (appVersionDtos.get(0).getIsNew()) {
                return R.success();
            } else {
                AppVersionQueryCriteria appVersionQueryCriteria = new AppVersionQueryCriteria();
                appVersionQueryCriteria.setVersionName(criteria.getVersionName());
                appVersionQueryCriteria.setResType(2);
                appVersionQueryCriteria.setIsNew(true);
                return R.success(appVersionService.queryAll(appVersionQueryCriteria));
            }
        } else {
            return R.fail(REnum.A0001);
        }
    }


}