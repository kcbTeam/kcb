package com.kcb.student.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcbTeam.R;

public class ModifyPasswordActivity extends BaseActivity {

    private static final String TAG = ModifyPasswordActivity.class.getName();

    private ButtonFlat backButton;
    private FloatingEditText userpasswordEditText;
    private PaperButton nextButton;
    private PaperButton completeButton;
    private FloatingEditText newPassWord;
    private FloatingEditText repeatNewPassWord;
    private RelativeLayout mLayout;
    private String newpassword;
    private SmoothProgressBar nextProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_modifypassword);

        initView();
    }

    @Override
    protected void initView() {

        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        userpasswordEditText = (FloatingEditText) findViewById(R.id.edittext_oldpassword);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        nextProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_next);

        mLayout = (RelativeLayout) findViewById(R.id.layout_newpassword);
        newPassWord = (FloatingEditText) findViewById(R.id.edittext_newpassword);
        repeatNewPassWord = (FloatingEditText) findViewById(R.id.edittext_repeatpassword);
        completeButton = (PaperButton) findViewById(R.id.button_complete);
        completeButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_next:
                final String oldpassword = userpasswordEditText.getText().toString();
                if (TextUtils.isEmpty(oldpassword)) {
                    userpasswordEditText.requestFocus();
                    AnimationUtil.shake(userpasswordEditText);
                } else {
                    if(nextProgressBar.getVisibility() == View.VISIBLE){
                        return;
                    }
                    nextProgressBar.setVisibility(View.VISIBLE);
                    StringRequest request =
                            new StringRequest(Method.POST, UrlUtil.getStuCheckOldPasswordUrl(
                                    KAccount.getAccountId(), oldpassword), new Listener<String>() {
                                public void onResponse(final String response) {
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            setNewPassWord();
                                        }
                                    }, 500);
                                };
                            }, new ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    nextProgressBar.hide(ModifyPasswordActivity.this);
                                    if (null != error.networkResponse
                                            && error.networkResponse.statusCode == 400) {
                                        ToastUtil.toast(R.string.password_error);
                                    } else {
                                        ResponseUtil.toastError(error);
                                    }
                                };
                            });
                    RequestUtil.getInstance().addToRequestQueue(request, TAG);
                }
                break;
            case R.id.button_complete:
                if (comparaPassWord()) {
                    StringRequest request =
                            new StringRequest(Method.POST, UrlUtil.getStuModifyPasswordUrl(
                                    KAccount.getAccountId(), newpassword), new Listener<String>() {
                                public void onResponse(final String response) {
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            ToastUtil.toast(R.string.modified);
                                            finish();
                                        }
                                    }, 500);
                                };
                            }, new ErrorListener() {
                                public void onErrorResponse(VolleyError error) {                                   
                                        ResponseUtil.toastError(error);                                 
                                };
                            });
                    RequestUtil.getInstance().addToRequestQueue(request, TAG);
                }
                break;
            default:
                break;
        }
    }

    private boolean comparaPassWord() {
        if (!newPassWord.getText().toString().equals(repeatNewPassWord.getText().toString())) {
            AnimationUtil.shake(newPassWord);
            AnimationUtil.shake(repeatNewPassWord);
            return false;
        }
        return true;
    }

    private void setNewPassWord() {
        AnimationUtil.centerToLeft(userpasswordEditText);
        AnimationUtil.centerToLeft(nextButton);
        AnimationUtil.rightToCenter(mLayout);
        mLayout.setVisibility(View.VISIBLE);
    }
}
