package com.kcb.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.StatusBarUtil;
import com.kcbTeam.R;

/**
 * 
 * @className: AboutusActivity
 * @description: 关于我们页面
 * @author: wanghang
 * @date: 2015-7-13 下午9:14:44
 */
public class AboutusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_activity_aboutus);

        initView();
    }

    @Override
    protected void initView() {
        int type = getIntent().getIntExtra(DATA_TYPE, 0);
        switch (type) {
            case TYPE_STU:
                StatusBarUtil.setStuStatusBarColor(AboutusActivity.this);
                break;
            case TYPE_TCH:
                StatusBarUtil.setTchStatusBarColor(AboutusActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {}

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
