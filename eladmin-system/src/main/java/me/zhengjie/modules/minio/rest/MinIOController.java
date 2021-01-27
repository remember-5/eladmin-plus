package me.zhengjie.modules.minio.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.minio.service.MinIOService;
import me.zhengjie.result.RestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static me.zhengjie.modules.minio.utils.BASE64DecodedMultipartFile.base64ToMultipart;


@RestController
@RequiredArgsConstructor
@Api(tags = "上传文件")
@RequestMapping("uploadFile")
public class MinIOController {

    private final MinIOService minIOService;

    /**
     * MultipartFile 上传形式
     * @param file 文件
     * @return return
     */
    @PostMapping("upload")
    public RestResult uploadFile(@RequestParam("file") MultipartFile file){
        return minIOService.uploadFile(file);
    }

    /**
     * base64上传
     * @param fileData 文件
     * @return return
     */
    @PostMapping("uploadBaseFile")
    @ApiImplicitParam(name = "fileData", value = "上传的Base64格式文件", required = true)
    public RestResult uploadBase64File(@RequestParam String fileData){
        MultipartFile file = base64ToMultipart(fileData);
        return minIOService.uploadFile(file);
    }

}
