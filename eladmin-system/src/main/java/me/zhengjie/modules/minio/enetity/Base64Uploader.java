package me.zhengjie.modules.minio.enetity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangjihao
 * @date 2021/5/2
 */
@Data
public class Base64Uploader {

    @ApiModelProperty(name = "name",value = "文件名",dataType = "String")
    private String name;
    @ApiModelProperty(name = "data",value = "文件数据",dataType = "String")
    private String data;


}
