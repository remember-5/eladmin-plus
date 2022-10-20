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
package com.remember5.biz.appversion.service.mapstruct;

import com.remember5.core.base.BaseMapper;
import com.remember5.biz.appversion.domain.AppVersion;
import com.remember5.biz.appversion.service.dto.AppVersionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author fly
* @date 2023-04-06
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppVersionMapper extends BaseMapper<AppVersionDto, AppVersion> {

}
