package com.kcb.student.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class ModifyPasswordActivity extends BaseActivity {

    private ButtonFlat backButton;
    private ButtonFlat finishButton;
    private EditText userpasswordEditText;
    private EditText passwordEditText;
    private EditText password2EditText;
    private CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_modifypassword);

        initView();
    }

    @Override
    protected void initView() {

        backButton = (ButtonFlat) findViewById(R.id.button_back);
        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        userpasswordEditText = (EditText) findViewById(R.id.edittext_userpassword);
        passwordEditText = (EditText) findViewById(R.id.edittext_newpassword);
        password2EditText = (EditText) findViewById(R.id.edittext_renewpassword);
        check = (CheckBox) findViewById(R.id.check);

        backButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);

        CheckPassword();
    }

    @Override
    protected void initData() {}

    public void CheckPassword() {
        userpasswordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = userpasswordEditText.getText().toString();
                if (!TextUtils.isEmpty(password)) {
                    check.setVisibility(View.VISIBLE);
                    passwordEditText.setEnabled(true);
                    password2EditText.setEnabled(true);
                } else {
                    check.setVisibility(View.INVISIBLE);
                    passwordEditText.setEnabled(false);
                    password2EditText.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onClick(View v) {
        String password = passwordEditText.getText().toString();
        String password2 = password2EditText.getText().toString();
        if (v == backButton) {
            finish();
        }
        if (v == finishButton) {
            if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                ToastUtil.toast(R.string.emptypassword);
            }
            if (!password.equals(password2)) {
                passwordEditText.setText("");
                password2EditText.setText("");
                ToastUtil.toast(R.string.errorpassword);
            } else {
                ToastUtil.toast(R.string.successpassword);
            }
        }
    }
}
