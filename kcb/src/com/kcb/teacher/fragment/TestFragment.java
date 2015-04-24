package com.kcb.teacher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcb.common.base.BaseFragment;
import com.kcbTeam.R;

/**
 * 
 * @className: TestFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:15
 */
public class TestFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tch_fragment_course_test, container, false);
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}
}
