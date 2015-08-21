/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.util;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by hutao on 2015/8/19.
 */
public class DateTimeUtil {

    /**
     * 获取指定格式的日期
     * @param format
     * @return
     */
    public static String getDateTime(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(calendar.getTime());
    }

}
