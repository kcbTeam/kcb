package com.kcb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午8:36:47
 */
public class LoginActivity extends BaseActivity {

    // TODO
    // 1, init view;
    // 2, detect input null;
    // 3, save login info to local (use sharedpreference)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_register);
        // for test ,by zqj;
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void initData() {}


    @Override
    protected void initView() {}
}
