/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.im.OpenfireUtil;
import com.example.util.LogUtil;
import com.example.util.SharedPreUtil;
import com.example.util.StringUtil;
import com.example.util.ToastUtil;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by hutao on 2015/8/19.
 */

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String username;
    private String password;

    private ExpandableListView listView;
    private TextView currentUser;
    private TextView printLogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initConfig();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            new ConnectionAsyncTask().execute();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                OpenfireUtil.closeConnection();
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     *初始化UI
     */
    private void initUI() {
        currentUser = (TextView) findViewById(R.id.current_username);
        listView = (ExpandableListView) findViewById(R.id.list_friend);
        printLogView = (TextView) findViewById(R.id.print_log);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        preferences = SharedPreUtil.getSharedPreferences(this, Config.CONFIG_LOGIN.name());
        username = preferences.getString(LoginActivity.USER_NAME, "");
        password = preferences.getString(LoginActivity.PASSWORD, "");
    }


    /**
     * 检测是否连接服务器的 异步类
     */
    class ConnectionAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... parm) {
            if (!OpenfireUtil.isConnect()) {
                OpenfireUtil.connectionServer();
                OpenfireUtil.login(username, password);
            }
            //阻塞线程1s 让程序有时间去 获取好友信息
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //显示昵称
            VCard vCard = OpenfireUtil.getUserVCard();
            if (vCard == null) {
                return;
            }
            currentUser.setText(vCard.getTo());
            printLogView.append(LogUtil.Log(vCard.getTo()));
            //记载分组及好友列表
            List<RosterGroup> groupList = OpenfireUtil.getGroups();
            if (groupList == null) {
                return;
            }
            final List<List<RosterEntry>> friendList = new ArrayList<List<RosterEntry>>();
            for (RosterGroup rosterGroup : groupList) {
                List<RosterEntry> list = OpenfireUtil.getEntriesByGroup(rosterGroup.getName());
                friendList.add(list);
            }
            final FriendListAdapter friendListAdapter = new FriendListAdapter(MainActivity.this, groupList, friendList);
            listView.setAdapter(friendListAdapter);
            listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    RosterEntry rosterEntry = friendListAdapter.getChild(groupPosition, childPosition);
                    String nickName = rosterEntry.getName();
                    ToastUtil.showShort(MainActivity.this, nickName);

                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    intent.putExtra("nickname", nickName);
                    intent.putExtra("jid", rosterEntry.getUser());
                    startActivity(intent);
                    return false;
                }
            });

            //输出日志
            printLogView.append(LogUtil.Log("好友:" + Arrays.toString(friendList.toArray())));
            printLogView.append(LogUtil.Log("分组:" + Arrays.toString(groupList.toArray())));

        }

    }
}
