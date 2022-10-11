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
package com.remember5.system.modules.generator.utils;

import com.remember5.system.properties.GeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * sql字段转java
 *
 * @author Zheng Jie
 * @date 2019-01-03
 */
@Slf4j
public class ColUtil {


    /**
     * 转换mysql数据类型为java数据类型
     *
     * @param type 数据库字段类型
     * @return String
     */
    static String cloToJava(String type) {
        Configuration config = getConfig();
        assert config != null;
        return config.getString(type, "unknowType");
    }

    /**
     * 获取配置信息
     */
    public static PropertiesConfiguration getConfig() {
        try {
            switch (GeneratorProperties.DATABASE_TYPE) {
                case GeneratorProperties.POSTGRES:
                    return new PropertiesConfiguration("generator.postgres.properties");
                case GeneratorProperties.MYSQL:
                    return new PropertiesConfiguration("generator.mysql.properties");
                default:
                    break;
            }

        } catch (ConfigurationException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
