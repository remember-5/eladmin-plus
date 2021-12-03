package me.zhengjie.modules.minio.service;

import me.zhengjie.result.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author wangjiahao
 */
public interface MinIOService {

    /**
     * 文件上传
     * @param file 文件
     * @return /
     */
    R uploadFile(MultipartFile file);

    /**
     * base64上传
     *
     * @param fileData base64
     * @return /
     */
    R uploadFile(String fileData);

    /**
     * InputStream 上传
     *
     * @param inputStreamFile InputStream流文件
     * @param fileName 文件名
     * @return /
     */
    R uploadFile(InputStream inputStreamFile, String fileName);

    /**
     * 删除文件
     *
     * @param objectName 文件名
     * @return /
     */
    R removeObject(String objectName);

    /**
     * 下载文件流
     *
     * @param objectName 文件名
     * @return /
     */
    InputStream getObject(String objectName);
}
