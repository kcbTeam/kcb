package com.kcb.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description: Login interface,one Textview,one ImageView,two editView,one Button
 * @author: Tao Li
 * @date: 2015-4-24 下午9:17:01
 */

public class LoginActivity extends BaseActivity {

    private EditText idEditText;
    private EditText passWordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_home);
        initView();
    }

    @Override
    protected void initView() {
        idEditText = (EditText) findViewById(R.id.edittext_stuid);
        passWordEditText = (EditText) findViewById(R.id.edittext_password);
        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(idEditText.getText())
                & !TextUtils.isEmpty(passWordEditText.getText())) {
            // if(need request server;){
            // SharedPreferences sharedPref = getSharedPreferences("AppLoginData",
            // MODE_PRIVATE);
            // Editor mEditor = sharedPref.edit();
            // mEditor.putString("UserId", stuIDEditText.getText().toString());
            // mEditor.putString("UserPassword", stuPassWordEditText.getText().toString());
            // mEditor.commit();}
            Intent intent = new Intent(LoginActivity.this, CheckinActivity.class);
            startActivity(intent);
        } else {}
    }

}
