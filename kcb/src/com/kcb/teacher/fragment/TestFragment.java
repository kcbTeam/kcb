package com.kcb.teacher.fragment;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.MaterialListDialog.OnClickSureListener;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.activity.test.EditTestActivity;
import com.kcb.teacher.activity.test.LookTestActivity;
import com.kcb.teacher.activity.test.SetTestNameActivity;
import com.kcb.teacher.database.test.TestDao;
import com.kcbTeam.R;

/**
 * 
 * @className: TestFragment
 * @description:
 * @author: ZQJ & ljx
 * @date: 2015年4月24日 下午3:24:15
 */
public class TestFragment extends BaseFragment {

    private final String TAG = TestFragment.class.getName();

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
        RequestUtil.getInstance().cancelPendingRequests(TAG);
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
                    Intent intent = new Intent(getActivity(), LookTestActivity.class);
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
        } else {
            DialogUtil.showListDialog(getActivity(), "选择测试", mTestNameList, "确定",
                    new OnClickSureListener() {

                        @Override
                        public void onClick(View view, int position) {
                            sendTestToServer(mTestList.get(position));
                        }
                    }, "取消", null);
        }
    }

    private void sendTestToServer(final Test test) {
        OnClickListener sureListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject requestObject = new JSONObject();
                try {
                    requestObject.put("id", KAccount.getAccountId());
                    test.setQuestionId();
                    requestObject.put("test", test.toJsonObject());
                } catch (JSONException e) {}
                JsonObjectRequest request =
                        new JsonObjectRequest(Method.POST, UrlUtil.getTchTestStartUrl(),
                                requestObject, new Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ToastUtil.toast("成功");
                                    }
                                }, new ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ToastUtil.toast("失败");
                                    }
                                });
                RequestUtil.getInstance().addToRequestQueue(request, TAG);
            }
        };
        DialogUtil.showNormalDialog(getActivity(), R.string.starttest, "本次测试的名称为" + test.getName()
                + "，包括" + test.getQuestionNum() + "道题，" + "时间为" + test.getTime() + "分钟。",
                R.string.tch_comm_sure, sureListener, R.string.tch_comm_cancel, null);
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
