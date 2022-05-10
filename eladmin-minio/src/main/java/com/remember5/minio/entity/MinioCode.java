package com.remember5.minio.entity;

import lombok.NoArgsConstructor;

/**
 * @author fly
 * @date 2020/12/2 17:38
 */
@NoArgsConstructor
public class MinioCode {

    /**
     * /
     */
    public static final char SLASH = '/';
    /**
     * file
     */
    public static final String FILE = "file";
    /**
     * minioConfig Bean name
     */
    public static final String MINIO = "minioConfig";
    /**
     * minioClient Bean name
     */
    public static final String MINIO_CLIENT = "minioClient";
    /**
     * remove Error
     */
    public static final String REMOVE_ERROR = "remove Error";
    /**
     * remove Success
     */
    public static final String REMOVE_SUCCESS = "remove Success";
    /**
     * register Bean Error
     */
    public static final String REGISTER_ERROR = "register Bean Error";
    /**
     * register Bean Success
     */
    public static final String REGISTER_SUCCESS = "register Bean Success";
}