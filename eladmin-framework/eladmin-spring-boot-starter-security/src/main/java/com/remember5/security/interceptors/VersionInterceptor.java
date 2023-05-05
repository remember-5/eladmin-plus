/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.security.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 版本拦截，过期请求不在进入
 *
 * @author wangjiahao
 * @date 2023/1/6 16:05
 */
//@Slf4j
public class VersionInterceptor implements HandlerInterceptor {

//    @Resource
//    private RedisUtils redisUtils;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("preHandle");
//        final String platform = request.getHeader("platform");
//        if (platform == null) {
//            throw new BaseException(R.fail(REnum.A0300));
//        } else if (platform == "ios" || platform == "android") {
//            final String appid = request.getHeader("appid");
//            final String version = request.getHeader("version");
//            final String build = request.getHeader("build");
//
//            final Object hget = redisUtils.hget(CacheKeyConstant.APP_VERSION_PREFIX + appid, build);
//            final JSONObject jsonObject = JSON.parseObject(hget.toString());
//            final Integer archived = jsonObject.getInteger("archived");
//
//            if (1 == archived) {
//                throw new BaseException(R.fail(REnum.A0300));
//            }
//        } else if (platform == "h5") {
//            //继续流程
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("postHandle");
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("afterCompletion");
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
}
