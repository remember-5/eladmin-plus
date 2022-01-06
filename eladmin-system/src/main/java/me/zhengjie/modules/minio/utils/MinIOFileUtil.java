package me.zhengjie.modules.minio.utils;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static me.zhengjie.modules.minio.config.MinIOCode.FILE;

/**
 * @Description inputStream 转 File
 * @Author fly
 * @Date 2021/4/7 16:13
 */
@Slf4j
public class MinIOFileUtil {

    /**
     * 系统临时目录
     * <br>
     * windows 包含路径分割符，但Linux 不包含,
     * 在windows \\==\ 前提下，
     * 为安全起见 同意拼装 路径分割符，
     * <pre>
     *       java.io.tmpdir
     *       windows : C:\Users/xxx\AppData\Local\Temp\
     *       linux: /temp
     * </pre>
     */
    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;

    /**
     * inputStream 转 File
     */
    public static File inputStreamToFile(InputStream ins, String name) {
        File file = new File(SYS_TEM_DIR + name);
        OutputStream os = null;
        try {
            if (file.exists()) {
                return file;
            }
            os = new FileOutputStream(file);
            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            return file;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            IoUtil.close(os);
            IoUtil.close(ins);
        }


    }

    /**
     * File 转 MultipartFile
     */
    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        return new MockMultipartFile(FILE, file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(file));
    }
}
