/**
 * Copyright© 2011-2014 DewMobile USA Inc.All Rights Reserved.
 * 
 * @Title: SignFragment.java
 * @Package com.kcb.common.base
 * @Description:Sign in fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:01
 * @version V1.0
 */

package com.kcb.student.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kcb.common.base.BaseFragment;
import com.kcbTeam.R;

/**
 * @className: SignFragment
 * @description: Sign in fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:01
 */
public class SignFragment extends BaseFragment {

    private Button buttonstart;
    private Button buttoncheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        return inflater.inflate(R.layout.student_fragment_sign, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonstart = (Button) getView().findViewById(R.id.startSign);
        buttoncheck = (Button) getView().findViewById(R.id.checkSign);

        buttonstart.setOnClickListener(this);
        buttoncheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startSign:
                // TODO
                break;
            case R.id.checkSign:
                // TODO
                break;
            default:
                break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kcb.common.base.BaseFragment#initView()
     */
    @Override
    protected void initView() {}

    /*
     * (non-Javadoc)
     * 
     * @see com.kcb.common.base.BaseFragment#initData()
     */
    @Override
    protected void initData() {}
}
