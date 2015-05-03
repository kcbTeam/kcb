package com.kcb.student.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.library.view.PaperButton;
import com.kcb.student.activity.CheckInResultActivity;
import com.kcb.student.activity.CheckInActivity;
import com.kcbTeam.R;

/**
 * @className: SignFragment
 * @description: Sign in fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:01
 */
public class CheckInFragment extends BaseFragment {

    private PaperButton startCheckInButton;
    private PaperButton lookCheckInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        return inflater.inflate(R.layout.stu_fragment_sign, container, false);
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

    private CustomOnClickListener mClickListener = new CustomOnClickListener(
            CustomOnClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            Intent intent;
            if (v == startCheckInButton) {
                intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);
            } else if (v == lookCheckInButton) {
                intent = new Intent(getActivity(), CheckInResultActivity.class);
                startActivity(intent);
            }
        }
    };
}
