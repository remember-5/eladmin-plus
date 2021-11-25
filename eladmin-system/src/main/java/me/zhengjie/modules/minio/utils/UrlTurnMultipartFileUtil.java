package me.zhengjie.modules.minio.utils;

import cn.hutool.core.img.ImgUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description url转为 MultipartFile对象
 * @Author fly
 * @Date 2020/12/16 18:16
 */
public class UrlTurnMultipartFileUtil {

    public static final String MEDIA_TYPE_MP3 = "mp3";
    public static final String MEDIA_TYPE_MP4 = "mp4";
    public static final String HTTP_CONTENT_TYPE_MEDIA_TYPE_AUDIO = "audio/mpeg";
    public static final String HTTP_CONTENT_TYPE_MEDIA_TYPE_VIDEO = "video/mp4";

    public static MultipartFile createFileItem(String url, String fileName) {
        FileItem item = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            String filetype = url.substring(url.lastIndexOf(".") + 1, url.length());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
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
                OutputStream os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }

        return new CommonsMultipartFile(item);
    }
}
