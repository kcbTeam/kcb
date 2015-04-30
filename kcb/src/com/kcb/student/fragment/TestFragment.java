package com.kcb.student.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.library.view.PaperButton;
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

    private CustomOnClickListener mClickListener = new CustomOnClickListener(
            CustomOnClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            if (v == startTestButton) {

            } else if (v == lookTestButton) {

            }
        }
    };
}
