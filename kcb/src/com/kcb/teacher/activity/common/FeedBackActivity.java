package com.kcb.teacher.activity.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.model.KAccount;
import com.kcb.teacher.util.SharedPreferenceUtil;
import com.kcbTeam.R;

/**
 * 
 * @className: FeedBackActivity
 * @description: 意见反馈界面
 * @author: wanghang
 * @date: 2015-7-10 下午4:19:52
 */
public class FeedBackActivity extends BaseActivity {

    private static final String TAG = FeedBackActivity.class.getName();

    private ButtonFlat backButton;
    private ButtonFlat finishButton;
    private SmoothProgressBar progressBar;
    private EditText feedbackEditText;

    private boolean submitSuccess; // 如果提交失败，然后返回了，将内容保存；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_feedback);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);

        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_finish);

        feedbackEditText = (EditText) findViewById(R.id.edittext_feedback);
    }

    /**
     * 退出activity的时候，如果内容没有提交，保存到本地，下次打开activity的时候，直接显示出来；
     */
    @Override
    protected void initData() {
        feedbackEditText.setText(SharedPreferenceUtil.getFeedbackContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            case R.id.button_finish:
                submitFeedback();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!submitSuccess) { // 如果未提交成功，保存内容到本地；
            String feedback = feedbackEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(feedback)) {
                SharedPreferenceUtil.setFeedbackContent(feedback);
            }
        } else { // 如果提交成功，清空数据；
            SharedPreferenceUtil.setFeedbackContent("");
        }
        super.onBackPressed();
    }

    private void submitFeedback() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request =
                new StringRequest(Method.POST, UrlUtil.getTchFeedbackUrl(KAccount.getAccountId(),
                        com.kcb.teacher.model.KAccount.getAccountName()), new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.toast(R.string.tch_feedback_success);
                        progressBar.hide(FeedBackActivity.this);
                        submitSuccess = true;
                        onBackPressed();
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(FeedBackActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }
}
