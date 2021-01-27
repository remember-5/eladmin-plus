package me.zhengjie.modules.minio.service;

import me.zhengjie.result.RestResult;
import org.springframework.web.multipart.MultipartFile;

public interface MinIOService {

    /**
     * @Author: fly
     * @Description: 文件上传
     * @Date: 2020/10/15
     * @Param: [file]
     * @Return: java.lang.String
     */
    RestResult uploadFile(MultipartFile file);
}
