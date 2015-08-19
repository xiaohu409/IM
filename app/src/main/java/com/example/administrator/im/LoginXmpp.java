/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.administrator.im;

/**
 * Created by Administrator on 2015/8/7.
 */

import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;


public class LoginXmpp {

    //xmpp 服务器地址
    private static String host = "192.168.0.71";
    //xmpp 服务器端口
    private static int prot = 5222;
    //xmpp 服务器名称 可能是计算机名字
    private static String serviceName = "desktop-mlf2s50";

    public static boolean login(String username, String password) {
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
        configBuilder.setUsernameAndPassword(username, password);

        XMPPTCPConnection connection = new XMPPTCPConnection(configBuilder.build());

        try {
            connection.connect();
            connection.login();
            return  true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
    }
}
