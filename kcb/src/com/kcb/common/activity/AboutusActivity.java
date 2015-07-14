package com.kcb.common.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: AboutusActivity
 * @description: 关于我们页面
 * @author: wanghang
 * @date: 2015-7-13 下午9:14:44
 */
public class AboutusActivity extends BaseActivity {

    private View titleLayout;
    private ButtonFlat backButton;

    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_activity_aboutus);

        initView();
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.layout_title);
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        // 显示版本信息
        versionTextView = (TextView) findViewById(R.id.textview_version);
        try {
            PackageManager manager = getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            versionTextView.setText(getString(R.string.app_name) + " V" + packageInfo.versionName);
        } catch (NameNotFoundException e) {}

        // 根据学生/老师设置按钮、标题栏颜色
        int type = getIntent().getIntExtra(DATA_TYPE, 0);
        switch (type) {
            case TYPE_STU:
                backButton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));
                StatusBarUtil.setStuStatusBarColor(AboutusActivity.this);
                break;
            case TYPE_TCH:
                titleLayout.setBackgroundColor(getResources().getColor(R.color.tch_primary));
                StatusBarUtil.setTchStatusBarColor(AboutusActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;

            default:
                break;
        }
    }

    private static final int TYPE_STU = 0;
    private static final int TYPE_TCH = 1;
    private static final String DATA_TYPE = "data_type";

    public static void startStu(Context context) {
        Intent intent = new Intent(context, AboutusActivity.class);
        intent.putExtra(DATA_TYPE, TYPE_STU);
        context.startActivity(intent);
    }

    public static void startTch(Context context) {
        Intent intent = new Intent(context, AboutusActivity.class);
        intent.putExtra(DATA_TYPE, TYPE_TCH);
        context.startActivity(intent);
    }
}
