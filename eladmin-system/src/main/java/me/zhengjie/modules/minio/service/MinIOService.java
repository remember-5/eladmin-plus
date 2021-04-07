package me.zhengjie.modules.minio.service;

import me.zhengjie.result.RestResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinIOService {

    /**
     * @Author: fly
     * @Description: 文件上传
     * @Date: 2020/10/15
     * @Param: [file]
     * @Return: java.lang.String
     */
    RestResult uploadFile(MultipartFile file);

    /**
     * base64上传
     * @param fileData base64
     * @return /
     */
    RestResult uploadFile(String fileData);

    /**
     * InputStream 上传
     * @param inputStreamFile InputStream流文件
     * @return /
     */
    RestResult uploadFile(InputStream inputStreamFile, String fileName);

    /**
     * 删除文件
     * @param objectName 文件名
     * @return /
     */
    RestResult removeObject(String objectName);

    /**
     * 下载文件流
     * @param objectName 文件名
     * @return /
     */
    InputStream getObject(String objectName);
}
