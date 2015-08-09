package com.kcb.teacher.activity.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.activity.StartActivity;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AccountUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.common.PasswordEditText;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.model.KAccount;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午8:36:47
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getName();

    private ButtonFlat backButton;

    private FloatingEditText idEditText;
    private PasswordEditText passwordEditText;
    private PaperButton loginButton;
    private SmoothProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_login);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        idEditText = (FloatingEditText) findViewById(R.id.edittext_id);
        idEditText.setHighlightedColor(getResources().getColor(R.color.blue));

        passwordEditText = (PasswordEditText) findViewById(R.id.passwordedittext);
        passwordEditText.setHint(R.string.tch_password);

        loginButton = (PaperButton) findViewById(R.id.button_login);
        loginButton.setOnClickListener(mClickListener);
        loginProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_login);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_login:
                    login();
                    break;
                default:
                    break;
            }
        }
    };

    private void login() {
        final String id = idEditText.getText().toString().trim().replace(" ", "");
        final String password = passwordEditText.getText();
        if (TextUtils.isEmpty(id)) {
            idEditText.requestFocus();
            AnimationUtil.shake(idEditText);
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.requestFocus();
            AnimationUtil.shake(passwordEditText);
        } else {
            if (loginProgressBar.getVisibility() == View.VISIBLE) {
                return;
            }
            loginProgressBar.setVisibility(View.VISIBLE);
            StringRequest request =
                    new StringRequest(Method.POST, UrlUtil.getTchLoginUrl(id, password),
                            new Listener<String>() {
                                public void onResponse(final String response) {
                                    LogUtil.i(TAG, "tch login response is " + response);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            KAccount account = new KAccount(id, response);
                                            KAccount.saveAccount(account);

                                            AccountUtil.setAccountType(AccountUtil.TYPE_TCH);

                                            HomeActivity.start(LoginActivity.this);
                                            finish();
                                        }
                                    }, 500);
                                };
                            }, new ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    loginProgressBar.hide(LoginActivity.this);
                                    NetworkResponse networkResponse = error.networkResponse;
                                    if (null != networkResponse) {
                                        int statusCode = networkResponse.statusCode;
                                        if (statusCode == 400) {
                                            LogUtil.e(TAG, getString(R.string.tch_id_error));
                                            ToastUtil.toast(R.string.tch_id_error);
                                        } else if (statusCode == 401) {
                                            LogUtil.e(TAG, getString(R.string.tch_password_error));
                                            ToastUtil.toast(R.string.tch_password_error);
                                        } else {
                                            ResponseUtil.toastError(error);
                                        }
                                    } else {
                                        ResponseUtil.toastError(error);
                                    }
                                };
                            });
            RequestUtil.getInstance().addToRequestQueue(request, TAG);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        StartActivity.restart(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        mClickListener = null;
    }

    /**
     * 
     * @title: start
     * @description: if delete account in HomeActivity, need show this Activity;
     * @author: wanghang
     * @date: 2015-5-10 上午11:13:50
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
