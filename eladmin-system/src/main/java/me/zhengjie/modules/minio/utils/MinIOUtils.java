package me.zhengjie.modules.minio.utils;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static me.zhengjie.modules.minio.config.MinIOCode.*;
import static me.zhengjie.utils.SpringContextHolder.getBean;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinIOUtils {
    //@Resource
    //private MinioClient minioClient;
    //private final MinIOConfig minIOConfig;
    private final ResourcesManagementService managementService;

    /**
     * 上传文件
     * 文件夹名取日期
     * 文件名取UUID
     *
     * @param file 文件
     * @return /
     */
    public String upload(MultipartFile file) {
        //获取原始文件名称  XX.png   XX.png
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        //获取原始文件名称后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(StrUtil.DOT));
        //新文件名称
        String newFileName = UUID.randomUUID(true).toString() + suffix;
        //获取当前日期作为文件夹名
        String packageName = LocalDate.now().toString();
        // 上传文件
        return uploadFile(file, packageName, newFileName);
    }

    /**
     * 上传文件
     * 文件夹名自定义
     * 文件名取UUID
     *
     * @param file        文件
     * @param packageName 文件夹名
     * @return /
     */
    public String upload(MultipartFile file, String packageName) {
        //获取原始文件名称  XX.png   XX.png
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        //获取原始文件名称后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(StrUtil.DOT));
        //新文件名称
        String newFileName = UUID.randomUUID(true).toString() + suffix;
        // 上传文件
        return uploadFile(file, packageName, newFileName);
    }

    /**
     * 上传文件
     * 文件夹名取日期
     * 文件名自定义
     *
     * @param fileName 文件名
     * @param file     文件
     * @return /
     */
    public String upload(String fileName, MultipartFile file) {
        //获取当前日期作为文件夹名
        String packageName = LocalDate.now().toString();
        // 上传文件
        return uploadFile(file, packageName, fileName);
    }

    /**
     * @Author: fly
     * @Description: 上传文件至minio服务器
     * @Date: 2020/10/15
     * @Param: [file, bucket, packageName, fileName]文件，文件分区名，文件夹名，文件名
     * @Return: String 文件url
     */
    public String uploadFile(MultipartFile file, String packageName, String fileName) {
        String fileType = Objects.requireNonNull(file.getContentType());
        //文件分区名
        ResourcesManagement byMinioEnabled = managementService.getMinioConfig();
        String bucket = byMinioEnabled.getBucket();
        //String bucket = minIOConfig.getBucket();
        bucketExists(bucket);
        PutObjectOptions putObjectOptions = new PutObjectOptions(file.getSize(), StrUtil.INDEX_NOT_FOUND);
        putObjectOptions.setContentType(fileType);
        try {
            MinioClient minioClient = getBean(MINIOCLIENT);
            minioClient.putObject(bucket, packageName + SLASH + fileName, file.getInputStream(), putObjectOptions);
        } catch (Exception e) {
            log.error(UPLOAD_FAILED);
            e.printStackTrace();
            return UPLOAD_FAILED;
        }
        return new UrlBuilder()
                //.setHost(minIOConfig.getHost().substring(7))
                //.setPort(minIOConfig.getPort())
                .setHost(byMinioEnabled.getUrl().substring(7))
                .setPort(byMinioEnabled.getPort())
                .addPath(bucket)
                .appendPath(packageName)
                .appendPath(fileName)
                .build();
    }


    /**
     * @Author: fly
     * @Description: 检查文件分区是否存在，如果没有就创建
     * @Date: 2020/10/15
     * @Param: [bucket]文件分区名字
     * @Return: void
     */
    private void bucketExists(String bucket) {
        boolean bucketisExist = false;
        MinioClient minioClient = getBean(MINIOCLIENT);
        try {
            bucketisExist = minioClient.bucketExists(bucket);
            if (bucketisExist) {
                log.error("文件分区已存在");
            } else {
                log.info("文件分区未存在,正在创建分区{}", bucket);
                minioClient.makeBucket(bucket);
            }
        } catch (Exception e) {
            log.error("文件分区{}异常", bucket);
            e.printStackTrace();
        }
    }


    /**
     * 获取全部bucket
     *
     * @return /
     */
    public List<Bucket> getAllBuckets() throws Exception {
        MinioClient minioClient = getBean(MINIOCLIENT);
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    public Optional<Bucket> getBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, ErrorResponseException, ServerException, XmlParserException, InvalidBucketNameException {
        MinioClient minioClient = getBean(MINIOCLIENT);
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) throws Exception {
        MinioClient minioClient = getBean(MINIOCLIENT);
        minioClient.removeBucket(bucketName);
    }

    /**
     * 获取⽂件外链
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    //    public String getObjectURL(String bucketName, String objectName, Integer expires) throws Exception {
    //        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(expires).build());
    //    }

    /**
     * 获取⽂件
     *
     * @param objectName ⽂件名称
     * @return ⼆进制流
     */
    public InputStream getObject(String objectName) throws Exception {
        ResourcesManagement byMinioEnabled = managementService.getMinioConfig();
        String bucketName = byMinioEnabled.getBucket();
        //String bucketName = minIOConfig.getBucket();
        MinioClient minioClient = getBean(MINIOCLIENT);
        return minioClient.getObject(bucketName, objectName);
    }

    /**
     * 上传⽂件
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param stream     ⽂件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    //    public void putObject(String bucketName, String objectName, InputStream stream) throws
    //            Exception {
    //        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).contentType(objectName.substring(objectName.lastIndexOf("."))).build());
    //    }

    /**
     * 上传⽂件
     * @param bucketName  bucket名称
     * @param objectName  ⽂件名称
     * @param stream      ⽂件流
     * @param size        ⼤⼩
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    //    public void putObject(String bucketName, String objectName, InputStream stream, long
    //            size, String contextType) throws Exception {
    //        minioClient.putObject(bucketName, objectName, stream, contextType);
    //    }

    /**
     * 获取⽂件信息
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    //    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
    //        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    //    }

    /**
     * 删除⽂件
     *
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-client-apireference.html#removeObject
     */
    public void removeObject(String objectName) throws Exception {
        ResourcesManagement byMinioEnabled = managementService.getMinioConfig();
        String bucketName = byMinioEnabled.getBucket();
        //String bucketName = minIOConfig.getBucket();
        MinioClient minioClient = getBean(MINIOCLIENT);
        minioClient.removeObject(bucketName, objectName);
        log.info("删除{}文件成功", objectName);
    }
}
