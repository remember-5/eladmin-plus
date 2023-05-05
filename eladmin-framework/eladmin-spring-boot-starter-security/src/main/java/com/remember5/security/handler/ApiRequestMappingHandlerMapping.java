package com.remember5.security.handler;

import com.remember5.security.anotation.ApiVersion;
import com.remember5.security.condition.ApiVersionCondition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * <a href="https://blog.csdn.net/weixin_39255905/article/details/110391515">实现 API 版本控制</a>
 * 使用方法：
 * 在controller1上 RequestMapping("api/{version}/user")
 * 在controller2上 RequestMapping("api/{version}/user") ApiVersion(2)
 * 输入/api/v1/user 会到controller1
 * 输入/api/v2/user 会到controller2
 * 如果在v2中没找到，会在v1里找接口
 *
 * @author wangjiahao
 * @date 2022/4/26 10:21
 */
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private static final String VERSION_FLAG = "{version}";

    private static RequestCondition<ApiVersionCondition> createCondition(Class<?> clazz) {
        RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
        if (classRequestMapping == null) {
            return null;
        }
        StringBuilder mappingUrlBuilder = new StringBuilder();
        if (classRequestMapping.value().length > 0) {
            mappingUrlBuilder.append(classRequestMapping.value()[0]);
        }
        String mappingUrl = mappingUrlBuilder.toString();
        if (!mappingUrl.contains(VERSION_FLAG)) {
            return null;
        }
        ApiVersion apiVersion = clazz.getAnnotation(ApiVersion.class);
        return apiVersion == null ? new ApiVersionCondition(1) : new ApiVersionCondition(apiVersion.value());
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return createCondition(method.getClass());
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return createCondition(handlerType);
    }
}
