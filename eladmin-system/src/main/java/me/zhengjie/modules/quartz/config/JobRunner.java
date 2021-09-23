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
package me.zhengjie.modules.quartz.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.quartz.domain.QuartzJob;
import me.zhengjie.modules.quartz.repository.QuartzJobRepository;
import me.zhengjie.modules.quartz.utils.QuartzManage;
import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementDto;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementQueryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static me.zhengjie.modules.minio.config.MinIOCode.MINIOCLIENT;
import static me.zhengjie.modules.minio.config.MinIOCode.REGISTER_SUCCESS;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(JobRunner.class);
    private final QuartzJobRepository quartzJobRepository;
    private final QuartzManage quartzManage;
    private final ResourcesManagementService resourcesManagementService;

    /**
     * 项目启动时重新激活启用的定时任务
     *
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("--------------------注入定时任务---------------------");
        List<QuartzJob> quartzJobs = quartzJobRepository.findByIsPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
        log.info("--------------------定时任务注入完成---------------------");

        // 检查是否有状态为启动的配置
        ResourcesManagement resourcesManagement = resourcesManagementService.findByEnabled();
        /*  此处有两个选择
                   1.根据已启动状态的配置注入Bean (此处使用的这个
                   2.修改数据库内数据状态为未启动
         */
        if (ObjectUtil.isNotNull(resourcesManagement)) {
            // 修改数据库内数据状态为未启动
            // resourcesManagementService.updateById(resourcesManagement.getId());
            // 根据已启动状态的配置注入Bean
            resourcesManagementService.registerBean(MINIOCLIENT, resourcesManagement);
        } else {
            // 如果没有状态为启动的配置
            // 查询数据库中全部的资源配置
            List<ResourcesManagementDto> resourcesManagementDtos = resourcesManagementService.queryAll(new ResourcesManagementQueryCriteria());
            if (ObjectUtil.isNotNull(resourcesManagementDtos)) {
                for (ResourcesManagementDto resourcesManagementDto : resourcesManagementDtos) {
                    if (resourcesManagementDto.getType() == 1) {
                        // 类型为minio
                        String s = resourcesManagementService.registerBean(MINIOCLIENT,
                                ResourcesManagement.builder()
                                        .url(resourcesManagementDto.getUrl())
                                        .port(resourcesManagementDto.getPort())
                                        .accesskey(resourcesManagementDto.getAccesskey())
                                        .secretkey(resourcesManagementDto.getSecretkey())
                                        .build());
                        if (REGISTER_SUCCESS.equals(s)) {
                            // 创建成功跳出循环
                            break;
                        }
                    }
                }
            } else {
                log.warn("-----------------资源配置无，请至菜单配置------------------");
            }
        }
        log.info("--------------------资源配置初始化完成---------------------");
    }
}
