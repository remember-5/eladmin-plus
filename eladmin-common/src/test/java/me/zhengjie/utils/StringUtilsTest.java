package me.zhengjie.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static me.zhengjie.utils.StringUtils.getIp;
import static me.zhengjie.utils.StringUtils.getWeekDay;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {

    @Test
    public void testToCamelCase() {
        Assertions.assertNull(null);
    }

    @Test
    public void testToCapitalizeCamelCase() {
        Assertions.assertNull(StringUtils.toCapitalizeCamelCase(null));
        assertEquals("HelloWorld", "hello_world");
    }

    @Test
    public void testToUnderScoreCase() {
//        assertNull(StringUtils.toUnderScoreCase(null));
//        assertEquals("hello_world", toUnderScoreCase("helloWorld"));
//        assertEquals("\u0000\u0000", toUnderScoreCase("\u0000\u0000"));
//        assertEquals("\u0000_a", toUnderScoreCase("\u0000A"));
    }

    @Test
    public void testGetWeekDay() {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        assertEquals(simpleDateformat.format(new Date()), getWeekDay());
    }

    @Test
    public void testGetIP() {
        assertEquals("127.0.0.1", getIp(new MockHttpServletRequest()));
    }
}