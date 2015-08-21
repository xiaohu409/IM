/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.im;

import com.example.util.DateTimeUtil;

/**
 * Created by hutao on 2015/8/21.
 */
public class MessageUtil {

    /**
     * 对发送的信息进行封装
     * 封装后的格式：（12:23）我：你好
     * @param nickName
     * @param msg
     * @return
     */
    public static String packageMsg(String nickName, String msg) {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(DateTimeUtil.getDateTime("HH:mm"));
        result.append(")");
        result.append(nickName);
        result.append(":");
        result.append(msg);
        result.append("\n");
        return result.toString();
    }
}
