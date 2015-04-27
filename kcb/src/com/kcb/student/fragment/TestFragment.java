package com.kcb.student.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kcb.common.base.BaseFragment;
import com.kcbTeam.R;

/**
 * @className: TestFragment
 * @description: Test fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:44
 */
public class TestFragment extends BaseFragment {

    private Button buttonstart;
    private Button buttoncheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stu_fragment_test, container, false);
        buttonstart = (Button) getView().findViewById(R.id.startTest);
        buttoncheck = (Button) getView().findViewById(R.id.checkTest);
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
            case R.id.startTest:
                // TODO go to test
                break;
            case R.id.checkTest:
                // TODO go to check the result of test
                break;
            default:
                break;
        }
    }

}
