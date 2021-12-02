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
package me.zhengjie.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * api页面 /doc.html
 *
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Configuration
@EnableSwagger2WebMvc
@RequiredArgsConstructor
public class Knife4jConfig {

    /**
     * 引入Knife4j提供的扩展类
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;


    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        //TODO 这些变量都要提到配置文件中
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# swagger-bootstrap-ui-demo RESTful APIs")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .contact(new Contact("wangjiahao","https://blog.remember5.top","1332661444@qq.com"))
                        .version("1.0")
                        .build())
                        //分组名称
                        .groupName("2.X版本")
                        .select()
                        //这里指定Controller扫描包路径
                        .apis(RequestHandlerSelectors.basePackage("me.zhengjie"))
                        .paths(PathSelectors.any())
                        .build()//赋予插件体系
                        .extensions(openApiExtensionResolver.buildExtensions("2.X版本"));
    }

}
