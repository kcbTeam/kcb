package com.kcb.student.activity.common;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.kcb.student.model.KAccount;
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

    private ButtonFlat backButton;

    private FloatingEditText idEditText;
    private PasswordEditText passwordEditText;
    private PaperButton loginButton;
    private SmoothProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_login);
        StatusBarUtil.setStuStatusBarColor(this);

        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        backButton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));

        idEditText = (FloatingEditText) findViewById(R.id.edittext_id);
        idEditText.setHighlightedColor(getResources().getColor(R.color.stu_primary));

        passwordEditText = (PasswordEditText) findViewById(R.id.passwordedittext);
        passwordEditText.setHint(R.string.stu_password);

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

    private final String KEY_DATA = "data";
    private final String KEY_STUNAME = "stuname";
    private final String KEY_TCHID = "tchid";
    private final String KEY_TCHNAME = "tchname";

    /**
     * 登录；
     */
    private void login() {
        // 取出id中的空格
        final String stuId = idEditText.getText().toString().trim().replace(" ", "");
        // 不需要取密码中的空格，密码可以包括空格
        final String password = passwordEditText.getText();
        if (TextUtils.isEmpty(stuId)) {
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
            JsonObjectRequest request =
                    new JsonObjectRequest(Method.POST, UrlUtil.getStuLoginUrl(stuId, password),
                            new Listener<JSONObject>() {
                                public void onResponse(final JSONObject response) {
                                    LogUtil.i(TAG, "login success response: " + response.toString());
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject data = response.getJSONObject(KEY_DATA);
                                                String stuName = data.optString(KEY_STUNAME);
                                                String tchId = data.optString(KEY_TCHID);
                                                String tchName = data.optString(KEY_TCHNAME);
                                                // save account
                                                KAccount account =
                                                        new KAccount(stuId, stuName, tchId, tchName);
                                                KAccount.saveAccount(account);
                                                AccountUtil.setAccountType(AccountUtil.TYPE_STU);
                                                // goto HomeActivity
                                                HomeActivity.start(LoginActivity.this);
                                                finish();
                                            } catch (JSONException e) {}
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
                                            ToastUtil.toast(R.string.stu_id_error);
                                            LogUtil.e(TAG, getString(R.string.stu_id_error));
                                        } else if (statusCode == 401) {
                                            ToastUtil.toast(R.string.stu_password_error);
                                            LogUtil.e(TAG, getString(R.string.stu_password_error));
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
        StartActivity.restart(LoginActivity.this);
        finish();
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
