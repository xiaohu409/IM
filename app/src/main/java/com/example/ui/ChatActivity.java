/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.ui;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.im.MessageUtil;
import com.example.im.OpenfireUtil;
import com.example.util.StringUtil;
import com.example.util.ToastUtil;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by hutao on 2015/8/20.
 */
public class ChatActivity extends AppCompatActivity {

    private Chat chat;


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
        String jId = intent.getStringExtra("jid");
        String nickName = intent.getStringExtra("nickname");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nickName);

        chat = OpenfireUtil.crateChat(jId);

    }

    private void initUI() {
        final TextView chatMsg = (TextView) findViewById(R.id.chat_msg);
        final EditText editMsg = (EditText) findViewById(R.id.edit_msg);
        Button sendMsg = (Button) findViewById(R.id.send_msg);
        sendMsg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editMsg.getText().toString();
                if (StringUtil.isNullOrEmpty(msg)) {
                    ToastUtil.showShort(ChatActivity.this, "消息不能为空");
                }
                else {
                    editMsg.getText().clear();
                    String packageMsg = MessageUtil.packageMsg("我", msg);
                    chatMsg.append(packageMsg);
                    try {
                        chat.sendMessage(msg);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        chat.addMessageListener(new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                ToastUtil.showShort(ChatActivity.this, message.getFrom() + ":" + message.getBody());
                chatMsg.append(message.getFrom() + ":" + message.getBody());
            }
        });


    }

}
