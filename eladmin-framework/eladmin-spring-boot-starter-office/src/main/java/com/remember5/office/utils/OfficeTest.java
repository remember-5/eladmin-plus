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
package com.remember5.office.utils;

import cn.hutool.core.io.IoUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2023/9/20 18:59
 */
public class OfficeTest {

    public void generateImage() throws IOException {
        String savePath = "/Users/wangjiahao/Downloads/";
        final long fileUuid = System.currentTimeMillis() + (int) (Math.random() * 90000 + 10000);
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("statisticalTime", new Date().toString());
        dataMap.put("imageUrl", "http://42.193.105.146:9000/nt1/mr.jpeg");
        dataMap.put("selfSummary", "这是我的自我总结文本");

        final String htmlStr = FreemarkerUtils.freemarkerRender(dataMap, "templates/xxx.tpl");
        String htmlFileName = savePath + "input.html";
        try (PrintWriter writer = new PrintWriter(htmlFileName, "UTF-8")) {
            writer.println(htmlStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        // 获取资源文件的 Resource 对象
        Resource resource = new ClassPathResource("static/fonts/xxx.ttf");
        byte[] fileBytes = IoUtil.readBytes(resource.getInputStream());

        String pdfFileName = savePath + fileUuid + ".pdf";
        FreemarkerUtils.html2pdf(htmlStr, fileBytes, pdfFileName);
        PdfBoxUtils.pdfBox2Png(pdfFileName, savePath, fileUuid);
    }



}
