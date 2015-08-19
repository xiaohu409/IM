/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.administrator.im;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Intent;

import com.example.util.SharedPreUtil;
import com.example.util.StringUtil;

/**
 * Created by hutao on 2015/8/19.
 */

public class MainActivity extends AppCompatActivity {


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = SharedPreUtil.getSharedPreferences(this, Config.CONFIG_LOGIN.name());

    }

    @Override
    protected void onStart() {
        super.onStart();
        String username = preferences.getString(Login.USER_NAME, "");
        if (StringUtil.isNullOrEmpty(username)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }



}
