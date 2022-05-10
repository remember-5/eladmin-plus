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

import me.zhengjie.modules.tool.domain.MinioConfig;
import me.zhengjie.modules.tool.service.dto.MinioConfigDto;
import me.zhengjie.modules.tool.service.dto.MinioConfigQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author fly
* @date 2022-05-10
**/
public interface MinioConfigService {

    /**
    * 查询所有数据不分页
    * @return List<MinioConfigDto>
    */
    List<MinioConfigDto> queryAll();

    /**
    * 编辑
    * @param resources /
    */
    void update(MinioConfig resources);

}
