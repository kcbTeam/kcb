package com.kcb.teacher.activity.common;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.model.account.KAccount;
import com.kcbTeam.R;

public class ModifyPasswordActivity extends BaseActivity {

    private final String TAG = ModifyPasswordActivity.class.getName();

    private ButtonFlat backButton;

    private FloatingEditText oldPasswordEditText;
    private PaperButton nextButton;
    private SmoothProgressBar nextProgressBar;

    private View newPasswordLayout;
    private FloatingEditText newPasswordEditText;
    private FloatingEditText repeatPassWordEditText;
    private PaperButton finishButton;
    private SmoothProgressBar finishProgressBar;

    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_modify_password);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        oldPasswordEditText = (FloatingEditText) findViewById(R.id.edittext_oldpassword);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        nextButton.setOnClickListener(mClickListener);
        nextProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_next);

        newPasswordLayout = findViewById(R.id.layout_newpassword);
        newPasswordEditText = (FloatingEditText) findViewById(R.id.edittext_newpassword);
        repeatPassWordEditText = (FloatingEditText) findViewById(R.id.edittext_repeatpassword);
        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
        finishProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_finish);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
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
                case R.id.button_next:
                    if (oldPasswordEditText.isEnabled()) {
                        checkOldPassword();
                    }
                    break;
                case R.id.button_finish:
                    setNewPassword();
                    break;
            }
        }
    };

    private void checkOldPassword() {
        final String oldPassword = oldPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            AnimationUtil.shake(oldPasswordEditText);
        } else {
            if (nextProgressBar.getVisibility() == View.VISIBLE) {
                return;
            }
            nextProgressBar.setVisibility(View.VISIBLE);
            StringRequest request =
                    new StringRequest(Method.POST, UrlUtil.getTchCheckOldPasswordUrl(
                            KAccount.getAccountId(), oldPassword), new Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    oldPasswordEditText.setEnabled(false);
                                    nextProgressBar.setVisibility(View.GONE);
                                    newPasswordLayout.setVisibility(View.VISIBLE);
                                    AnimationUtil.fadeIn(newPasswordLayout);
                                    newPasswordEditText.requestFocus();
                                }
                            }, 500);
                        }
                    }, new ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            nextProgressBar.hide(ModifyPasswordActivity.this);
                            if (null != error.networkResponse
                                    && error.networkResponse.statusCode == 400) {
                                ToastUtil.toast(R.string.tch_password_error);
                            } else {
                                ResponseUtil.toastError(error);
                            }
                        }
                    });
            RequestUtil.getInstance().addToRequestQueue(request, TAG);
        }
    }

    private void setNewPassword() {
        String password = newPasswordEditText.getText().toString();
        String repeatPassword = repeatPassWordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            AnimationUtil.shake(newPasswordEditText);
            newPasswordEditText.requestFocus();
        } else if (TextUtils.isEmpty(repeatPassword)) {
            AnimationUtil.shake(repeatPassWordEditText);
            repeatPassWordEditText.requestFocus();
        } else if (!password.equals(repeatPassword)) {
            ToastUtil.toast(R.string.tch_twice_password_unequal);
        } else {
            if (finishProgressBar.getVisibility() == View.VISIBLE) {
                return;
            }
            finishProgressBar.setVisibility(View.VISIBLE);
            newPassword = password;
            StringRequest request =
                    new StringRequest(Method.POST, UrlUtil.getTchModifyPasswordUrl(
                            KAccount.getAccountId(), newPassword), new Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    ToastUtil.toast(R.string.tch_modify_success);
                                    finish();
                                }
                            }, 500);
                        }
                    }, new ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            finishProgressBar.hide(ModifyPasswordActivity.this);
                            ResponseUtil.toastError(error);
                        }
                    });
            RequestUtil.getInstance().addToRequestQueue(request, TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        mClickListener = null;
    }
}
