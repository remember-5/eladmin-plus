package me.zhengjie.modules.tool.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.modules.tool.repository.ResourcesManagementRepository;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static me.zhengjie.modules.minio.config.MinIOCode.MINIOCLIENT;

/**
 * @author fly
 * @description
 * @date 2021/4/16 17:11
 */
@Slf4j
@Component
public class ResourcesManagementConfig implements ApplicationRunner {

    @Autowired
    private ResourcesManagementService resourcesManagementService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 检查是否有状态为启动的配置
        ResourcesManagement resourcesManagement = resourcesManagementService.findByEnabled();
        /*  此处有两个选择
                   1.根据已启动状态的配置注入Bean (需要调整顺序 此时执行找不到BeanFactory
                   2.修改数据库内数据状态为未启动
         */
        if (ObjectUtil.isNotNull(resourcesManagement)) {
            // 禁用 启用状态的资源配置
            resourcesManagementService.updateById(resourcesManagement.getId());
        }
        log.info("资源配置状态已初始化");
    }
}
