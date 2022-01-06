package me.zhengjie.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fly
 * @date 2021-12-24
 */
@Data
public class NettyPushMessageBody implements Serializable {

    private static final long serialVersionUID = 8783105942749912792L;
    private String userId;

    private String message;
}
