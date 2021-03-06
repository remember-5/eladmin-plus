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
package me.zhengjie.modules.tool.service.impl;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.tool.domain.EmailConfig;
import me.zhengjie.modules.tool.domain.vo.EmailVo;
import me.zhengjie.modules.tool.repository.EmailRepository;
import me.zhengjie.modules.tool.service.EmailService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "email")
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    public static final String KEY = "Passw0rdPassw0rd";
    private SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, KEY.getBytes(StandardCharsets.UTF_8));
    @Override
    @CachePut(key = "'config'")
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig config(EmailConfig emailConfig, EmailConfig old) throws Exception {
        emailConfig.setId(1L);
        if (!emailConfig.getPass().equals(old.getPass())) {
            // ????????????
            emailConfig.setPass(new String(aes.encrypt(emailConfig.getPass())));
        }
        return emailRepository.save(emailConfig);
    }

    @Override
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if (emailConfig.getId() == null) {
            throw new BadRequestException("????????????????????????");
        }
        // ??????
        MailAccount account = new MailAccount();
        // ????????????
        String user = emailConfig.getFromUser().split("@")[0];
        account.setUser(user);
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // ????????????
            account.setPass( new String(aes.decrypt(emailConfig.getPass())));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        // ssl????????????
        account.setSslEnable(true);
        // ??????STARTTLS????????????
        account.setStarttlsEnable(true);
        String content = emailVo.getContent();
        // ??????
        try {
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //??????session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
