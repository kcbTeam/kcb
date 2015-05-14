package com.kcb.common.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @className: BaseActivity
 * @description: extend this activity when define a new activity;
 * @author: wanghang
 * @date: 2015-4-20 上午11:14:44
 */
public abstract class BaseActivity extends Activity implements OnClickListener {

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {}
}
