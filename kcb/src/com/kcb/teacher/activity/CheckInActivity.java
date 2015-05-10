package com.kcb.teacher.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
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
    private ProgressBar progressBar;
    private TextView timetip;

    protected static final int STOP = 0x10000;
    protected static final int NEXT = 0x10001;
    private int iCount = 0;

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

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP:
                    progressBar.setVisibility(View.GONE);
                    timetip.setVisibility(View.GONE);
                    Thread.currentThread().interrupt();
                    break;
                case NEXT:
                    if (!Thread.currentThread().isInterrupted()) {
                        progressBar.setProgress(iCount);
                        timetip.setText(iCount + "/120");
                    }
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        giveupButton = (ButtonFlat) findViewById(R.id.button_giveup);
        giveupButton.setOnClickListener(this);

        getNumButton = (PaperButton) findViewById(R.id.button_getnum);

        getNumButton.setOnClickListener(mClickListener);

        signnum1TextView = (TextView) findViewById(R.id.textview_showsignnum1);
        signnum2TextView = (TextView) findViewById(R.id.textview_showsignnum2);
        signnum3TextView = (TextView) findViewById(R.id.textview_showsignnum3);
        signnum4TextView = (TextView) findViewById(R.id.textview_showsignnum4);

        startButton = (PaperButton) findViewById(R.id.button_start);
        startButton.setOnClickListener(mClickListener);

        stopButton = (PaperButton) findViewById(R.id.button_stop);
        stopButton.setOnClickListener(mClickListener);

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        timetip = (TextView) findViewById(R.id.textview_timetip);

        rateButton = (PaperButton) findViewById(R.id.button_rate);
        rateButton.setOnClickListener(mClickListener);

        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        if (v == giveupButton) {
            DialogUtil.showNormalDialog(this, R.string.giveupsign, R.string.giveup_sign_tip,
                    R.string.sure, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, R.string.cancel, null);
        }
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

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
                            signnum1TextView.setText(String.valueOf(intnum / 1000));
                        }
                        if (msg.what == 0x1111) {
                            signnum2TextView.setText(String.valueOf(intnum % 1000 / 100));
                        }
                        if (msg.what == 0x1222) {
                            signnum3TextView.setText(String.valueOf(intnum % 1000 % 100 / 10));
                        }
                        if (msg.what == 0x1333) {
                            signnum4TextView.setText(String.valueOf(intnum % 1000 % 100 % 10));
                        }
                    }
                };

                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 0x1000;
                        handler.sendMessage(message);
                    }
                };

                TimerTask task2 = new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 0x1111;
                        handler.sendMessage(message);
                    }
                };

                TimerTask task3 = new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 0x1222;
                        handler.sendMessage(message);
                    }
                };

                TimerTask task4 = new TimerTask() {
                    @Override
                    public void run() {
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

                iCount = 0;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(120);
                progressBar.setProgress(0);

                Thread mThread = new Thread(new Runnable() {

                    public void run() {

                        for (int i = 0; i < 120; i++) {
                            try {
                                iCount = (i + 1);
                                Thread.sleep(1000);
                                if (i == 119) {
                                    Message msg = new Message();
                                    msg.what = STOP;
                                    mHandler.sendMessage(msg);
                                    break;
                                } else {
                                    Message msg = new Message();
                                    msg.what = NEXT;
                                    mHandler.sendMessage(msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                mThread.start();
            } else if (v == stopButton) {
                DialogUtil.showNormalDialog(CheckInActivity.this, R.string.stopsign,
                        R.string.stop_sign_tip, R.string.sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ToastUtil.toast("停止签到");
                                progressBar.setVisibility(View.GONE);
                                timetip.setText(""); // important
                                timetip.setVisibility(View.GONE);
                            }
                        }, R.string.cancel, null);

            } else if (v == rateButton) {
                Intent intent = new Intent(CheckInActivity.this, CheckInDetailsActivity.class);
                startActivity(intent);
            } else if (v == finishButton) {
                finish();
            }
        }
    };

}
