package com.kcb.common.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @className: BaseFragmentActivity
 * @description: extends FragmentActivity
 * @author: ZQJ
 * @date: 2015年4月22日 上午11:11:11
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {
    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {}
}
