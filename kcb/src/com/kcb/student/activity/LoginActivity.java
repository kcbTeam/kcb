package com.kcb.student.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.activity.StartActivity;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description: Login interface,one Textview,one ImageView,two editView,one Button
 * @author: Tao Li
 * @date: 2015-4-24 下午9:17:01
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getName();

    private FloatingEditText idEditText;
    private FloatingEditText passwordEditText;
    private PaperButton loginButton;
    private SmoothProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_login);

        initView();
    }

    @Override
    protected void initView() {
        idEditText = (FloatingEditText) findViewById(R.id.edittext_id);
        passwordEditText = (FloatingEditText) findViewById(R.id.edittext_password);
        loginButton = (PaperButton) findViewById(R.id.button_login);
        loginButton.setOnClickListener(mClickListener);
        loginProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_login);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            final String id = idEditText.getText().toString().trim().replace(" ", "");
            final String password = passwordEditText.getText().toString();
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
                        new StringRequest(Method.POST, UrlUtil.getStuLoginUrl(id, password),
                                new Listener<String>() {
                                    public void onResponse(final String response) {
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                KAccount account =
                                                        new KAccount(KAccount.TYPE_STU, id,
                                                                response);
                                                KAccount.saveAccount(account);
                                                HomeActivity.start(LoginActivity.this);
                                                finish();
                                            }
                                        }, 500);
                                    };
                                }, new ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        loginProgressBar.hide(LoginActivity.this);
                                        if (null != error.networkResponse
                                                && error.networkResponse.statusCode == 400) {
                                            ToastUtil.toast(R.string.id_password_error);
                                        } else {
                                            ResponseUtil.toastError(error);
                                        }
                                    };
                                });
                RequestUtil.getInstance().addToRequestQueue(request, TAG);

            }
        }
    };

    @Override
    public void onBackPressed() {
        StartActivity.restart(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
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
