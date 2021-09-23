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
package me.zhengjie.modules.smsServer.service;

import me.zhengjie.modules.smsServer.domain.SmsaRecord;
import me.zhengjie.modules.smsServer.service.dto.SmsRecordaDto;
import me.zhengjie.modules.smsServer.service.dto.SmsRecordaQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @website https://el-admin.vip
 * @description 服务接口
 * @date 2021-04-19
 **/
public interface SmsRecordaService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(SmsRecordaQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<SmsRecordaDto>
     */
    List<SmsRecordaDto> queryAll(SmsRecordaQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return SmsRecordaDto
     */
    SmsRecordaDto findById(Integer id);

    /**
     * 创建
     *
     * @param resources /
     * @return SmsRecordaDto
     */
    SmsRecordaDto create(SmsaRecord resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SmsaRecord resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Integer[] ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SmsRecordaDto> all, HttpServletResponse response) throws IOException;


    /**
     * 日统计查询
     */
    Object getrecordday();

    /**
     * 周统计查询
     */
    Object getrecordweek();

    /**
     * 月统计查询
     */
    Object getrecordmonth();

    /**
     * 时间查询
     */
    List<Map<String, String>> getcustomTime(String start, String end);
}
