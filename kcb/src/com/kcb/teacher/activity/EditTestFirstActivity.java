package com.kcb.teacher.activity;

import android.os.Bundle;
/**
 * 
 * @className: EditTestFirstActivity
 * @description:
 * @author: ljx
 * @date: 2015年5月14日 下午4:51:17
 */

import android.view.View;
import android.view.View.OnClickListener;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class EditTestFirstActivity extends BaseActivity {
	
	private ButtonFlat backButton;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tch_activity_edittestfirst);

		initView();
		initData();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}
}
