package me.zhengjie.modules.minio.utils;


import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

import static me.zhengjie.modules.minio.config.MinIOCode.UPLOAD_FAILED;

@Slf4j
@Component
public class MinIOUtils {
    @Resource
    private MinioClient minioClient;

    /**
     * @Author: fly
     * @Description: 上传文件至minio服务器
     * @Date: 2020/10/15
     * @Param: [file, bucket, packageName]文件，文件分区名，文件夹名
     * @Return: String 文件名称
     */
    public String upload(MultipartFile file, String bucket, String packageName)  {
        bucketExists(bucket);
        //获取原始文件名称  XX.png   XX.png
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        //获取原始文件名称后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件名称
        String newFileName = UUID.randomUUID() + suffix;
        PutObjectOptions putObjectOptions= new PutObjectOptions(file.getSize(),-1);
        putObjectOptions.setContentType(Objects.requireNonNull(file.getContentType()));
        try {
            minioClient.putObject(bucket,packageName + newFileName,file.getInputStream(),putObjectOptions);
        } catch (Exception e) {
            log.error(UPLOAD_FAILED);
            e.printStackTrace();
            return UPLOAD_FAILED;
        }
        return newFileName;
    }


    /**
     * @Author: fly
     * @Description:  检查文件分区是否存在，如果没有就创建
     * @Date: 2020/10/15
     * @Param: [bucket]文件分区名字
     * @Return: void
     */
    private void bucketExists(String bucket){
        boolean bucketisExist = false;
        try {
            bucketisExist = minioClient.bucketExists(bucket);
            if (bucketisExist){
                log.error("文件分区已存在");
            }else {
                log.info("文件分区未存在,正在创建分区{}",bucket);
                minioClient.makeBucket(bucket);
            }
        } catch (Exception e) {
            log.error("文件分区{}异常",bucket);
            e.printStackTrace();
        }
    }
}
