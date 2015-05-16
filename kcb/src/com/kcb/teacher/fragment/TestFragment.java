package com.kcb.teacher.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.MaterialListDialog.OnClickSureListener;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.activity.CheckTestActivity;
import com.kcb.teacher.activity.EditTestActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: TestFragment
 * @description:
 * @author: ZQJ & ljx
 * @date: 2015年4月24日 下午3:24:15
 */
public class TestFragment extends BaseFragment {

    private PaperButton editButton;
    private PaperButton testButton;
    private PaperButton testresultButton;
    private TextView tipTextView;

    private ArrayList<String> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_course_test, container, false);
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
        editButton = (PaperButton) view.findViewById(R.id.button_edit_test);
        editButton.setOnClickListener(mClickListener);
        testButton = (PaperButton) view.findViewById(R.id.button_begin_test);
        testButton.setOnClickListener(mClickListener);
        testresultButton = (PaperButton) view.findViewById(R.id.button_test_result);
        testresultButton.setOnClickListener(mClickListener);

        tipTextView = (TextView) view.findViewById(R.id.textview_tip);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            // Intent intent;
            switch (v.getId()) {
                case R.id.button_edit_test:
                    // TODO get title from sharedPreference;
                    mList = new ArrayList<String>();
                    mList.add("新测试");
                    mList.add("第一次测试");
                    mList.add("第二次测试");
                    mList.add("第三次测试");

                    DialogUtil.showListDialog(getActivity(), "编辑测试内容", mList, "确定",
                            new OnClickSureListener() {

                                @Override
                                public void onClick(View view, int position) {
                                    if (position == 0) {
                                   
                                    	//Intent intent = new Intent(getActivity(), EditTestFirstActivity.class);
                                       // startActivity(intent);
                                        EditTestActivity.startAddTest(getActivity());
                                    } else {
                                        // TODO set selected testId
                                        EditTestActivity.startEditTest(getActivity(), "1");
                                    }
                                }
                            }, "取消", null);
                    break;
                case R.id.button_begin_test:
                    // TODO get title from sharedPreference;
                    // if null ,toast;
                    mList = new ArrayList<String>();
                    mList.add("第一次测试");
                    mList.add("第二次测试");
                    mList.add("第三次测试");
                    DialogUtil.showListDialog(getActivity(), "开始测试", mList, "确定",
                            new OnClickSureListener() {

                                @Override
                                public void onClick(View view, int position) {
                                    ToastUtil.toast("" + position);
                                }
                            }, "取消", null);
                    // intent = new Intent(getActivity(), TestActivity.class);
                    // startActivity(intent);
                    break;
                case R.id.button_test_result:
                    // Intent intent = new Intent(getActivity(), EditTestFirstActivity.class);
                    // startActivity(intent);
                    Intent intent = new Intent(getActivity(), CheckTestActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}
