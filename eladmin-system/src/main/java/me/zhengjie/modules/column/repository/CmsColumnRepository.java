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
package me.zhengjie.modules.column.repository;

import me.zhengjie.modules.column.domain.CmsColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @website https://el-admin.vip
* @author fly
* @date 2021-03-02
**/
public interface CmsColumnRepository extends JpaRepository<CmsColumn, Long>, JpaSpecificationExecutor<CmsColumn> {

    /**
     *  根据父id查询子集
     * @param columnId 栏目父id
     * @return 栏目子集
     */
    @Query(value = "select * from t_cms_column  where fid = ? and is_deleted = 0 ",nativeQuery = true)
    List<CmsColumn> queryTreeData(Long columnId);

    /**
     * 逻辑删除
     * @param id /
     */
    @Modifying
    @Transactional
    @Query(value = "update CmsColumn set isDeleted=1 where id=?1 ")
    void updateById(Long id);
}
