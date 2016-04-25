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

    /**
     * yyyy-MM-dd HH:mm:dd  -> yyyy-MM-ddTHH:mm:dd
     * @param dateTime
     * @return
     */
    public static String parseDateTimeToLocal(String dateTime){
        String[] s = dateTime.split(" ");
        return s[0] + "T" + s[1];
    }
}
