package com.kcb.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.activity.checkin.LookCheckInActivity;
import com.kcb.teacher.activity.checkin.StartCheckInActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: SignInFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:21:09
 */
public class CheckInFragment extends BaseFragment {

    private PaperButton startCheckInButton;
    private PaperButton lookCheckInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_checkin, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    @Override
    protected void initView() {
        View view = getView();
        startCheckInButton = (PaperButton) view.findViewById(R.id.button_start_checkin);
        startCheckInButton.setOnClickListener(mClickListener);
        lookCheckInButton = (PaperButton) view.findViewById(R.id.button_look_checkin);
        lookCheckInButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.button_start_checkin:
                    intent = new Intent(getActivity(), StartCheckInActivity.class);
                    break;
                case R.id.button_look_checkin:
                    intent = new Intent(getActivity(), LookCheckInActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mClickListener = null;
    };
}
