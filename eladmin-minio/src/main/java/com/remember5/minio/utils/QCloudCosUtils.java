package com.remember5.minio.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author wangjiahao
 * @date 2022/3/21 15:00
 * @see "https://cloud.tencent.com/document/product/436/10199"
 */
@Slf4j
@Component
public class QCloudCosUtils {

    public static final String PREFIX_URL = "";
    public static final String SECRET_ID = "";
    public static final String SECRET_KEY = "";
    public static final String REGION_KEY = "";
    public static final String BUCKETS_NAME = "";
    public static final String APPID = "";
    public static COSClient cosClient;

    public QCloudCosUtils() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(REGION_KEY);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    public static void getAllBuckets() {
        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.err.println(bucketName);
            System.err.println(bucketLocation);
        }
    }

    /**
     * 上传文件
     *
     * @param filePath 路径
     * @param key      文件key=文件名
     */
    public static String upload(String filePath, String key) {
        // 指定要上传的文件
        File localFile = new File(filePath);
        return uploadFile(localFile, key);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @param key  文件key=文件名
     */
    public static String uploadFile(File file, String key) {
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下 key=fileName
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETS_NAME, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return PREFIX_URL + key;
    }

    /**
     * @param key
     * @return
     */
    public static boolean removeFile(String key) {
        try {
            cosClient.deleteObject(BUCKETS_NAME, key);
        } catch (CosClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 获取文件夹下的所有文件
     */
    public static void getAllFile() {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(BUCKETS_NAME);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix("exampleobject/");
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return;
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                System.err.println(key);
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
    }


    public static void main(String[] args) {
//        getAllBuckets();
//        final String upload = upload("/Users/wangjiahao/Downloads/1.png", "2.png");
//        System.err.println(upload);
//        getAllFile();
    }

}
