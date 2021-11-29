package me.zhengjie.modules.minio.service.impl;


import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.minio.service.MinIOService;
import me.zhengjie.modules.minio.utils.MinIOUtils;
import me.zhengjie.result.R;
import me.zhengjie.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

import static me.zhengjie.modules.minio.config.MinIOCode.UPLOAD_FAILED;
import static me.zhengjie.modules.minio.utils.BASE64DecodedMultipartFile.base64ToMultipart;
import static me.zhengjie.modules.minio.utils.MinIOFileUtil.fileToMultipartFile;
import static me.zhengjie.modules.minio.utils.MinIOFileUtil.inputStreamToFile;

/**
 * @author wangjiahao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {

    private final MinIOUtils minIOUtils;

    /**
     * @Author: fly
     * @Description: 文件上传
     * @Date: 2020/10/15
     * @Param: [file]
     * @Return: java.lang.String
     */
    @Override
    public R uploadFile(MultipartFile file) {
        if (ObjectUtil.isNotNull(file)) {
            String newFileUrl = minIOUtils.upload(file);
            if (newFileUrl != UPLOAD_FAILED && !UPLOAD_FAILED.equals(newFileUrl)) {
                return new R().resultEnum(ResultEnum.A00000).data(newFileUrl);
            }
            return new R().resultEnum(ResultEnum.A0500);
        }
        return new R().resultEnum(ResultEnum.A0400);
    }

    /**
     * base64上传
     *
     * @param fileData base64
     * @return /
     */
    @Override
    public R uploadFile(String fileData) {
        MultipartFile file = base64ToMultipart(fileData);
        return uploadFile(file);
    }

    /**
     * InputStream 上传
     *
     * @param inputStreamFile InputStream流文件
     * @return
     */
    @Override
    public R uploadFile(InputStream inputStreamFile, String fileName) {
        try {
            // InputStream 转 File
            File file = inputStreamToFile(inputStreamFile, fileName);
            // File 转 MultipartFile
            MultipartFile multipartFile = fileToMultipartFile(file);
            // 上传文件
            return uploadFile(multipartFile);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new R().resultEnum(ResultEnum.A0500);
        }
    }

    @Override
    public R removeObject(String objectName) {
        try {
            minIOUtils.removeObject(objectName);
            return new R().resultEnum(ResultEnum.A00000);
        } catch (Exception e) {
            e.printStackTrace();
            return new R().resultEnum(ResultEnum.A0500);
        }
    }

    @Override
    public InputStream getObject(String objectName) {
        try {
            return minIOUtils.getObject(objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
