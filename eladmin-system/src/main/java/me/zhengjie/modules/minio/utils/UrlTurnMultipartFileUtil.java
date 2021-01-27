package me.zhengjie.modules.minio.utils;

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

    public static MultipartFile createFileItem(String url, String fileName) {
        FileItem item = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            String filetype = url.substring(url.length()-4);
            System.out.println(filetype);
            System.out.println(fileName);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                FileItemFactory factory = new DiskFileItemFactory(16, null);
                if (".jpg".equals(filetype)){
                    item = factory.createItem(fileName+filetype, ContentType.IMAGE_JPEG.toString(), false, fileName);
                }else if (".png".equals(filetype)){
                    item = factory.createItem(fileName+filetype, ContentType.IMAGE_PNG.toString(), false, fileName);
                }else if (".mp3".equals(filetype)){
                    item = factory.createItem(fileName+filetype, "audio/mpeg", false, fileName);
                }else if (".mp4".equals(filetype)){
                    item = factory.createItem(fileName+filetype, "video/mp4", false, fileName);
                } else {
                    item = factory.createItem(fileName+filetype, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
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
