package com.kcb.common.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @className: BaseFragmentActivity
 * @description: extends this when define a new FragmentActivity
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:20:57
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {}

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


}
