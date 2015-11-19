package com.kcb.student.fragment;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.activity.test.LookTestActivity;
import com.kcb.student.activity.test.StartTestActivity;
import com.kcb.student.database.test.TestDao;
import com.kcb.student.model.KAccount;
import com.kcbTeam.R;

/**
 * @className: TestFragment
 * @description: Test fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:44
 */
public class TestFragment extends BaseFragment {

    private static final String TAG = TestFragment.class.getName();

    private PaperButton startButton;
    private SmoothProgressBar startProgressBar;

    private PaperButton lookResultButton;

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
        startButton = (PaperButton) view.findViewById(R.id.button_start_test);
        startButton.setOnClickListener(mClickListener);
        startProgressBar = (SmoothProgressBar) view.findViewById(R.id.progressbar_start);

        lookResultButton = (PaperButton) view.findViewById(R.id.button_look_test);
        lookResultButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_start_test:
                    checkTest();
                    break;
                case R.id.button_look_test:
                    lookTestResult();
                    break;
                default:
                    break;
            }
        }
    };

    // 检查是否有正在进行的测试
    private void checkTest() {
        JsonObjectRequest request =
                new JsonObjectRequest(Method.POST, UrlUtil.getStuCheckTestUrl(KAccount
                        .getTchId()), new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        int duration = response.optInt("last_time");
                        long startTime = response.optLong("start_time");
                        if ((System.currentTimeMillis() - startTime) > duration) {
                            ToastUtil.toast(R.string.stu_no_test_now);
                        } else {
                            startTest(startTime);
                        }
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
        LogUtil.i(TAG, request.getUrl());
    }

    private final String KEY_REMAINTIME = "remaintime";
    private final String KEY_TEST = "test";


    // 开始答题
    private void startTest(final long startTime) {
        if (startProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        startProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request =
                new JsonObjectRequest(Method.GET, UrlUtil.getStuTestStartUrl(KAccount.getTchId(),
                        startTime), new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.i(TAG, "stu get test from server, response is " + response);

                        final int remaintime = (int) (System.currentTimeMillis() - startTime);
//                        final Test test = Test.fromJsonObject(response.optJSONObject(KEY_TEST));
//
//                        // 打乱测试中的题目和选项
//                        test.shuffle();
//                        test.decode();
//
//                        TestDao testDao = new TestDao(getActivity());
//                        testDao.add(test);
//                        testDao.close();
//
//                        // 进入开始答题页面
//                        StartTestActivity.start(getActivity(), test, remaintime);
                        startProgressBar.hide(getActivity());
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                startProgressBar.hide(getActivity());
                                NetworkResponse response = error.networkResponse;
                                if (null != response) {
                                    int statusCode = response.statusCode;
                                    if (statusCode == 400) {
                                        LogUtil.e(TAG, getString(R.string.stu_no_test_now));
                                        ToastUtil.toast(R.string.stu_no_test_now);
                                    } else if (statusCode == 401) {
                                        LogUtil.e(TAG, getString(R.string.stu_has_test));
                                        ToastUtil.toast(R.string.stu_has_test);
                                    }
                                } else {
                                    ResponseUtil.toastError(error);
                                }
                            }
                        });
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    private void lookTestResult() {
        Intent intent = new Intent(getActivity(), LookTestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        mClickListener = null;
    }
}
