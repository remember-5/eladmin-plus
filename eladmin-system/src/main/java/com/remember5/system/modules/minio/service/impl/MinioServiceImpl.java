package com.remember5.system.modules.minio.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.remember5.core.enums.FileTypeEnum;
import com.remember5.core.result.R;
import com.remember5.core.result.REnum;
import com.remember5.minio.entity.Base64DecodedMultipartFile;
import com.remember5.minio.entity.MinioResponse;
import com.remember5.minio.properties.MinioProperties;
import com.remember5.minio.utils.MinioUtils;
import com.remember5.system.modules.minio.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.remember5.core.utils.FileUtil.checkFileType;

/**
 * @author wangjiahao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioProperties minioProperties;
    private final MinioUtils minioUtils;

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
            // 校验文件是否属于可上传类型
            if (!checkAllFileType(file)) {
                return R.fail(REnum.A0400);
            }
            try {
                MinioResponse upload = minioUtils.upload(file, minioProperties.getBucket());
                return upload != null && upload.stats() ? R.success(upload.url()) : R.fail(REnum.A0500);
            } catch (IOException e) {
                log.error(e.getMessage());
//                throw new RuntimeException(e);
                return R.fail(REnum.A0500);
            }
        }
        return R.fail(REnum.A0500);
    }

    /**
     * @author: fly
     * @description: 自定义文件名称 上传
     * @param file 文件
     * @param fileName 文件名称
     * @return /
     */
    @Override
    public R uploadFileAndName(MultipartFile file, String fileName) {
        if (ObjectUtil.isNotNull(file)) {
            try {
                // 校验文件是否属于可上传类型
                if (!checkAllFileType(file)) {
                    return R.fail(REnum.A0400);
                }
                // 文件名称由前端传入
                MinioResponse upload = minioUtils.upload(file, minioProperties.getBucket(), fileName);
                return upload != null && upload.stats() ? R.success(upload.url()) : R.fail(REnum.A0500);
            } catch (IOException e) {
                log.error(e.getMessage());
                //                throw new RuntimeException(e);
                return R.fail(REnum.A0500);
            }
        }
        return R.fail(REnum.A0500);
    }

    /**
     * base64上传
     *
     * @param fileData base64
     * @return /
     */
    @Override
    public R uploadFile(String fileData) {
        return uploadFile(Base64DecodedMultipartFile.base64ToMultipart(fileData));
    }

    @Override
    public R removeObject(String objectName) {
        try {
            minioUtils.removeObject(minioProperties.getBucket(), objectName);
            return R.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.fail(REnum.A0500);
        }
    }

    @Override
    public InputStream getObject(String objectName) {
        try {
            return minioUtils.getObject(minioProperties.getBucket(), objectName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 校验文件类型是否在可上传的类型列表中
     * @param file 文件
     * @return 是否可上传
     */
    private boolean checkAllFileType(MultipartFile file) {
        boolean falg = true;
        for (FileTypeEnum e : FileTypeEnum.values()) {
            falg = checkFileType(file, e.suffix, e.mimeType);
            if (falg) {
                break;
            }
        }
        return falg;
    }


}
