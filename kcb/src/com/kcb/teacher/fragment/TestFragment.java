package com.kcb.teacher.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.activity.CheckTest;
import com.kcb.teacher.adapter.ListAdapterEdit;
import com.kcbTeam.R;

/**
 * 
 * @className: TestFragment
 * @description:
 * @author: ZQJ & ljx
 * @date: 2015年4月24日 下午3:24:15
 */
public class TestFragment extends BaseFragment {

    private PaperButton testButton;
    private PaperButton editButton;
    private PaperButton testresultButton;
    private TextView tipTextView;

    private ArrayList<String> mList;

    private ListAdapterEdit mAdapter;

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
        testButton = (PaperButton) view.findViewById(R.id.button_begin_test);
        testresultButton = (PaperButton) view.findViewById(R.id.button_test_result);
        editButton.setOnClickListener(mClickListener);
        testButton.setOnClickListener(mClickListener);
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
                    mList = new ArrayList<String>();
                    mList.add("新测试");
                    mList.add("第一次测试");
                    mList.add("第二次测试");
                    mList.add("第三次测试");
                    mAdapter = new ListAdapterEdit(getActivity(), mList);

                    DialogUtil.showListDialog(getActivity(), "编辑测试内容", mAdapter, "确定",
                            new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    ToastUtil.toast("click sure");
                                }
                            }, "取消", null);

                    break;
                case R.id.button_begin_test:
                    mList = new ArrayList<String>();
                    mList.add("第一次测试");
                    mList.add("第二次测试");
                    mList.add("第三次测试");
                    mAdapter = new ListAdapterEdit(getActivity(), mList);

                    DialogUtil.showListDialog(getActivity(), "开始测试", mAdapter, "确定",
                            new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    ToastUtil.toast("click sure");
                                    // testButton.setVisibility(View.GONE);

                                    tipTextView.setVisibility(View.VISIBLE);

                                }
                            }, "取消", null);

                    // intent = new Intent(getActivity(), TestActivity.class);
                    // startActivity(intent);
                    break;

                case R.id.button_test_result:
                    Intent intent = new Intent(getActivity(), CheckTest.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }

    };
}
