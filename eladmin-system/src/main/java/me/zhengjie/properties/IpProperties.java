package me.zhengjie.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangjiahao
 * @date 2022/5/16 10:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "ip")
public class IpProperties {

    private Boolean localParsing;

}
