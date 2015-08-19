/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hutao on 2015/8/19.
 */
public class ToastUtil {

    public static void showShort(Context context, String tips) {
        Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
    }
}
