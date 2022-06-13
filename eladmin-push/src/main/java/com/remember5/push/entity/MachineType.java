package com.remember5.push.entity;

/**
 * @author wangjiahao
 * @date 2022/5/27 19:56
 */
@SuppressWarnings("all")
public enum MachineType {

    Android("Android"),
    iOS("iOS"),
    WxMiniProgram("WxMiniProgram"),
    WxOffice("WxOffice"),
    ZFB("ZFB");



    private  final String type;

    MachineType(String type) {
        this.type = type;
    }

}
