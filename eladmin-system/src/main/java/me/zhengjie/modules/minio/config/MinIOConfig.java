package me.zhengjie.modules.minio.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangjiahao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinIOConfig implements Serializable {

    private static final long serialVersionUID = 5512969315614829142L;

    private String host;
    private Integer port;
    private String bucket;
    private String accessKey;
    private String secretKey;

}
