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

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.remember5.doc.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author wangjiahao
 * @date 2023/5/6 15:32
 */
@Configuration
@EnableConfigurationProperties(value = {SwaggerProperties.class})
public class SwaggerAutoConfiguration {

    @EnableSwagger2WebMvc
    @ConditionalOnBean(OpenApiExtensionResolver.class)
    public static class KnifeAutoConfiguration {
        /**
         * 引入Knife4j提供的扩展类
         */
        @Bean(value = "defaultApi2")
        public Docket defaultApi2(OpenApiExtensionResolver openApiExtensionResolver, SwaggerProperties swaggerProperties) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .enable(swaggerProperties.getEnabled())
                    .apiInfo(apiInfo(swaggerProperties))
                    //分组名称
                    .groupName(swaggerProperties.getGroupName())
                    .select()
                    //这里指定Controller扫描包路径
                    .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                    //赋予插件体系;
                    .paths(PathSelectors.any()).build()
                    .extensions(openApiExtensionResolver.buildExtensions(swaggerProperties.getGroupName()));
        }

        private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
            return new ApiInfoBuilder()
                    .title(swaggerProperties.getTitle())
                    .description(swaggerProperties.getDescription())
                    .termsOfServiceUrl(swaggerProperties.getServiceUrl())
                    .contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getBlog(), swaggerProperties.getEmail()))
                    .version(swaggerProperties.getVersion())
                    .build();
        }

    }

    @EnableSwagger2WebMvc
    @ConditionalOnMissingBean(OpenApiExtensionResolver.class)
    public static class KnifeAutoConfiguration2 {
        /**
         * 引入Knife4j提供的扩展类
         */
        @Bean(value = "defaultApi2")
        public Docket defaultApi2(SwaggerProperties swaggerProperties) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .enable(swaggerProperties.getEnabled())
                    .apiInfo(apiInfo(swaggerProperties))
                    //分组名称
                    .groupName(swaggerProperties.getGroupName())
                    .select()
                    //这里指定Controller扫描包路径
                    .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                    //赋予插件体系;
                    .paths(PathSelectors.any()).build();
        }

        private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
            return new ApiInfoBuilder()
                    .title(swaggerProperties.getTitle())
                    .description(swaggerProperties.getDescription())
                    .termsOfServiceUrl(swaggerProperties.getServiceUrl())
                    .contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getBlog(), swaggerProperties.getEmail()))
                    .version(swaggerProperties.getVersion())
                    .build();
        }

    }
}
