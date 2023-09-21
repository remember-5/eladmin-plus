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
package com.remember5.doc.config;

import cn.hutool.core.util.RandomUtil;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.remember5.doc.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建Swagger配置
 *
 * @author wangjiahao
 * @date 2023/5/6 15:32
 */
@Configuration
@EnableConfigurationProperties(value = {SwaggerProperties.class})
public class SwaggerAutoConfiguration {

    @ConditionalOnBean(OpenApiExtensionResolver.class)
    public static class KnifeAutoConfiguration {


        /**
         * 根据@Tag 上的排序，写入x-order
         *
         * @return the global open api customizer
         */
        @Bean
        public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
            return openApi -> {
                if (openApi.getTags() != null) {
                    openApi.getTags().forEach(tag -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("x-order", RandomUtil.randomInt(0, 100));
                        tag.setExtensions(map);
                    });
                }
                if (openApi.getPaths() != null) {
                    openApi.addExtension("x-test123", "333");
                    openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
                }

            };
        }

        @Bean
        public GroupedOpenApi userApi(SwaggerProperties swaggerProperties) {
            String[] paths = {"/**"};
            String[] packagedToMatch = {swaggerProperties.getBasePackage()};
            return GroupedOpenApi.builder().group("用户模块")
                    .pathsToMatch(paths)
                    .addOperationCustomizer((operation, handlerMethod) -> {
                        return operation.addParametersItem(new HeaderParameter().name("groupCode").example("测试").description("集团code").schema(new StringSchema()._default("BR").name("groupCode").description("集团code")));
                    })
                    .packagesToScan(packagedToMatch).build();
        }

        /**
         * 引入Knife4j提供的扩展类
         */
        @Bean
        public OpenAPI customOpenAPI(SwaggerProperties swaggerProperties) {
            return new OpenAPI()
                    .info(apiInfo(swaggerProperties))
                    .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                    .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                            .name(HttpHeaders.AUTHORIZATION).type(SecurityScheme.Type.HTTP).scheme("bearer")));
        }

        private Info apiInfo(SwaggerProperties swaggerProperties) {
            return new Info()
                    .title(swaggerProperties.getTitle())
                    .version(swaggerProperties.getVersion())

                    .description(swaggerProperties.getDescription())
                    .termsOfService(swaggerProperties.getServiceUrl())
                    .contact(
                            new Contact()
                                    .name(swaggerProperties.getAuthor())
                                    .email(swaggerProperties.getEmail())
                                    .url(swaggerProperties.getBlog())
                    );
        }

    }

}
