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
package me.zhengjie.modules.smsServer.repository;

import me.zhengjie.modules.smsServer.domain.ProjectInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

/**
 * @author wh
 * @website https://el-admin.vip
 * @date 2021-04-21
 **/
public interface ProjectInformationRepository extends JpaRepository<ProjectInformation, Integer>, JpaSpecificationExecutor<ProjectInformation> {

    @Query(value = "select * from project_information where appid=?1 and  secret=?2", nativeQuery = true)
    Map<String, String> selectAppid(String appid, String secret);

    @Query(value = "select entry_name from project_information where appid=?1", nativeQuery = true)
    Map<String, String> selectentryName(String appid);
}
