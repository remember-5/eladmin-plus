package me.zhengjie.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangjiahao
 * @date 2022/5/16 10:35
 */
@Data
@Component
public class IpProperties {
    public static boolean localParsing;

    @Value("${ip.local-parsing}")
    public void setIpLocal(boolean localParsing) {
        IpProperties.localParsing = localParsing;
    }
}
