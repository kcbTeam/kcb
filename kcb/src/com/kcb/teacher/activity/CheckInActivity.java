package com.kcb.teacher.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午9:05:06
 */
public class CheckInActivity extends BaseActivity {

	private ButtonFlat giveupButton;
	private PaperButton getNumButton;
	private PaperButton startButton;
	private PaperButton stopButton;
	private TextView signnum1TextView;
	private TextView signnum2TextView;
	private TextView signnum3TextView;
	private TextView signnum4TextView;

	// private TextView numTextview;
	private PaperButton rateButton;
	private PaperButton finishButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tch_activity_checkin);

		initView();
	}

	@Override
	protected void initView() {
		giveupButton = (ButtonFlat) findViewById(R.id.button_giveup);
		giveupButton.setOnClickListener(this);
		giveupButton.setRippleSpeed(6f);

		getNumButton = (PaperButton) findViewById(R.id.button_getnum);

		getNumButton.setOnClickListener(mClickListener);

		signnum1TextView = (TextView) findViewById(R.id.textview_showsignnum1);
		signnum2TextView = (TextView) findViewById(R.id.textview_showsignnum2);
		signnum3TextView = (TextView) findViewById(R.id.textview_showsignnum3);
		signnum4TextView = (TextView) findViewById(R.id.textview_showsignnum4);

		startButton = (PaperButton) findViewById(R.id.button_start);
		startButton.setOnClickListener(mClickListener);

		stopButton = (PaperButton) findViewById(R.id.button_stop);

		rateButton = (PaperButton) findViewById(R.id.button_rate);
		rateButton.setOnClickListener(mClickListener);

		finishButton = (PaperButton) findViewById(R.id.button_finish);
		finishButton.setOnClickListener(mClickListener);
	}

	@Override
	protected void initData() {
	}

	@Override
	public void onClick(View v) {
		if (v == giveupButton) {

		}
	}

	private CustomOnClickListener mClickListener = new CustomOnClickListener(
			CustomOnClickListener.DELAY_PAPER_BUTTON) {

		@Override
		public void doClick(View v) {

			if (v == getNumButton) {
				final int intnum = (int) (Math.random() * 9000 + 1000);

				signnum1TextView.setText("");
				signnum2TextView.setText("");
				signnum3TextView.setText("");
				signnum4TextView.setText("");

				final Timer timer = new Timer();

				final Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						if (msg.what == 0x1000) {
							signnum1TextView.setText(String
									.valueOf(intnum / 1000));
						}
						if (msg.what == 0x1111) {
							signnum2TextView.setText(String
									.valueOf(intnum % 1000 / 100));
						}
						if (msg.what == 0x1222) {
							signnum3TextView.setText(String
									.valueOf(intnum % 1000 % 100 / 10));
						}
						if (msg.what == 0x1333) {
							signnum4TextView.setText(String
									.valueOf(intnum % 1000 % 100 % 10));
						}

					}
				};

				TimerTask task1 = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = 0x1000;
						handler.sendMessage(message);
					}
				};

				TimerTask task2 = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = 0x1111;
						handler.sendMessage(message);
					}
				};

				TimerTask task3 = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = 0x1222;
						handler.sendMessage(message);
					}
				};

				TimerTask task4 = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = 0x1333;
						handler.sendMessage(message);
					}
				};
				timer.schedule(task1, 500);
				timer.schedule(task2, 1000);
				timer.schedule(task3, 1500);
				timer.schedule(task4, 2000);

			} else if (v == startButton) {

				startButton.setVisibility(View.GONE);
				stopButton.setVisibility(View.VISIBLE);
				stopButton.setClickable(true);

			} else if (v == stopButton) {
				// DialogUtil.showDialog(this,"停止签到","是否真的停止签到？","是",new
				// OnClickListener(){},"否",new OnClickListener(){});

			} else if (v == rateButton) {
				Intent intent = new Intent(CheckInActivity.this,
						CheckInDetailsActivity.class);
				startActivity(intent);
			} else if (v == finishButton) {
				finish();
			}
		}
	};
}
