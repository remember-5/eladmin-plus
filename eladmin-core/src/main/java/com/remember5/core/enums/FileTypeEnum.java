package com.remember5.core.enums;

/**
 * @author wangjiahao
 */

public enum FileTypeEnum {

    // File suffix
    JPG(".jpg", "image/jpeg", ""),
    JPEG(".jpeg", "image/jpeg", ""),
    PNG(".png", "image/png", ""),
    MP3(".mp3", "audio/mpeg", ""),
    MP4(".mp4", "video/mp4", ""),
    TXT(".txt", "text/plain", ""),
    EXE(".exe", "application/x-msdownload", ""),
    PDF(".pdf", "application/pdf", ""),
    XLS(".xls", "application/vnd.ms-excel", ""),
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ""),
    XLSX_UTF8(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8", ""),
    DOC(".doc", "application/msword", ""),
    DOCX(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", ""),
    PPT(".ppt", "application/vnd.ms-powerpoint", ""),
    PPTX(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", ""),
    ZIP(".zip", "application/zip", ""),
    GZ(".gz", "application/gzip", ""),
    TAR(".tar", "application/x-tar", ""),
    WGT(".wgt", "application/widget","");

    public final String suffix;

    public final String mimeType;

    public final String icon;

    FileTypeEnum (String suffix, String mimeType, String icon) {
        this.suffix = suffix;
        this.mimeType = mimeType;
        this.icon = icon;
    }
}
