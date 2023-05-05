package com.remember5.core.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.remember5.core.eneity.AppInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 关于uniapp通用的工具类
 * @author wangjiahao
 * @date 2022/9/29 22:30
 */
@Slf4j
public class UniAppUtils {


    /**
     * @return void 返回类型
     * @throws
     * @Description: (读取Zip信息 ， 获得zip中所有的目录文件信息)
     * @param设定文件
     */
    public static AppInformation readZipFile(MultipartFile multipartFile) {
        try {
            // 获得zip信息
            File file = new File("./tmp1");
            cn.hutool.core.io.FileUtil.writeFromStream(multipartFile.getInputStream(), file);
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry("manifest.json");
            if (entry != null) {
                InputStream inputStream = zipFile.getInputStream(entry);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s = bufferedReader.readLine();
                JSONObject manifestJson = JSON.parseObject(s);
                JSONObject versionJson = manifestJson.getJSONObject("version");

                AppInformation appInformation = new AppInformation();
                appInformation.setAppid(manifestJson.getString("id"));
                appInformation.setName(manifestJson.getString("name"));
                appInformation.setDescription(manifestJson.getString("description"));
                
                appInformation.setVersion(versionJson.getString("name"));
                appInformation.setBuild(versionJson.getString("code"));
                return appInformation;
            }
        } catch (Exception e) {
            // TODO 抛出解析包的异常，做全局拦截
            log.error(e.getMessage(), e);
        }
        return null;
    }

//    public static MultipartFile zipFile(MultipartFile multipartFile) {
//        // 获得zip信息
//        File file = new File("./tmp");
//        try {
//            //转换到文件 zipfile只能从file读
//            FileUtil.writeFromStream(multipartFile.getInputStream(), file);
//            ZipFile zipFile = new ZipFile(file);
//            FileOutputStream fileOutputStream = new FileOutputStream("./tmp1.wgt");
//            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
//            for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
//                ZipEntry entryIn = (ZipEntry) e.nextElement();
//                if (entryIn == null) {
//                    continue;
//                }
//                byte[] bytes = new byte[10240];
//                int leng = 0;
//                if ("manifest.json".equals(entryIn.getName())) {
//                    InputStream inputStream = zipFile.getInputStream(entryIn);
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    String s = bufferedReader.readLine();
//
//                    inputStreamReader.close();
//                    inputStream.close();
//                    bufferedReader.close();
//
//                    JSONObject parse = JSONObject.parseObject(s);
//                    entryIn = new ZipEntry("manifest.json");
//
//
//                    JSONObject version = parse.getJSONObject("version");
//                    String name = version.getString("name");
//                    Integer code = version.getInteger("code");
//
//
//                    code += 1;
//                    version.remove("code");
//                    version.put("code", code);
//
//
//                    String s1 = parse.toJSONString();
//                    zipOutputStream.putNextEntry(entryIn);
//                    zipOutputStream.write(s1.getBytes(StandardCharsets.UTF_8));
//                } else {
//                    InputStream inputStream = zipFile.getInputStream(entryIn);
//                    zipOutputStream.putNextEntry(entryIn);
//                    while ((leng = inputStream.read(bytes)) > 0) {
//                        zipOutputStream.write(bytes, 0, leng);
//                    }
//                }
//                zipOutputStream.closeEntry();
//            }
//            zipFile.close();
//            zipOutputStream.finish();
//            zipOutputStream.close();
//            FileInputStream fileInputStream = new FileInputStream("./tmp1.wgt");
//            return new MockMultipartFile("tmp1.wgt", fileInputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
