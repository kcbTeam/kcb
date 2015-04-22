package com.kcb.teacher.fragment;

import com.kcb.common.base.BaseFragment;
import com.kcbTeam.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_course_test, container, false);
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}
}
