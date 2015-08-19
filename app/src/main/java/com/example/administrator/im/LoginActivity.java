/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.administrator.im;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.content.SharedPreferences;

import com.example.util.SharedPreUtil;
import com.example.util.StringUtil;
import com.example.util.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    private  SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        初始化UI

     */
    private void init() {
        final EditText usernameEdt = (EditText) findViewById(R.id.username);
        final EditText passwordEdt = (EditText) findViewById(R.id.password);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)) {
                    ToastUtil.showShort(LoginActivity.this, "用户名或密码不能为空！");
                    return;
                }
                new LoginAsyncTask().execute(username, password);
            }
        });

    }

    /*
        登录的异步类

     */
    class LoginAsyncTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... parm) {
            Boolean result = false;
            OpenfireUtil.connectionServer();
            result = OpenfireUtil.login(parm[0],parm[1]);
            if (result) {
                preferences = SharedPreUtil.getSharedPreferences(LoginActivity.this, Config.CONFIG_LOGIN.name());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Login.USER_NAME, parm[1]);
                editor.commit();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                ToastUtil.showShort(LoginActivity.this, "用户名或密码错误");
            }
            else {
                ToastUtil.showShort(LoginActivity.this, "登录成功");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }
    }

}


