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
 * @description: Login interface£¬one Textview,one ImageView,two editView£¬one Button
 * @author: Tao Li
 * @date: 2015-4-22 ÏÂÎç3:41:45
 */
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
        stuIDEditText.getText().toString();
        stuPassWordEditText.getText().toString();
        Intent intent = new Intent(HomeActivity.this, CheckinActivity.class);
        startActivity(intent);

    }

}
