/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.im;


import android.util.Log;
import android.widget.TextView;

import com.example.util.DebugUtil;
import com.example.util.LogUtil;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by hutao on 2015/8/19.
 */
public class OpenfireUtil {

    //xmpp 服务器地址
    private static final String host = "192.168.0.71";
    //xmpp 服务器端口
    private static final int prot = 5222;
    //xmpp 服务器名称 可能是计算机名字
    private static final String serviceName = "desktop-mlf2s50";

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
      //  configBuilder.setSendPresence(true);

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
     * 判断是否连接服务器
     * @return
     */
    public static boolean isConnect() {
        if (connection == null) {
            return false;
        }
        return connection.isConnected();
    }

    /**
     * 关闭连接
     * @return
     */
    public static boolean closeConnection() {
        if (connection == null) {
            return false;
        }
        if (connection.isConnected()) {
            connection.disconnect();
            connection = null;
            return true;
        }
        return  false;
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

            Presence presence = new Presence(Presence.Type.available);
            presence.setMode(Presence.Mode.chat);
            connection.sendStanza(presence);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *获取所有好友 包括离线的好友
     * @return
     */
    public static List<RosterEntry> getAllEntries() {
        if (connection == null) {
            return null;
        }
        List<RosterEntry> list =  new ArrayList<RosterEntry>();
        Roster roster = Roster.getInstanceFor(connection);
        Set<RosterEntry> set  = roster.getEntries();
        for (RosterEntry rosterEntry : set) {
            list.add(rosterEntry);
        }
        DebugUtil.logD("OpenfireUtil", "");
        return list;
    }

    /**
     * 获取分组
     * @return
     */
    public static List<RosterGroup> getGroups() {
        if (connection == null) {
            return null;
        }
        List<RosterGroup> list = new ArrayList<RosterGroup>();
        Roster roster = Roster.getInstanceFor(connection);
        Collection<RosterGroup>  collection = roster.getGroups();
        for (RosterGroup rosterGroup : collection) {
            list.add(rosterGroup);
        }
        return list;
    }

    /**
     * 获取分组下面的好友
     * @param groupName
     * @return
     */
    public static List<RosterEntry> getEntriesByGroup(String groupName) {
        if (connection == null) {
            return null;
        }
        List<RosterEntry> list = new ArrayList<RosterEntry>();
        Roster roster = Roster.getInstanceFor(connection);
        RosterGroup rosterGroup = roster.getGroup(groupName);
        list = rosterGroup.getEntries();
        return list;
    }

    /**
     * 获取当前 用户VCard
     * @return
     */
    public static VCard getUserVCard() {
        if (connection == null) {
            return null;
        }
        VCardManager vCardManager = VCardManager.getInstanceFor(connection);
        try {
           VCard vCard = vCardManager.loadVCard();
            return vCard;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定用户的VCard
     * @param jId
     * @return
     */
    public static VCard getUserVCard(String jId) {
        if (connection == null) {
            return  null;
        }
        VCardManager vCardManager = VCardManager.getInstanceFor(connection);
        try {
            VCard vCard = vCardManager.loadVCard(jId);
            return  vCard;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建聊天
     * @param jid
     * @return
     */
    public static Chat crateChat(String jid) {
        if (connection == null) {
            return null;
        }
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        Chat chat = chatManager.createChat(jid);
        return chat;
    }

    /**
     *
     * @return
     */
    public static ChatManager getChatManager() {
        if (connection == null) {
            return null;
        }
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        return  chatManager;
    }


    /**
     * 获取离线消息
     * @return
     */
    public static OfflineMessageManager getOfflineManger() {
        if (connection == null) {
            return null;
        }
        OfflineMessageManager messageManager = new OfflineMessageManager(connection);
        return messageManager;
    }

}
