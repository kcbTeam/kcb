package com.kcb.student.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kcb.common.base.BaseFragment;
import com.kcb.student.activity.CheckInResultActivity;
import com.kcb.student.activity.CheckinActivity;
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
        return inflater.inflate(R.layout.stu_fragment_sign, container, false);
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
    protected void initView() {}

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.startSign:
                intent = new Intent(getActivity(), CheckinActivity.class);
                startActivity(intent);
                break;
            case R.id.checkSign:
                intent = new Intent(getActivity(), CheckInResultActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
