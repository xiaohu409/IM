/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.util;

/**
 * Created by hutao on 2015/8/19.
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        else {
            return  false;
        }
    }
}
