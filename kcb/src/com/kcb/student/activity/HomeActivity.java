package com.kcb.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: HomeActivity
 * @description: Login interface��one Textview,one ImageView,two editView��one Button
 * @author: Tao Li
 * @date: 2015-4-22 ����3:41:45
 */
// TODO
// 1, use utf-8 encode
// 2, delete some empty row
// 3, rename stuIDEditText to idEdittext
// 4, rename stuPassWordEditText to passwordEdittext
// 5, rename this HomeActivity to LoginActivity
// 6, rename student_activity_home to activity_stu_login
public class HomeActivity extends BaseActivity {

    private EditText stuIDEditText;
    private EditText stuPassWordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_home);
        initView();
    }

    @Override
    protected void initView() {
        stuIDEditText = (EditText) findViewById(R.id.edittext_stuid);
        stuPassWordEditText = (EditText) findViewById(R.id.edittext_password);
        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        // TODO
        // 1, need detect id and password null;
        // 2, need request server;
        // 3, if login success, goto HomeActivity from LoginActivity;
        // 4, need save login success result to local, then student needn't login next time;
        stuIDEditText.getText().toString();
        stuPassWordEditText.getText().toString();
        Intent intent = new Intent(HomeActivity.this, CheckinActivity.class);
        startActivity(intent);

    }

}
