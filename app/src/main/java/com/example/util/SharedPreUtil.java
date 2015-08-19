/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.util;

import android.content.SharedPreferences;
import android.content.Context;

/**
 * Created by hutao on 2015/8/19.
 */
public class SharedPreUtil {

    /**
     * 获取SharedPreference 实例
     * @param context
     * @param preName
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context, String preName) {
        SharedPreferences preferences = context.getSharedPreferences(preName, context.MODE_PRIVATE);
        return preferences;
    }


}
