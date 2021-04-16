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
package me.zhengjie.modules.tool.repository;

import me.zhengjie.modules.tool.domain.ResourcesManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
* @website https://el-admin.vip
* @author fly
* @date 2021-04-12
**/
public interface ResourcesManagementRepository extends JpaRepository<ResourcesManagement, Long>, JpaSpecificationExecutor<ResourcesManagement> {

    /**
     * 禁用状态
     * @param id 资源配置id
     */
    @Modifying
    @Transactional
    @Query(value = "update ResourcesManagement set enabled=0 where id=?1 ")
    void updateById(Long id);

    /**
     * 查询状态为启用的配置信息
     * @return /
     */
    @Query(value = "select a.*  from t_resources_management a where a.enabled = 1 ", nativeQuery = true)
    ResourcesManagement findByEnabled();

    /**
     * 查询状态为启用的minio的信息
     * @return /
     */
    @Query(value = "select a.*  from t_resources_management a where a.enabled = 1 and a.type = 1 ", nativeQuery = true)
    ResourcesManagement findByMinioEnabled();
}
