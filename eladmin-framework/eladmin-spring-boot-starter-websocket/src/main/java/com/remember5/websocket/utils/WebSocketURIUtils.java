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
package com.remember5.websocket.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2023/12/13 14:24
 */
public class WebSocketURIUtils {

    /**
     * 获取uri中的参数
     *
     * @param uri uri
     * @return 参数map
     */
    public static Map<String, String> getParams(String uri) {
        // /ws?name=123  /?name=123
        if (null == uri || uri.isEmpty()) {
            return Collections.emptyMap();
        }
        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        if (uri.contains("?")) {
            uri = uri.substring(uri.indexOf("?"));
        }

        uri = uri.replace("/", "").replace("?", "");
        HashMap<String, String> result = new HashMap<>();

        if (uri.contains("&")) {
            final String[] split = uri.split("&");
            for (String s : split) {
                result.put(s.split("=")[0], s.split("=")[1]);
            }
        } else if (uri.contains("=")) {
            result.put(uri.split("=")[0], uri.split("=")[1]);
        }

        return result;

    }
}
