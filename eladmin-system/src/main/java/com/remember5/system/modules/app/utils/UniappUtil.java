package com.remember5.system.modules.app.utils;


import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.remember5.system.modules.app.domain.AppVersion;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class UniappUtil {
    /**
     * @return void 返回类型
     * @throws
     * @Description: (读取Zip信息 ， 获得zip中所有的目录文件信息)
     * @param设定文件
     */
    public static void zipFileRead(MultipartFile multipartFile, AppVersion appVersion) {
        try {
            //MultipartFile fileItem = UrlTurnMultipartFileUtil.createFileItem(url, "");

            // 获得zip信息
            File file = new File("./tmp1");
            FileUtil.writeFromStream(multipartFile.getInputStream(), file);
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry("manifest.json");
            if (entry != null) {
                InputStream inputStream = zipFile.getInputStream(entry);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s = bufferedReader.readLine();
                JSONObject jsonObject = JSONObject.parseObject(s);
                JSONObject parse = jsonObject.getJSONObject("version");
                appVersion.setVersionName(parse.getString("name"));
                appVersion.setBuildCode(parse.get("code").toString());
                appVersion.setContent(jsonObject.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MultipartFile zipFile(MultipartFile multipartFile) {
        // 获得zip信息
        File file = new File("./tmp");
        try {
            //转换到文件 zipfile只能从file读
            FileUtil.writeFromStream(multipartFile.getInputStream(), file);
            ZipFile zipFile = new ZipFile(file);
            FileOutputStream fileOutputStream = new FileOutputStream("./tmp1.wgt");
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
                ZipEntry entryIn = (ZipEntry) e.nextElement();
                if (entryIn == null) {
                    continue;
                }
                byte[] bytes = new byte[10240];
                int leng = 0;
                if ("manifest.json".equals(entryIn.getName())) {
                    InputStream inputStream = zipFile.getInputStream(entryIn);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String s = bufferedReader.readLine();

                    inputStreamReader.close();
                    inputStream.close();
                    bufferedReader.close();

                    JSONObject parse = JSONObject.parseObject(s);
                    entryIn = new ZipEntry("manifest.json");


                    JSONObject version = parse.getJSONObject("version");
                    String name = version.getString("name");
                    Integer code = version.getInteger("code");


                    code += 1;
                    version.remove("code");
                    version.put("code", code);


                    String s1 = parse.toJSONString();
                    zipOutputStream.putNextEntry(entryIn);
                    zipOutputStream.write(s1.getBytes(StandardCharsets.UTF_8));
                } else {
                    InputStream inputStream = zipFile.getInputStream(entryIn);
                    zipOutputStream.putNextEntry(entryIn);
                    while ((leng = inputStream.read(bytes)) > 0) {
                        zipOutputStream.write(bytes, 0, leng);
                    }
                }
                zipOutputStream.closeEntry();
            }
            zipFile.close();
            zipOutputStream.finish();
            zipOutputStream.close();
            FileInputStream fileInputStream = new FileInputStream("./tmp1.wgt");
            return new MockMultipartFile("tmp1.wgt", fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
