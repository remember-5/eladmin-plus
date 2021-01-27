package me.zhengjie.modules.minio.service.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.minio.config.MinIOConfig;
import me.zhengjie.modules.minio.service.MinIOService;
import me.zhengjie.modules.minio.utils.MinIOUtils;
import me.zhengjie.result.RestResult;
import me.zhengjie.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static me.zhengjie.modules.minio.config.MinIOCode.SLASH;
import static me.zhengjie.modules.minio.config.MinIOCode.UPLOAD_FAILED;

@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {

    private final MinIOConfig minIOConfig;
    private final MinIOUtils minIOUtils;

    /**
     * @Author: fly
     * @Description: 文件上传
     * @Date: 2020/10/15
     * @Param: [file]
     * @Return: java.lang.String
     */
    @Override
    public RestResult uploadFile(MultipartFile file){
        if (!ObjectUtil.isNull(file)){
            //文件分区名
            String bucket =minIOConfig.getBucket();
            //获取当前日期作为文件夹名
            String packageName=LocalDate.now().toString() + SLASH;
            String newFileName = minIOUtils.upload(file, bucket, packageName);
            if (newFileName != UPLOAD_FAILED&& !UPLOAD_FAILED.equals(newFileName)){
                return new RestResult().resultEnum(ResultEnum.A00000).data(
                        new UrlBuilder()
                                .setHost(minIOConfig.getHost().substring(7))
                                .setPort(minIOConfig.getPort())
                                .addPath(bucket)
                                .appendPath(LocalDate.now().toString())
                                .appendPath(newFileName)
                                .build());
            }
            return new RestResult().resultEnum(ResultEnum.A0500);
        }
        return new RestResult().resultEnum(ResultEnum.A0400);
    }

}
