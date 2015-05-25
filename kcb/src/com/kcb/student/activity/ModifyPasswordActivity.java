package com.kcb.student.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class ModifyPasswordActivity extends BaseActivity {

    private ButtonFlat backButton;
    private FloatingEditText userpasswordEditText;
    private PaperButton nextButton;
    private PaperButton completeButton;
    private FloatingEditText newPassWord;
    private FloatingEditText repeatNewPassWord;
    private RelativeLayout mLayout;

    private String oldPassWord = "123456";

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
                if (oldPassWord.equals(userpasswordEditText.getText().toString())) {
                    setNewPassWord();
                } else {
                    ToastUtil.toast(R.string.password_error);
                }
                break;
            case R.id.button_complete:
                if (comparaPassWord()) {
                    // TODO set new password
                    oldPassWord = newPassWord.getText().toString();
                    ToastUtil.toast(R.string.modified);
                    finish();
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
