package com.kcb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午8:36:47
 */
public class LoginActivity extends BaseActivity {

    private FloatingEditText idEditText;
    private FloatingEditText passwordEditText;
    private PaperButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_login);
        // for test ,by zqj;
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void initView() {
        idEditText = (FloatingEditText) findViewById(R.id.edittext_id);
        passwordEditText = (FloatingEditText) findViewById(R.id.edittext_password);
        loginButton = (PaperButton) findViewById(R.id.buton_login);
        loginButton.setOnClickListener(new CustomOnClickListener() {

            @Override
            public void doClick(View v) {
                String id = idEditText.getText().toString();
                String passwoString = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(id)) {

                } else if (TextUtils.isEmpty(passwoString)) {

                } else {

                }
            }
        });
    }

    @Override
    protected void initData() {}
}
