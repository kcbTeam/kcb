package com.kcb.student.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kcb.teacher.activity.SignActivity;
import com.kcbTeam.R;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent=new Intent(HomeActivity.this,SignActivity.class);
        startActivity(intent);
    }
}
