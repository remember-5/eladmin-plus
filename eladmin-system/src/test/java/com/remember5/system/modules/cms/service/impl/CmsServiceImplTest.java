package com.remember5.system.modules.cms.service.impl;

import com.remember5.system.SystemApplicationTests;
import com.remember5.system.modules.cms.domain.Cms;
import com.remember5.system.modules.cms.repository.CmsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class CmsServiceImplTest extends SystemApplicationTests {
    @Autowired
    CmsRepository cmsRepository;
    @Test
    void contextLoads() {
        final Cms insertEntity = cmsRepository.save(new Cms().setTitle("insertEntity").setIsDeleted(false));
        log.info("Inserted entity: {}", insertEntity);
        final Cms findEntity = cmsRepository.findById(insertEntity.getId()).orElseGet(Cms::new);
        log.info("Found entity: {}", findEntity);
        findEntity.setTitle("updated");
        final Cms updateEntity = cmsRepository.save(findEntity);
        log.info("Updated entity: {}", updateEntity);
        cmsRepository.deleteById(updateEntity.getId());
        log.debug("测试成功");
    }

}
