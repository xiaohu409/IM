/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.util;

/**
 * Created by hutao on 2015/8/19.
 */
public class LogUtil {

    /**
     * 日志
     * @param msg
     * @return
     */
    public static String Log(String msg) {
        StringBuilder result = new StringBuilder();
        String datetiem = DateTimeUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
        result.append(datetiem);
        result.append(":");
        result.append(msg);
        result.append("\n");
        return result.toString();
    }
}
