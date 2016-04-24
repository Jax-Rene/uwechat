package com.youyicun.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by johnny on 16/4/24.
 */
public class DateUtil {
    public static String parseLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
