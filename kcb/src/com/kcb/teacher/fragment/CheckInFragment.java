package com.kcb.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kcb.common.base.BaseFragment;
import com.kcb.teacher.activity.CheckInResultActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: SignInFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:21:09
 */
public class CheckInFragment extends BaseFragment {

    private Button startSign;
    private Button checkSignResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_checkin, container, false);
        startSign = (Button) view.findViewById(R.id.button_begin_signin);
        checkSignResult = (Button) view.findViewById(R.id.button_signin_result);
        startSign.setOnClickListener(this);
        checkSignResult.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_begin_signin:
                // TODO go to begin sign activity
                break;
            case R.id.button_signin_result:
                Intent intent = new Intent(getActivity(), CheckInResultActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
