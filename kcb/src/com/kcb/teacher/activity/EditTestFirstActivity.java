package com.kcb.teacher.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;
import com.kcb.library.slider.*;
import com.kcb.library.slider.Slider.OnValueChangedListener;

public class EditTestFirstActivity extends BaseActivity {

	int backgroundColor = Color.parseColor("#1E88E5");
	private ButtonFlat backButton;
	private PaperButton editfinishButton;
	private TextView numtip;
	private Slider slider;

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

		int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);

		slider = (Slider) findViewById(R.id.sliderNumber);
		slider.setBackgroundColor(color);

		// findViewById(R.id.sliderNumber).setBackgroundColor(color);

		numtip = (TextView) findViewById(R.id.edittext_num);

		slider.setOnValueChangedListener(new OnValueChangedListener() {

			@Override
			public void onValueChanged(int value) {
				// TODO Auto-generated method stub
				numtip.setText("共" + String.valueOf(value) + "题");
			}

		});

		backButton = (ButtonFlat) findViewById(R.id.button_back);
		
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		editfinishButton = (PaperButton) findViewById(R.id.button_editfinish);
		editfinishButton.setOnClickListener(mClickListener);
		
		
	/*	
	 * editfinishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditTestFirstActivity.this,
						EditTestActivity.class);
				startActivity(intent);
			}
		});*/

	}
	
	private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
        	Intent intent = new Intent(EditTestFirstActivity.this,
					EditTestActivity.class);
			startActivity(intent);
        }
	};
	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
