package com.xyh.sleeper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Administrator on 2018/1/16 0016
 */

public class DateUtil {

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(new Date());
    }

    public static long getTimeMillis(String dateTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(dateTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
