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
 * @description: course test fragment
 * @author: ZQJ
 * @date: 2015年4月22日 下午4:28:32
 */
public class TestFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.teacher_fragment_course_test, container, false);
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}
}
