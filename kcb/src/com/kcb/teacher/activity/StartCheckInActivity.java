package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
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
    private TextView signnum1TextView;
    private TextView signnum2TextView;
    private TextView signnum3TextView;
    private TextView signnum4TextView;

    private PaperButton startButton;
    private SmoothProgressBar startStopProgressBar;

    private View timeLayout;
    private ProgressBar progressBar;
    private TextView timeTextView;
    private int mTime = 0;

    private PaperButton rateButton;
    private PaperButton finishButton;

    private int mNum;
    private boolean hasGetNum;
    private Handler mShowNumHandler;
    private final int MESSAGE_SHOW_NUM1 = 1;
    private final int MESSAGE_SHOW_NUM2 = 2;
    private final int MESSAGE_SHOW_NUM3 = 3;
    private final int MESSAGE_SHOW_NUM4 = 4;

    private boolean hasStarted;
    private boolean hasFinished;

    private Handler mShowTimeHandler;
    private static final int MESSAGE_TIME_ADD = 10;
    private static final int MESSAGE_TIME_STOP = 11;

    // add by zqj
    public final static String CURRENT_CHECKIN_RECORD_KEY = "current record";
    public final static String TAG = "CheckInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_startcheckin);

        initView();
        initData();
    }

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

        startButton = (PaperButton) findViewById(R.id.button_startstop);
        startButton.setOnClickListener(mClickListener);

        startStopProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_startstop);

        timeLayout = findViewById(R.id.layout_time);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        timeTextView = (TextView) findViewById(R.id.textview_timetip);

        rateButton = (PaperButton) findViewById(R.id.button_rate);
        rateButton.setOnClickListener(mClickListener);
        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {
        mShowNumHandler = new Handler(getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_SHOW_NUM1) {
                    signnum1TextView.setText(String.valueOf(mNum / 1000));
                }
                if (msg.what == MESSAGE_SHOW_NUM2) {
                    signnum2TextView.setText(String.valueOf(mNum % 1000 / 100));
                }
                if (msg.what == MESSAGE_SHOW_NUM3) {
                    signnum3TextView.setText(String.valueOf(mNum % 100 / 10));
                }
                if (msg.what == MESSAGE_SHOW_NUM4) {
                    signnum4TextView.setText(String.valueOf(mNum % 10));
                }
            };
        };
        mShowTimeHandler = new Handler(getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_TIME_ADD:
                        progressBar.setProgress(mTime);
                        timeTextView.setText(mTime + "/120");
                        break;
                    case MESSAGE_TIME_STOP:
                        hasFinished = true;
                        break;
                }
            }
        };
    }

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
            switch (v.getId()) {
                case R.id.button_getnum:
                    getNum();
                    break;
                case R.id.button_startstop:
                    if (!hasStarted) {
                        start();
                    } else {
                        if (!hasFinished) {
                            stop();
                        } else {
                            ToastUtil.toast("签到已结束，无法停止");
                        }
                    }
                    break;
                case R.id.button_rate:
                    lookRate();
                    break;
                case R.id.button_finish:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void getNum() {
        if (hasGetNum) {
            return;
        }
        hasGetNum = true;

        mNum = (int) (Math.random() * 9000 + 1000);

        Message message = mShowNumHandler.obtainMessage();
        message.what = MESSAGE_SHOW_NUM1;
        mShowNumHandler.sendMessage(message);

        Message message2 = new Message();
        message2.what = MESSAGE_SHOW_NUM2;
        mShowNumHandler.sendMessageDelayed(message2, 200);

        Message message3 = new Message();
        message3.what = MESSAGE_SHOW_NUM3;
        mShowNumHandler.sendMessageDelayed(message3, 400);

        Message message4 = new Message();
        message4.what = MESSAGE_SHOW_NUM4;
        mShowNumHandler.sendMessageDelayed(message4, 600);
    }

    private void start() {
        if (startStopProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        startStopProgressBar.setVisibility(View.VISIBLE);
        StringRequest request =
                new StringRequest(Method.POST, UrlUtil.getTchCheckinStartUrl(
                        KAccount.getAccountId(), mNum), new Listener<String>() {

                    @Override
                    public void onResponse(String response) {}
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });


        hasStarted = true;
        startButton.setText("停止签到");
        progressBar.setVisibility(View.VISIBLE);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (mTime > 120) {
                    mShowTimeHandler.sendEmptyMessage(MESSAGE_TIME_STOP);
                    timer.cancel();
                } else {
                    mShowTimeHandler.sendEmptyMessage(MESSAGE_TIME_ADD);
                }
                mTime++;
            }
        }, 0, 1000);
    }

    private void stop() {
        DialogUtil.showNormalDialog(StartCheckInActivity.this, R.string.stopsign,
                R.string.stop_sign_tip, R.string.sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.GONE);
                        timeTextView.setVisibility(View.GONE);

                        rateButton.setVisibility(View.VISIBLE);
                        finishButton.setVisibility(View.VISIBLE);


                        Animation animation =
                                AnimationUtils.loadAnimation(StartCheckInActivity.this,
                                        R.anim.layer_alpha_out);
                    }
                }, R.string.cancel, null);
    }

    private void lookRate() {
        Intent intent = new Intent(StartCheckInActivity.this, CheckInDetailsActivity.class);
        List<StudentInfo> missedCheckInStus = new ArrayList<StudentInfo>();
        missedCheckInStus.add(new StudentInfo("testNameq", "0003", 12, 5));
        CheckInRecordInfo currentInfo = new CheckInRecordInfo("2015 - 1", 0.9f, missedCheckInStus);
        intent.putExtra("ACTIVITY_TAG", TAG);
        intent.putExtra(CURRENT_CHECKIN_RECORD_KEY, currentInfo);
        startActivity(intent);
    }

    public void onBackPressed() {
        DialogUtil.showNormalDialog(this, R.string.giveupsign, R.string.giveup_sign_tip,
                R.string.sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }, R.string.cancel, null);
    };
}
