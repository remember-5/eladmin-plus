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
package me.zhengjie.modules.tool.service.impl;

import cn.hutool.core.util.ObjectUtil;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.tool.repository.ResourcesManagementRepository;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementDto;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementQueryCriteria;
import me.zhengjie.modules.tool.service.mapstruct.ResourcesManagementMapper;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static me.zhengjie.modules.minio.config.MinIOCode.*;
import static me.zhengjie.utils.SpringContextHolder.getApplicationContext;
import static me.zhengjie.utils.SpringContextHolder.getBean;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-04-12
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ResourcesManagementServiceImpl implements ResourcesManagementService {

    private final ResourcesManagementRepository resourcesManagementRepository;
    private final ResourcesManagementMapper resourcesManagementMapper;

    @Override
    public Map<String, Object> queryAll(ResourcesManagementQueryCriteria criteria, Pageable pageable) {
        Page<ResourcesManagement> page = resourcesManagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(resourcesManagementMapper::toDto));
    }

    @Override
    public List<ResourcesManagementDto> queryAll(ResourcesManagementQueryCriteria criteria) {
        return resourcesManagementMapper.toDto(resourcesManagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public ResourcesManagementDto findById(Long id) {
        ResourcesManagement resourcesManagement = resourcesManagementRepository.findById(id).orElseGet(ResourcesManagement::new);
        ValidationUtil.isNull(resourcesManagement.getId(), "ResourcesManagement", "id", id);
        return resourcesManagementMapper.toDto(resourcesManagement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourcesManagementDto create(ResourcesManagement resources) {
        // 初始化状态 默认为0 禁用状态
        resources.setEnabled(false);
        return resourcesManagementMapper.toDto(resourcesManagementRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ResourcesManagement resources) {
        ResourcesManagement resourcesManagement = resourcesManagementRepository.findById(resources.getId()).orElseGet(ResourcesManagement::new);
        ValidationUtil.isNull(resourcesManagement.getId(), "ResourcesManagement", "id", resources.getId());
        resourcesManagement.copy(resources);
        resourcesManagementRepository.save(resourcesManagement);
        // 修改数据后删除Bean重新生成
        removeBeanDefinition(MINIOCLIENT);
        registerBean(MINIOCLIENT, resourcesManagement);
    }

    @Override
    public ResourcesManagement findByEnabled() {
        return resourcesManagementRepository.findByEnabled();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editEnabled(ResourcesManagement resources) {
        // 获取当前资源状态，0为禁用 1为启用
        Boolean enabled = resources.getEnabled();
        if (enabled) {
            // 修改状态为启用
            resources.setEnabled(true);
            // 检查其他启用状态的资源
            ResourcesManagement resourcesManagement = resourcesManagementRepository.findByEnabled();
            if (ObjectUtil.isNotNull(resourcesManagement)) {
                // 禁用其他启用状态的资源
                resourcesManagementRepository.updateById(resourcesManagement.getId());
                // 销毁Bean
                switch (resources.getType()) {
                    // 类型为 minio
                    case 1:
                        removeBeanDefinition(MINIOCLIENT);
                        break;
                    // 类型为七牛云
                    case 2:
                        break;
                    // 类型为阿里云
                    case 3:
                        break;
                    default:
                        // 类型错误
                        throw new RuntimeException("资源类型错误");
                }
            }
            // 注入Bean
            switch (resources.getType()) {
                // 类型为 minio
                case 1:
                    registerBean(MINIOCLIENT, resources);
                    break;
                // 类型为七牛云
                case 2:
                    break;
                // 类型为阿里云
                case 3:
                    break;
                default:
                    // 类型错误
                    throw new RuntimeException("资源类型错误");
            }
        } else {
            // 修改状态为禁用
            resources.setEnabled(false);
            // 销毁Bean
            switch (resources.getType()) {
                // 类型为 minio
                case 1:
                    removeBeanDefinition(MINIOCLIENT);
                    break;
                // 类型为七牛云
                case 2:
                    break;
                // 类型为阿里云
                case 3:
                    break;
                default:
                    // 类型错误
                    throw new RuntimeException("资源类型错误");
            }
        }
        ResourcesManagement resourcesManagement = resourcesManagementRepository.findById(resources.getId()).orElseGet(ResourcesManagement::new);
        ValidationUtil.isNull(resourcesManagement.getId(), "ResourcesManagement", "id", resources.getId());
        resourcesManagement.copy(resources);
        resourcesManagementRepository.save(resourcesManagement);
    }

    /**
     * 注册Bean
     *
     * @return /
     */
    @Override
    public String registerBean(String beanName, ResourcesManagement resourcesManagement) {
        // 获取context
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) getApplicationContext();
        // 获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        // 创建bean信息
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MinioClient.class);

        // 赋值构造参数
        beanDefinitionBuilder.addConstructorArgValue(resourcesManagement.getUrl());
        beanDefinitionBuilder.addConstructorArgValue(resourcesManagement.getPort());
        beanDefinitionBuilder.addConstructorArgValue(resourcesManagement.getAccesskey());
        beanDefinitionBuilder.addConstructorArgValue(resourcesManagement.getSecretkey());
        // 赋值成员变量参数
        //beanDefinitionBuilder.addPropertyValue("bucket", resourcesManagement.getBucket());

        // 判断Bean是否已经注册
        Object beanObject = getBean(beanName);
        if (ObjectUtil.isNotNull(beanObject)) {
            log.info("Bean {} 已注册", beanName);
            //System.out.println(String.format("Bean %s 已注册", beanName));
        } else {
            // 动态注册bean.
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            // 获取动态注册的bean.
            beanObject = getBean(beanName);
            if (ObjectUtil.isNotNull(beanObject)) {
                log.info("Bean {} 注册成功", beanName);
                //System.out.println(String.format("Bean %s 注册成功", beanName));
                return beanObject.toString();
            } else {
                return REGISTER_ERROR;
            }
        }
        return REGISTER_SUCCESS;
    }

    /**
     * 移除Bean
     *
     * @return /
     */
    public String removeBeanDefinition(String beanName) {
        Object beanObject = getBean(beanName);
        if (ObjectUtil.isNull(beanObject)) {
            log.error("Bean {} 不存在", beanName);
            return REMOVE_ERROR;
        }
        //获取context.
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) getApplicationContext();
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        defaultListableBeanFactory.removeBeanDefinition(beanName);
        log.info("Bean {} 已移除", beanName);
        return REMOVE_SUCCESS;
    }

    @Override
    public void updateById(Long id) {
        resourcesManagementRepository.updateById(id);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            // 如果有启用状态的 则销毁Bean
            ResourcesManagementDto resDto = findById(id);
            if (resDto.getEnabled()) {
                removeBeanDefinition(MINIOCLIENT);
            }
            resourcesManagementRepository.deleteById(id);
        }
    }

    /**
     * 获取minio已启动的配置
     *
     * @return /
     */
    @Override
    public ResourcesManagement getMinioConfig() {
        ResourcesManagement byMinioEnabled = resourcesManagementRepository.findByMinioEnabled();
        if (ObjectUtil.isNull(byMinioEnabled)) {
            throw new RuntimeException("minio服务暂未启动");
        }
        return byMinioEnabled;
    }

    @Override
    public void download(List<ResourcesManagementDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ResourcesManagementDto resourcesManagement : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("分类", resourcesManagement.getType());
            map.put("资源编号", resourcesManagement.getNum());
            map.put("资源地址", resourcesManagement.getUrl());
            map.put("端口号", resourcesManagement.getPort());
            map.put("空间名", resourcesManagement.getBucket());
            map.put("accessKey", resourcesManagement.getAccesskey());
            map.put("secretKey", resourcesManagement.getSecretkey());
            map.put("启用状态(1启用/0禁用)", resourcesManagement.getEnabled());
            map.put("备注", resourcesManagement.getRemarks());
            map.put("创建时间", resourcesManagement.getCreateDate());
            map.put("修改时间", resourcesManagement.getUpdateDate());
            map.put("1 表示删除，0 表示未删除", resourcesManagement.getIsDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
