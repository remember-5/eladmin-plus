package com.remember5.system.modules.minio.rest;


import com.remember5.system.modules.minio.service.MinioService;
import com.remember5.minio.entity.Base64Uploader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.remember5.logging.annotation.Log;
import com.remember5.core.result.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * @author wangjiahao
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "上传文件")
@RequestMapping("uploadFile")
public class MinioController {

    private final MinioService minioService;

    /**
     * MultipartFile 上传形式
     *
     * @param file 文件
     * @return return
     */
    @Log("上传文件:MultipartFile")
    @ApiOperation("上传文件:MultipartFile")
    @PostMapping("upload")
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        return minioService.uploadFile(file);
    }

    @Log("上传文件:自定义文件名")
    @ApiOperation("上传文件:自定义文件名")
    @PostMapping("/uploadName")
    public R uploadFileAndName(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
        return minioService.uploadFileAndName(file, fileName);
    }
    /**
     * base64上传
     *
     * @param base64Uploader 文件
     * @return return
     */
    @Log("上传文件:base64")
    @ApiOperation("上传文件:base64")
    @PostMapping("uploadBaseFile")
    public R uploadBase64File(@RequestBody Base64Uploader base64Uploader) {
        return minioService.uploadFile(base64Uploader.getData());
    }

    /**
     * 删除文件
     */
    @Log("删除文件")
    @ApiOperation("删除文件")
    @DeleteMapping("/delete/{objectName}")
    public R delete(@PathVariable("objectName") String objectName) throws Exception {
        return minioService.removeObject(objectName);
    }

    /**
     * 在浏览器预览图片
     */
    @ApiOperation("在浏览器预览图片")
    @GetMapping("/preViewPicture/{objectName}")
    public void preViewPicture(@PathVariable("objectName") String objectName, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        try (ServletOutputStream out = response.getOutputStream()) {
            InputStream stream = minioService.getObject(objectName);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();
            out.write(bytes);
            out.flush();
        }
    }
}
