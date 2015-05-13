package com.kcb.student.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.library.view.PaperButton;
import com.kcb.student.activity.TestActivity;
import com.kcb.student.activity.TestResultActivity;
import com.kcbTeam.R;

/**
 * @className: TestFragment
 * @description: Test fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:44
 */
public class TestFragment extends BaseFragment {

    private PaperButton startTestButton;
    private PaperButton lookTestButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stu_fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    @Override
    protected void initView() {
        View view = getView();
        startTestButton = (PaperButton) view.findViewById(R.id.button_start_test);
        startTestButton.setOnClickListener(mClickListener);
        lookTestButton = (PaperButton) view.findViewById(R.id.button_look_test);
        lookTestButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            Intent intent;
            if (v == startTestButton) {
                intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            } else if (v == lookTestButton) {
                intent = new Intent(getActivity(), TestResultActivity.class);
                startActivity(intent);
            }
        }
    };
}
