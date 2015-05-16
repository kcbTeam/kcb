package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.DialogUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.model.CheckInRecordInfo;
import com.kcb.teacher.model.StudentInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午9:05:06 add something by zqj.
 */
public class StartCheckInActivity extends BaseActivity {

    private ButtonFlat giveupButton;
    private PaperButton getNumButton;
    private SmoothProgressBar getNumProgressBar;

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

    // add by zqj
    public final static String CURRENT_CHECKIN_RECORD_KEY = "current record";
    public final static String TAG = "CheckInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_startcheckin);

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

        getNumProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_getnum);

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

                getNumProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        getNumProgressBar.setVisibility(View.INVISIBLE);
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
                        timer.schedule(task1, 300);
                        timer.schedule(task2, 600);
                        timer.schedule(task3, 900);
                        timer.schedule(task4, 1200);

                    }
                }, 2000);

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
                DialogUtil.showNormalDialog(StartCheckInActivity.this, R.string.stopsign,
                        R.string.stop_sign_tip, R.string.sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                finish();
                                // progressBar.setVisibility(View.GONE);
                                // timetip.setText(""); // important
                                // timetip.setVisibility(View.GONE);
                            }
                        }, R.string.cancel, null);

            } else if (v == rateButton) {
                Intent intent = new Intent(StartCheckInActivity.this, CheckInDetailsActivity.class);
                List<StudentInfo> missedCheckInStus = new ArrayList<StudentInfo>();
                missedCheckInStus.add(new StudentInfo("testNameq", "0003", 12, 5));
                CheckInRecordInfo currentInfo =
                        new CheckInRecordInfo("2015 - 1", 0.9f, missedCheckInStus);
                intent.putExtra("ACTIVITY_TAG", TAG);
                intent.putExtra(CURRENT_CHECKIN_RECORD_KEY, currentInfo);
                startActivity(intent);
            } else if (v == finishButton) {
                finish();
            }
        }
    };
    
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
            
        	DialogUtil.showNormalDialog(this, R.string.giveupsign, R.string.giveup_sign_tip,
                    R.string.sure, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, R.string.cancel, null);
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 

}
