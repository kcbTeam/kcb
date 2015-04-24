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
        View view=inflater.inflate(R.layout.stu_fragment_sign, container, false);
        buttonstart = (Button) getView().findViewById(R.id.startSign);
        buttoncheck = (Button) getView().findViewById(R.id.checkSign);
        buttonstart.setOnClickListener(this);
        buttoncheck.setOnClickListener(this);
        return view;
    }
    
    @Override
    protected void initView() {}

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startSign:
                // TODO go to sign in
                break;
            case R.id.checkSign:
                // TODO go to check the result of sign in
                break;
            default:
                break;
        }
    }
 
}
