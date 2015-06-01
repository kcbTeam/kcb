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

import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.MaterialListDialog.OnClickSureListener;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.activity.CheckTestActivity;
import com.kcb.teacher.activity.EditTestActivity;
import com.kcb.teacher.activity.SetTestNameActivity;
import com.kcb.teacher.database.test.TestDao;
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

    private PaperButton startTestButton;
    private PaperButton addOrEditTestButton;
    private PaperButton lookTestResultButton;

    private List<Test> mTestList;
    private List<String> mTestNameList;

    private GetTestListTask mGetTestListTask;

    private TestDao mTestDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_test, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        View view = getView();
        addOrEditTestButton = (PaperButton) view.findViewById(R.id.button_edit_test);
        addOrEditTestButton.setOnClickListener(mClickListener);
        startTestButton = (PaperButton) view.findViewById(R.id.button_begin_test);
        startTestButton.setOnClickListener(mClickListener);
        lookTestResultButton = (PaperButton) view.findViewById(R.id.button_test_result);
        lookTestResultButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {
        mTestDao = new TestDao(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetTestListTask = new GetTestListTask();
        mGetTestListTask.execute(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTestDao.close();
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
        refreshNameList(TAG_ADD_OR_EDIT_TEST);
        if (mTestNameList.isEmpty()) {
            ToastUtil.toast("请先添加测试内容");
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
        refreshNameList(TAG_START_TEST);
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
                mTestList = mTestDao.getAllRecord();
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

    private final int TAG_START_TEST = 1;
    private final int TAG_ADD_OR_EDIT_TEST = 2;

    private void refreshNameList(int INDEX) {
        if (null == mTestNameList) {
            return;
        }
        if (mTestNameList.contains("添加新测试")) {
            mTestNameList.remove("添加新测试");
        }
        if (INDEX == TAG_START_TEST) {
            mTestNameList.add("添加新测试");
        }
    }
}
