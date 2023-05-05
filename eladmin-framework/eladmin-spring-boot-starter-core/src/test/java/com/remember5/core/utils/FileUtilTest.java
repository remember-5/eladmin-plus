package com.remember5.core.utils;

import com.remember5.core.utils.FileUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static com.remember5.core.utils.FileUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilTest {

    @Test
    public void testToFile() {
        long retval = FileUtil.toFile(new MockMultipartFile("foo", (byte[]) null)).getTotalSpace();
        Assertions.assertEquals(500695072768L, retval);
    }

    @Test
    public void testGetExtensionName() {
        Assertions.assertEquals("foo", FileUtil.getExtensionName("foo"));
        Assertions.assertEquals("exe", FileUtil.getExtensionName("bar.exe"));
    }

    @Test
    public void testGetFileNameNoEx() {
        Assertions.assertEquals("foo", FileUtil.getFileNameNoEx("foo"));
        Assertions.assertEquals("bar", FileUtil.getFileNameNoEx("bar.txt"));
    }

    @Test
    public void testGetSize() {
        Assertions.assertEquals("1000B   ", FileUtil.getSize(1000));
        Assertions.assertEquals("1.00KB   ", FileUtil.getSize(1024));
        Assertions.assertEquals("1.00MB   ", FileUtil.getSize(1048576));
        Assertions.assertEquals("1.00GB   ", FileUtil.getSize(1073741824));
    }
}
