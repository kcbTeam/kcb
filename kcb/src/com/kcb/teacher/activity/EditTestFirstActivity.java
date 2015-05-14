package com.kcb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class EditTestFirstActivity extends BaseActivity {

	private ButtonFlat backButton;
	private PaperButton editfinishButton;

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

		editfinishButton = (PaperButton) findViewById(R.id.button_editfinish);
		editfinishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditTestFirstActivity.this,
						EditTestActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
