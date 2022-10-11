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
package com.remember5.system.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangjiahao
 * @date 2022/10/11 16:23
 */
@Data
@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorProperties {

    public static Boolean ENABLED;

    public static String DATABASE_TYPE;


    public static final String MYSQL = "mysql";
    public static final String POSTGRES = "postgres";


    @Value("${generator.enabled}")
    public void setEnabled(boolean enabled) {
        GeneratorProperties.ENABLED = enabled;
    }

    @Value("${generator.database-type}")
    public void setDatabaseType(String databaseType) {
        GeneratorProperties.DATABASE_TYPE = databaseType;
    }
}
