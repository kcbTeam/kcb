
package com.kcb.student.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;
import com.kcb.library.view.parallaxview.ParallaxScrollView;

/**
 * @className: SubmitActivity
 * @description: 
 * @author: Ding
 * @date: 2015年5月6日 下午4:10:17
 */
public class SubmitActivity extends BaseActivity{
    
    private ButtonFlat submitButton;
    private ButtonFlat modifyButton1;
    private ButtonFlat modifyButton2;
    private ButtonFlat modifyButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_submit);
        
        initView();
    }
    
    @Override
    protected void initView() {      
        ParallaxScrollView parallaxScrollView=(ParallaxScrollView) findViewById(R.id.scrollview_submit);
        submitButton=(ButtonFlat) findViewById(R.id.button_submit);
        modifyButton1=(ButtonFlat) findViewById(R.id.button_modify1);
        modifyButton2=(ButtonFlat) findViewById(R.id.button_modify2);
        modifyButton3=(ButtonFlat) findViewById(R.id.button_modify3);
        
        parallaxScrollView.setDiffFactor(0.3f);
        submitButton.setOnClickListener(this);
        submitButton.setRippleSpeed(6f);
    }

    @Override
    protected void initData() {}
    
    @Override
    public void onClick(View v){
        DialogUtil.showDialog(this, "取消", "请问你是否确认提交本次测试内容？", "确定",
            new OnClickListener() {

                @Override
                public void onClick(View v) {
                    ToastUtil.toast("提交成功!");
                    finish();
                }
            }, "取消", null);
    }
    
}
