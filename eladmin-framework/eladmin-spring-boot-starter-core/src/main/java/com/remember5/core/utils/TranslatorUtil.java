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
package com.remember5.core.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;

/**
 * 翻译工具类
 *
 * @author wangjiahao
 * @date 2023/07/17
 */
public class TranslatorUtil {
    private static final String TRANSLATED_URL = "https://translate.googleapis.com/translate_a/single";

    public static String translate(String word) {
        final String body = HttpRequest.get(TRANSLATED_URL).form("client", "gtx")
                .form("sl", "en")
                .form("tl", "zh-CN")
                .form("dt", "t")
                .form("q", URLEncodeUtil.encode(word))
                .execute().body();

        final JSONArray parse = JSONArray.parse(body);
        return parse.getJSONArray(0).getJSONArray(0).getString(0);
    }
}
