/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.ui;

import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.im.MessageUtil;
import com.example.im.OpenfireUtil;
import com.example.util.DebugUtil;
import com.example.util.StringUtil;
import com.example.util.ToastUtil;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.util.List;

/**
 * Created by hutao on 2015/8/20.
 */
public class ChatActivity extends AppCompatActivity {

    private String nickName;

    private Chat chat;

    //必须为静态的 不知道原因
    private static TextView chatMsg;

    private ChatManager chatManager;

    private ChatManagerListener chatManagerListener;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {
            super.handleMessage(message);
            String msg = message.getData().getString("msg");
            chatMsg.append(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initConfig();
        initUI();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickname");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nickName);

        String jId = intent.getStringExtra("jid");
        chat = OpenfireUtil.crateChat(jId);

    }

    private void initUI() {
        chatMsg = (TextView) findViewById(R.id.chat_msg);
        chatMsg.setMovementMethod(new ScrollingMovementMethod());
        final EditText editMsg = (EditText) findViewById(R.id.edit_msg);
        Button sendMsg = (Button) findViewById(R.id.send_msg);
        sendMsg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editMsg.getText().toString();
                if (StringUtil.isNullOrEmpty(msg)) {
                    ToastUtil.showShort(ChatActivity.this, "消息不能为空");
                } else {
                    editMsg.getText().clear();
                    String packageMsg = MessageUtil.packageMsg("我", msg);
                    chatMsg.append(packageMsg);
                    try {
                        chat.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        chatManager =  OpenfireUtil.getChatManager();
        crateMessageListener();
        chatManager.addChatListener(chatManagerListener);
    }




    /**
     * 消息监听器 用于接听消息 并通过handler 进行转发
     * 为什么需要转发 而不是直接显示textview上面
     * 原因是方法层次太深了 不允许操作UI
     *
     */
    private void crateMessageListener() {
        chatManagerListener = new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean b) {
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        if (message.getBody() != null) {
                           // DebugUtil.logD("消息", message.getFrom() + ":" + message.getBody());
                            String msg = MessageUtil.packageMsg(nickName, message.getBody());
                            android.os.Message ms = new android.os.Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", msg);
                            ms.setData(bundle);
                            handler.sendMessage(ms);
                        }
                    }
                });
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chatManager.removeChatListener(chatManagerListener);
    }
}
