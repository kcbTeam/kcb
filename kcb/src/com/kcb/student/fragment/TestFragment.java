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
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.activity.test.LookTestActivity;
import com.kcb.student.activity.test.TestActivity;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

/**
 * @className: TestFragment
 * @description: Test fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:44
 */
public class TestFragment extends BaseFragment {

    private static final String TAG = TestFragment.class.getName();

    private PaperButton startTestButton;
    private PaperButton lookTestResultButton;
    private SmoothProgressBar startProgressBar;

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
        startProgressBar = (SmoothProgressBar) view.findViewById(R.id.progressbar_start);

        lookTestResultButton = (PaperButton) view.findViewById(R.id.button_look_test);
        lookTestResultButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_start_test:
                    startTest();
                    break;
                case R.id.button_look_test:
                    lookTestResult();
                    break;
                default:
                    break;
            }
        }
    };

    private void startTest() {
        if (startProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        startProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request =
                new JsonObjectRequest(Method.GET, UrlUtil.getStuTestStartUrl(KAccount
                        .getAccountId()), new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO save test to database
                        Test test = Test.fromJsonObject(response);
                        TestActivity.start(getActivity(), test,20);
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        startProgressBar.hide(getActivity());
                        NetworkResponse response = error.networkResponse;
                        if (null != response && response.statusCode == 400) {
                            ToastUtil.toast("没有测试");
                        } else {
                            ResponseUtil.toastError(error);
                        }
                        // TODO for test
                        TestActivity.start(getActivity(), new Test(),20);
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
    }
}
