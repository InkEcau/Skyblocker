package de.hysky.skyblocker.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatterUtilTest {
    @Test
    public void testFormatDuration(){
        Assertions.assertEquals("1d1h1min1s", FormatterUtil.formatDuration(90061));
        Assertions.assertEquals("1d2h", FormatterUtil.formatDuration(93600));
        Assertions.assertEquals("1d1min", FormatterUtil.formatDuration(86460));
        Assertions.assertEquals("30s", FormatterUtil.formatDuration(30));
    }

    @Test
    public void testFormatNumber(){
        Assertions.assertEquals("123", FormatterUtil.formatNumber(123));
        Assertions.assertEquals("1,233,666", FormatterUtil.formatNumber(1233666));
    }
}