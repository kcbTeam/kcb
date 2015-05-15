package com.kcb.student.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class ModifyPasswordActivity extends BaseActivity{
    
    private ButtonFlat backButton;
    private ButtonFlat finishButton;
    private EditText passwordEditText;
    private EditText password2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_modifypassword);
        
        initView();
    }
    
    @Override
    protected void initView() {
        backButton=(ButtonFlat) findViewById(R.id.button_back);
        finishButton=(ButtonFlat) findViewById(R.id.button_finish);
        passwordEditText=(EditText) findViewById(R.id.edittext_newpassword);
        password2EditText=(EditText) findViewById(R.id.edittext_renewpassword);
        
        backButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {}
    
    @Override
    public void onClick(View v){
        String password=passwordEditText.getText().toString();
        String password2=password2EditText.getText().toString();
        if(v==backButton){
            finish();
        }if(v==finishButton){
            if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
                ToastUtil.toast("密码不能为空，请重新输入！");
            }if(!password.equals(password2)){
                passwordEditText.setText("");
                password2EditText.setText("");
                ToastUtil.toast("密码不一致，请重新输入！");
            }else{
                ToastUtil.toast("密码修改成功！");
            } 
        }
    }
    
}
