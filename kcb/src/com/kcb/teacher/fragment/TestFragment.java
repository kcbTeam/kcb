package com.kcb.teacher.fragment;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcb.common.application.KApplication;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.MaterialListDialog.OnClickSureListener;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.activity.CheckTestActivity;
import com.kcb.teacher.activity.EditTestActivity;
import com.kcb.teacher.activity.SetTestNameActivity;
import com.kcb.teacher.model.test.Test;
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

    private List<Test> mTestList;
    private List<String> mTestNameList;

    private GetTestListTask mGetTestListTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_test, container, false);
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

    }

    @Override
    protected void initData() {}

    @Override
    public void onStart() {
        super.onStart();
        mGetTestListTask = new GetTestListTask();
        mGetTestListTask.execute();
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_begin_test:
                    startTest();
                    break;
                case R.id.button_edit_test:
                    addOrEditTest();
                    break;
                case R.id.button_test_result:
                    Intent intent = new Intent(getActivity(), CheckTestActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private void startTest() {
        initData();
        refreshNameList(INDEX_OF_SATRT_TEST);
        if (mTestNameList.isEmpty()) {
            ToastUtil.toast("测试题库空空如也，请先编辑测试！");
            return;
        }
        DialogUtil.showListDialog(getActivity(), "开始测试", mTestNameList, "确定",
                new OnClickSureListener() {

                    @Override
                    public void onClick(View view, int position) {
                        ToastUtil.toast("" + position);
                    }
                }, "取消", null);
    }

    private void addOrEditTest() {
        initData();
        refreshNameList(INDEX_OF_EDIT_TEST);
        DialogUtil.showListDialog(getActivity(), "编辑测试内容", mTestNameList, "确定",
                new OnClickSureListener() {

                    @Override
                    public void onClick(View view, int position) {
                        if (position == 0) { // add new test
                            Intent intent = new Intent(getActivity(), SetTestNameActivity.class);
                            startActivity(intent);
                        } else {
                            // TODO set selected testId
                            mTestList.get(mTestList.size() - position).changeTestToSerializable();
                            EditTestActivity.startEditTest(getActivity(),
                                    mTestList.get(mTestList.size() - position));
                        }
                    }
                }, "取消", null);

    }

    private class GetTestListTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                mTestList = KApplication.mTestDao.getAllRecord();
                mTestNameList = new ArrayList<String>();
                for (int i = 0; i < mTestList.size(); i++) {
                    mTestNameList.add(mTestList.get(i).getName());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private final int INDEX_OF_EDIT_TEST = 1;
    private final int INDEX_OF_SATRT_TEST = 2;

    private void refreshNameList(int INDEX) {
        if (null == mTestNameList) {
            return;
        }
        if (mTestNameList.contains("编辑新的测试")) {
            mTestNameList.remove("编辑新的测试");
        }
        if (INDEX == INDEX_OF_EDIT_TEST) {
            mTestNameList.add("编辑新的测试");
        }
    }


}
