package com.kcb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: HomeActivity
 * @description: 
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:21:48
 */

//TODO delete this activity if not use

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//         just for test , while the user name and password is correct, then go to HomePageActivity
         Intent intent = new Intent(HomeActivity.this, HomePageActivity.class);
         startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}
}
