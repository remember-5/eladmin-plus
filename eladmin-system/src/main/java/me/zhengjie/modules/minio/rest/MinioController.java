package me.zhengjie.modules.minio.rest;


import com.remember5.minio.entity.Base64Uploader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.minio.service.MinioService;
import me.zhengjie.result.R;
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
     * 下载文件到本地
     */
    //    @Log("下载文件")
    //    @ApiOperation("下载文件到本地")
    //    @GetMapping("/download/{objectName}")
    //    public ResponseEntity<byte[]> downloadToLocal(@PathVariable("objectName") String objectName, HttpServletResponse response) throws Exception {
    //        ResponseEntity<byte[]> responseEntity = null;
    //        InputStream stream = null;
    //        ByteArrayOutputStream output = null;
    //        try {
    //            // 获取"myobject"的输入流。
    //            stream = minIOService.getObject( objectName);
    //            if (stream == null) {
    //                System.out.println("文件不存在");
    //            }
    //            //用于转换byte
    //            output = new ByteArrayOutputStream();
    //            byte[] buffer = new byte[4096];
    //            int n = 0;
    //            while (-1 != (n = stream.read(buffer))) {
    //                output.write(buffer, 0, n);
    //            }
    //            byte[] bytes = output.toByteArray();
    //
    //            //设置header
    //            HttpHeaders httpHeaders = new HttpHeaders();
    //            httpHeaders.add("Accept-Ranges", "bytes");
    //            httpHeaders.add("Content-Length", bytes.length + "");
    ////            objectName = new String(objectName.getBytes("UTF-8"), "ISO8859-1");
    //            //把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
    //            httpHeaders.add("Content-disposition", "attachment; filename=" + objectName);
    //            httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
    ////            httpHeaders.add("Content-Type", "image/jpeg");
    //            responseEntity = new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.CREATED);
    //
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            if (stream != null) {
    //                stream.close();
    //            }
    //            if (output != null) {
    //                output.close();
    //            }
    //        }
    //        return responseEntity;
    //    }

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
