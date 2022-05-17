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
package me.zhengjie;

import io.swagger.annotations.Api;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.utils.SpringContextHolder;
import me.zhengjie.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启审计功能 -> @EnableJpaAuditing
 *
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@RestController
@Api(hidden = true)
@SpringBootApplication
@EnableTransactionManagement // 开启事物
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy(exposeProxy = true) // Aop
@ComponentScan(basePackages = {
        "me.zhengjie",
        "me.zhengjie.utils",
        "me.zhengjie.config",
        "cn.hutool.extra.spring",
        "com.remember5.redis.*",
        "com.remember5.captcha.*",
        "com.remember5.minio.*",
})
public class SystemApiApplication {

    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(SystemApiApplication.class);
//        // 监控应用的PID，启动时可指定PID路径：--spring.pid.file=/home/eladmin/app.pid
//        // 或者在 application.yml 添加文件路径，方便 kill，kill `cat /home/eladmin/app.pid`
//        springApplication.addListeners(new ApplicationPidFileWriter());
//        springApplication.run(args);
        ApplicationContext app = SpringApplication.run(SystemApiApplication.class, args);
        SpringContextUtil.setApplicationContext(app);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }
}
