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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2023/6/13 16:08
 */
public class FreemarkerUtils {

    public FreemarkerUtils() {
    }

    private volatile static Configuration configuration;

    static {
        if (configuration == null) {
            synchronized (FreemarkerUtils.class) {
                if (configuration == null) {
                    configuration = new Configuration(Configuration.VERSION_2_3_28);
                }
            }
        }
    }

    /**
     * freemarker 引擎渲染 html
     *
     * @param dataMap    传入 html 模板的 Map 数据
     * @param templateName html 模板文件相对路径(相对于 resources路径,路径 + 文件名)
     *                   eg: "templates/pdf_export_demo.ftl"
     * @return
     */
    public static String freemarkerRender(Map<String, Object> dataMap, String templateName) {
        configuration.setClassForTemplateLoading(FreemarkerUtils.class, "/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try(Writer out = new StringWriter()) {
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);
            Template template = configuration.getTemplate(templateName);
            template.process(dataMap, out);
            out.flush();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用 iText 生成 PDF 文档
     *
     * @param html     html 模板文件字符串
     * @param fontFile 所需字体文件(相对路径+文件名)
     */
    public static byte[] html2pdf(String html, String fontFile) {
        byte[] result = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter pdfWriter = new PdfWriter(outputStream);

            DefaultFontProvider fontProvider = new DefaultFontProvider();
            fontProvider.addSystemFonts();
            fontProvider.addFont(fontFile);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(fontProvider);

            HtmlConverter.convertToPdf(html, pdfWriter, converterProperties);
            result = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param html     html 模板文件字符串
     * @param fontFile 所需字体文件(相对路径+文件名)
     * @param savePath 保存路径
     * @return
     */
    public static void html2pdf(String html, byte[] fontFile, String savePath) throws IOException {
        try (PdfWriter pdfWriter = new PdfWriter(savePath)) {
            DefaultFontProvider fontProvider = new DefaultFontProvider();
            fontProvider.addFont(fontFile);
            fontProvider.addSystemFonts();

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(fontProvider);

            HtmlConverter.convertToPdf(html, pdfWriter, converterProperties);
        }
    }


}
