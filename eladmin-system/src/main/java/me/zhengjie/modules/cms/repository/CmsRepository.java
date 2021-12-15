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
package me.zhengjie.modules.cms.repository;

import me.zhengjie.modules.cms.domain.Cms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
* @website https://el-admin.vip
* @author zhangenrong
* @date 2021-03-01
**/
@Component
public interface CmsRepository extends JpaRepository<Cms, Long>, JpaSpecificationExecutor<Cms> {

    /**
     * 逻辑删除
     * @param id /
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update Cms set isDeleted=1 where id=?1 ")
    void updateById(Long id);
}
