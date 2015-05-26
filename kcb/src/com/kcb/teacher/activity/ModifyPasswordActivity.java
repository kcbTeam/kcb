package com.kcb.teacher.activity;

import android.os.Bundle;
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
import com.kcbTeam.R;

public class ModifyPasswordActivity extends BaseActivity {

    private final String TAG = ModifyPasswordActivity.class.getName();

    private ButtonFlat backButton;
    private FloatingEditText userpasswordEditText;
    private PaperButton nextButton;
    private PaperButton completeButton;
    private FloatingEditText newPassWord;
    private FloatingEditText repeatNewPassWord;
    private RelativeLayout mLayout;

    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_modifypassword);

        initView();
    }

    @Override
    protected void initView() {

        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        userpasswordEditText = (FloatingEditText) findViewById(R.id.edittext_oldpassword);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);

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
                final String password = userpasswordEditText.getText().toString();
                if (password.equals("")) {
                    ToastUtil.toast(R.string.password_empty);
                    userpasswordEditText.requestFocus();
                    AnimationUtil.shake(userpasswordEditText);
                } else {
                    StringRequest request =
                            new StringRequest(Method.POST, UrlUtil.getTchCheckOldPasswordUrl(
                                    KAccount.getAccountId(), password), new Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    setNewPassWord();
                                }
                            }, new ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (null != error.networkResponse
                                            && error.networkResponse.statusCode == 400) {
                                        ToastUtil.toast(R.string.password_error);
                                    } else {
                                        ResponseUtil.toastError(error);
                                    }
                                }
                            });

                    RequestUtil.getInstance().addToRequestQueue(request, TAG);
                }
                break;
            case R.id.button_complete:
                if (comparaPassWord()) {
                    StringRequest request =
                            new StringRequest(Method.POST, UrlUtil.getTchModifyPasswordUrl(
                                    KAccount.getAccountId(), newPassword),
                                    new Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            ToastUtil.toast(R.string.modified);
                                            finish();
                                        }
                                    }, new ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
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
            ToastUtil.toast(R.string.twice_password_unequal);
            return false;
        }
        newPassword = newPassWord.getText().toString();
        return true;
    }

    private void setNewPassWord() {
        AnimationUtil.centerToLeft(userpasswordEditText);
        AnimationUtil.centerToLeft(nextButton);
        AnimationUtil.rightToCenter(mLayout);
        mLayout.setVisibility(View.VISIBLE);
    }

}
