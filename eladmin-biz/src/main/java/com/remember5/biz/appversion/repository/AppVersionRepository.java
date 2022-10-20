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
package com.remember5.biz.appversion.repository;

import com.remember5.biz.appversion.domain.AppVersion;
import com.remember5.biz.appversion.service.dto.AppVersionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author fly
 * @date 2022-12-07
 **/
public interface AppVersionRepository extends JpaRepository<AppVersion, Integer>, JpaSpecificationExecutor<AppVersion> {

    AppVersionDto findByAppIdAndVersionAndBuildAndArchivedAndPublishedAndIsDeleted(String appid, String version, Long build, int archived, int published, int isDeleted);

    /**
     * 逻辑删除
     *
     * @param id /
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update AppVersion set isDeleted=1 where id=?1 ")
    void updateIsDeletedById(Integer id);

    @Transactional
    @Modifying
    @Query("update AppVersion a set a.isDeleted = 1")
    int logicallyDeleteAll();

    /**
     * 设置所有版本不是最新
     */
    @Transactional
    @Modifying
    @Query("update AppVersion a set a.isLatestRelease = 0")
    void updateIsLatestRelease();

    /**
     * 判断版本是否存在
     *
     * @param appId   appid
     * @param version /
     * @param build   /
     * @return /
     */

    @Query("select (count(a) > 0) from AppVersion a where a.appId = ?1 and a.version = ?2 and a.build = ?3 and a.isDeleted = 0")
    boolean existsByAppIdAndVersionAndBuild(String appId, String version, Long build);

    /**
     * 查询版本
     *
     * @param appId   /
     * @param version /
     * @param build   /
     * @return /
     */
    AppVersion findByAppIdAndVersionAndBuild(String appId, String version, Long build);

    /**
     * 查询最后一个最新的版本
     * @param appId
     * @return
     */
    @Query("select a from AppVersion a " +
            "where a.appId = ?1 and a.published = 1 and a.archived = 0 and a.isDeleted = 0 and a.isLatestRelease = 1 " +
            "order by a.build DESC")
    AppVersion findLatestVersion(String appId);


}
