package me.zhengjie.modules.security.config.bean;

import lombok.Data;

import java.util.List;

/**
 * @author wangjiahao
 * @date 2021/11/8
 */
@Data
public class PermitProperties {

    private List<String> defaultsUrl;
    private List<String> getUrl;
    private List<String> postUrl;
    private List<String> putUrl;
    private List<String> deleteUrl;




}
