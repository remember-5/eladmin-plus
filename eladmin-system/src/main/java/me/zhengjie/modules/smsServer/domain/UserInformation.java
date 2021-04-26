package me.zhengjie.modules.smsServer.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "user.information")
public class UserInformation {
    private String account;
    private String password;
}
