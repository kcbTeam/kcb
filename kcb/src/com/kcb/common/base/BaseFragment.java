package com.kcb.common.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @className: BaseFragment
 * @description: extend this fragment when define a new fragment;
 * @author: hang.wang
 * @date: 2015-4-20 上午11:29:29
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {}
}
