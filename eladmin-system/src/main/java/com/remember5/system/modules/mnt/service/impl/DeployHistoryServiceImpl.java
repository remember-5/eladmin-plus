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
package com.remember5.system.modules.mnt.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember5.core.utils.FileUtil;
import com.remember5.core.utils.PageResult;
import com.remember5.core.utils.PageUtil;
import com.remember5.system.modules.mnt.domain.DeployHistory;
import com.remember5.system.modules.mnt.domain.vo.DeployHistoryQueryCriteria;
import com.remember5.system.modules.mnt.mapper.DeployHistoryMapper;
import com.remember5.system.modules.mnt.service.DeployHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Service
@RequiredArgsConstructor
public class DeployHistoryServiceImpl extends ServiceImpl<DeployHistoryMapper, DeployHistory> implements DeployHistoryService {

    private final DeployHistoryMapper deployhistoryMapper;

    @Override
    public PageResult<DeployHistory> queryAll(DeployHistoryQueryCriteria criteria, Page<Object> page) {
        return PageUtil.toPage(deployhistoryMapper.findAll(criteria, page));
    }

    @Override
    public List<DeployHistory> queryAll(DeployHistoryQueryCriteria criteria) {
        return deployhistoryMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DeployHistory resources) {
        resources.setId(IdUtil.simpleUUID());
        resources.setDeployDate(Timestamp.valueOf(LocalDateTimeUtil.now()));
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<DeployHistory> deployHistories, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeployHistory deployHistory : deployHistories) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("部署编号", deployHistory.getDeployId());
            map.put("应用名称", deployHistory.getAppName());
            map.put("部署IP", deployHistory.getIp());
            map.put("部署时间", deployHistory.getDeployDate());
            map.put("部署人员", deployHistory.getDeployUser());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
