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
package me.zhengjie.modules.tool.service;

import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementDto;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description 服务接口
 * @date 2021-04-12
 **/
public interface ResourcesManagementService {

    /**
     * 导入文件
     * @param file /
     * @throws IOException IOException
     */
    void importData(MultipartFile file) throws IOException;

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(ResourcesManagementQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<ResourcesManagementDto>
     */
    List<ResourcesManagementDto> queryAll(ResourcesManagementQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return ResourcesManagementDto
     */
    ResourcesManagementDto findById(Long id);

    /**
     * 查询状态为启用的配置
     *
     * @return /
     */
    ResourcesManagement findByEnabled();

    /**
     * 创建
     *
     * @param resources /
     * @return ResourcesManagementDto
     */
    ResourcesManagementDto create(ResourcesManagement resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(ResourcesManagement resources);

    /**
     * 修改状态
     *
     * @param resources /
     */
    void editEnabled(ResourcesManagement resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 获取启用的minio配置
     *
     * @return /
     */
    ResourcesManagement getMinioConfig();

    /**
     * 根据资源id修改配置状态为禁用
     *
     * @param id 资源id
     */
    void updateById(Long id);

    /**
     * 注册Bean
     *
     * @param beanName            bean名字
     * @param resourcesManagement 参数
     * @return /
     */
    String registerBean(String beanName, ResourcesManagement resourcesManagement);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<ResourcesManagementDto> all, HttpServletResponse response) throws IOException;
}
