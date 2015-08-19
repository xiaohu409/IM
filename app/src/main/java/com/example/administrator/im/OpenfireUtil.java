/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.administrator.im;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created by hutao on 2015/8/19.
 */
public class OpenfireUtil {

    //xmpp 服务器地址
    private static String host = "192.168.0.71";
    //xmpp 服务器端口
    private static int prot = 5222;
    //xmpp 服务器名称 可能是计算机名字
    private static String serviceName = "desktop-mlf2s50";

    private static XMPPTCPConnection connection;

    /**
     * 连接服务器
     * @return
     */
    public static boolean connectionServer() {
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        //服务器IP地址
        configBuilder.setHost(host);
        //服务器端口
        configBuilder.setPort(prot);
        //服务器名称
        configBuilder.setServiceName(serviceName);
        //是否开启安全模式
        configBuilder.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled);
        //是否开启压缩
        //configBuilder.setCompressionEnabled(false);
        //开启调试模式
        configBuilder.setDebuggerEnabled(true);
        //用户名密码
        if (connection == null) {
            connection = new XMPPTCPConnection(configBuilder.build());
        }
        try {
            connection.connect();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * 登陆openfire
     * @param username
     * @param password
     * @return
     */
    public static boolean login(String username, String password) {
        if (connection == null) {
            return  false;
        }
        try {
            connection.login(username, password);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
