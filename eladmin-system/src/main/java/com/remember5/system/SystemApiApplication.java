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
package com.remember5.system;

import com.remember5.security.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 开启审计功能 -> @EnableJpaAuditing
 *
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement // 开启事物
@EnableAspectJAutoProxy(exposeProxy = true) // Aop exposeProxy=强制采用cglib代理 proxyTargetClass=暴露cglib代理的目标对象
@ComponentScan(basePackages = {
        "cn.hutool.extra.spring",
        "com.remember5.*",
})
@EntityScan(basePackages = {
        "com.remember5.*",
})
public class SystemApiApplication {

    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(SystemApiApplication.class);
//        // 监控应用的PID，启动时可指定PID路径：--spring.pid.file=/home/eladmin/app.pid
//        // 或者在 application.yml 添加文件路径，方便 kill，kill `cat /home/eladmin/app.pid`
//        springApplication.addListeners(new ApplicationPidFileWriter());
//        springApplication.run(args);
//        ApplicationContext app = SpringApplication.run(SystemApiApplication.class, args);
//        SpringContextUtil.setApplicationContext(app);
        SpringApplication.run(SystemApiApplication.class, args);
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
