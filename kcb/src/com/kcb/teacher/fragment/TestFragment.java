package com.kcb.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kcb.common.base.BaseFragment;
import com.kcb.teacher.activity.TestActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: TestFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:15
 */
public class TestFragment extends BaseFragment {

    private Button testButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_course_test, container, false);
        testButton = (Button) view.findViewById(R.id.button_begin_test);
        testButton.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_begin_test:
                intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
