package com.kcb.student.fragment;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.activity.HomeActivity;
import com.kcb.student.activity.LoginActivity;
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
    private static final String TAG = TestFragment.class.getName();
    private PaperButton startTestButton;
    private PaperButton lookTestButton;
    private SmoothProgressBar loginProgressBar;

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
        loginProgressBar = (SmoothProgressBar) view.findViewById(R.id.progressbar_begintest);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            Intent intent;
            if (v == startTestButton) {
                if (loginProgressBar.getVisibility() == View.VISIBLE) {
                    return;
                }
                loginProgressBar.setVisibility(View.VISIBLE);
                String url = "http://m.weather.com.cn/data/101201401.html";
                JsonObjectRequest objRequest =
                        new JsonObjectRequest(Method.GET, url,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject obj) {
                                        loginProgressBar.hide(getActivity());
                                        ToastUtil.toast("成功获取天气网站的Json信息");
                                        Intent intent =
                                                new Intent(getActivity(), TestActivity.class);
                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        loginProgressBar.hide(getActivity());
                                        error.getMessage();
                                    }
                                }) {
                            @Override
                            public com.android.volley.Request.Priority getPriority() {
                                return Priority.HIGH;
                            }
                        };
                RequestUtil.getInstance().addToRequestQueue(objRequest, TAG);
            } else if (v == lookTestButton) {
                intent = new Intent(getActivity(), TestResultActivity.class);
                startActivity(intent);
            }
        }
    };
}
