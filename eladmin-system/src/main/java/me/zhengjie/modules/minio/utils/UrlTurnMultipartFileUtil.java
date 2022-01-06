package me.zhengjie.modules.minio.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * url转为 MultipartFile对象
 * @author  fly
 * @date 2020/12/16 18:16
 */
@Slf4j
public class UrlTurnMultipartFileUtil {

    public static final String MEDIA_TYPE_MP3 = "mp3";
    public static final String MEDIA_TYPE_MP4 = "mp4";
    public static final String HTTP_CONTENT_TYPE_MEDIA_TYPE_AUDIO = "audio/mpeg";
    public static final String HTTP_CONTENT_TYPE_MEDIA_TYPE_VIDEO = "video/mp4";

    public static MultipartFile createFileItem(String url, String fileName) {
        HttpResponse httpResponse = HttpRequest.get(url).execute();
        if (httpResponse.isOk()) {
            FileItem item;
            OutputStream os = null;
            InputStream is  = null;
            String filetype = url.substring(url.lastIndexOf(".") + 1, url.length());
            try {
                is = httpResponse.bodyStream();
                FileItemFactory factory = new DiskFileItemFactory(16, null);
                if (ImgUtil.IMAGE_TYPE_JPG.equals(filetype)) {
                    item = factory.createItem(fileName + filetype, ContentType.IMAGE_JPEG.toString(), false, fileName);
                } else if (ImgUtil.IMAGE_TYPE_PNG.equals(filetype)) {
                    item = factory.createItem(fileName + filetype, ContentType.IMAGE_PNG.toString(), false, fileName);
                } else if (MEDIA_TYPE_MP3.equals(filetype)) {
                    item = factory.createItem(fileName + filetype, HTTP_CONTENT_TYPE_MEDIA_TYPE_AUDIO, false, fileName);
                } else if (MEDIA_TYPE_MP4.equals(filetype)) {
                    item = factory.createItem(fileName + filetype, HTTP_CONTENT_TYPE_MEDIA_TYPE_VIDEO, false, fileName);
                } else {
                    item = factory.createItem(fileName + filetype, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
                }
                os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                return new CommonsMultipartFile(item);
            } catch (IOException e) {
                log.error("文件下载失败, {}", e.getMessage());
                return null;
            } finally {
                IoUtil.close(os);
                IoUtil.close(is);
            }
        }
        return null;
    }
}
