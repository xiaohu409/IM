/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.util;

import android.util.Log;

/**
 * Created by hutao on 2015/8/19.
 */
public class DebugUtil  {

    /**
     * Logcat 日志
     * debug = true 输出日志 否则 不输出日志
     */
    private static final boolean DEBUG = false;

    /**
     * 输出Debug 日志
     * @param tag
     * @param msg
     */
    public static void logD(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * 输出Error 日志
     * @param tag
     * @param msg
     */
    public static void logE(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }
}
