package com.kcb.teacher.activity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.model.StudentInfo;
import com.kcb.teacher.model.checkin.CheckInResult;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午9:05:06 add something by zqj.
 */
public class StartCheckInActivity extends BaseActivity {

    public static final String TAG = StartCheckInActivity.class.getName();

    // title layout
    private ButtonFlat giveupButton;

    // get checkin num layout
    private PaperButton getNumButton;
    private TextView signnum1TextView;
    private TextView signnum2TextView;
    private TextView signnum3TextView;
    private TextView signnum4TextView;

    // start checkin layout
    private View startLayout;
    private TextView startTipTextView;
    private PaperButton startStopButton;
    private SmoothProgressBar startStopProgressBar;
    private View timeLayout;
    private ProgressBar timeProgressBar;
    private TextView timeTextView;

    // finish checkin layout
    private View finishLayout;
    private PaperButton rateButton;
    private PaperButton finishButton;

    private boolean hasGetNum;
    private int mNum;
    private Handler mShowNumHandler;
    private final int MESSAGE_SHOW_NUM1 = 1;
    private final int MESSAGE_SHOW_NUM2 = 2;
    private final int MESSAGE_SHOW_NUM3 = 3;
    private final int MESSAGE_SHOW_NUM4 = 4;

    private boolean hasStarted;
    private int mRemainTime = 10;
    private boolean hasFinished;

    private Handler mShowTimeHandler;
    private static final int MESSAGE_TIME_ADD = 10;
    private static final int MESSAGE_TIME_FINISH = 11;
    private static final int MESSAGE_TIME_STOP = 12;

    // add by zqj
    public final static String CURRENT_CHECKIN_RECORD_KEY = "current record";

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

        startLayout = findViewById(R.id.layout_start);
        startTipTextView = (TextView) findViewById(R.id.tip2);
        startStopButton = (PaperButton) findViewById(R.id.button_startstop);
        startStopButton.setOnClickListener(mClickListener);
        startStopProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_startstop);
        timeLayout = findViewById(R.id.layout_time);
        timeProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        timeTextView = (TextView) findViewById(R.id.textview_timetip);

        finishLayout = findViewById(R.id.layout_finish);
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
                    startLayout.setVisibility(View.VISIBLE);
                    AnimationUtil.fadeIn(startLayout);
                }
            };
        };
        mShowTimeHandler = new Handler(getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_TIME_ADD:
                        timeProgressBar.setProgress(mRemainTime);
                        timeTextView.setText(mRemainTime + " s");
                        mRemainTime--;
                        break;
                    case MESSAGE_TIME_FINISH:
                        hasFinished = true;
                        startTipTextView.setText("两分钟已到");
                        finishLayout.setVisibility(View.VISIBLE);
                        AnimationUtil.fadeIn(finishLayout);
                        break;
                    case MESSAGE_TIME_STOP:
                        hasFinished = true;
                        startStopProgressBar.setVisibility(View.INVISIBLE);
                        startTipTextView.setText("已经停止");
                        finishLayout.setVisibility(View.VISIBLE);
                        AnimationUtil.fadeIn(finishLayout);
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == giveupButton) {
            if (hasFinished) {
                finish();
            } else {
                DialogUtil.showNormalDialog(this, R.string.giveupsign, R.string.giveup_sign_tip,
                        R.string.sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO cancel request
                                finish();
                            }
                        }, R.string.cancel, null);
            }
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
        Message message2 = mShowNumHandler.obtainMessage();
        message2.what = MESSAGE_SHOW_NUM2;
        mShowNumHandler.sendMessageDelayed(message2, 200);
        Message message3 = mShowNumHandler.obtainMessage();
        message3.what = MESSAGE_SHOW_NUM3;
        mShowNumHandler.sendMessageDelayed(message3, 400);
        Message message4 = mShowNumHandler.obtainMessage();
        message4.what = MESSAGE_SHOW_NUM4;
        mShowNumHandler.sendMessageDelayed(message4, 600);
    }

    private void start() {
        if (startStopProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        startStopProgressBar.setVisibility(View.VISIBLE);
        startTipTextView.setText("请稍等...");
        // start checkin
        StringRequest request =
                new StringRequest(Method.POST, UrlUtil.getTchCheckinStartUrl(
                        KAccount.getAccountId(), mNum), new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        mShowTimeHandler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                startStopProgressBar.setVisibility(View.GONE);
                                startTipTextView.setText("点击停止，后续学生将不可签到");
                                startStopButton.setText("停止签到");
                                timeLayout.setVisibility(View.VISIBLE);

                                hasStarted = true;

                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {

                                    @Override
                                    public void run() {
                                        if (mRemainTime < 0) {
                                            mShowTimeHandler.sendEmptyMessage(MESSAGE_TIME_FINISH);
                                            timer.cancel();
                                        } else {
                                            mShowTimeHandler.sendEmptyMessage(MESSAGE_TIME_ADD);
                                        }
                                    }
                                }, 0, 1000);
                            }
                        }, 500);
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        startTipTextView.setText(getString(R.string.tipforteacher2));
                        startStopProgressBar.hide(StartCheckInActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    private CheckInResult mCheckInResult;

    private void stop() {
        if (startStopProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        DialogUtil.showNormalDialog(StartCheckInActivity.this, R.string.stopsign,
                R.string.stop_sign_tip, R.string.sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (hasFinished) {
                            return;
                        }
                        startStopProgressBar.setVisibility(View.VISIBLE);
                        JsonObjectRequest request =
                                new JsonObjectRequest(Method.POST, UrlUtil.getTchCheckinStopUrl(
                                        KAccount.getAccountId(), mNum), new Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        mShowTimeHandler.sendEmptyMessage(MESSAGE_TIME_STOP);

                                        Date date = new Date(System.currentTimeMillis());
                                        Double rate = response.optDouble("rate");
                                        List<StudentInfo> studentInfos =
                                                new ArrayList<StudentInfo>();
                                        try {
                                            JSONArray jsonArray = response.getJSONArray("stu");
                                            if (jsonArray.length() > 0) {
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    StudentInfo studInfo =
                                                            StudentInfo.from(jsonArray
                                                                    .getJSONObject(i));
                                                    studentInfos.add(studInfo);
                                                }
                                            }
                                        } catch (JSONException e) {}
                                        mCheckInResult =
                                                new CheckInResult(date, rate, studentInfos);
                                        // TODO save checkin result to db;
                                    }
                                }, new ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        startStopProgressBar.hide(StartCheckInActivity.this);
                                        ResponseUtil.toastError(error);
                                    }
                                });
                        RequestUtil.getInstance().addToRequestQueue(request, TAG);
                    }
                }, R.string.cancel, null);
    }

    private void lookRate() {
        if (null != mCheckInResult) {
            CheckInDetailActivity.start(this, mCheckInResult);
        } else {
            ToastUtil.toast("未获取到签到结果");
        }
    }

    @Override
    public void onBackPressed() {
        if (hasFinished) {
            super.onBackPressed();
            return;
        }
        DialogUtil.showNormalDialog(this, R.string.giveupsign, R.string.giveup_sign_tip,
                R.string.sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }, R.string.cancel, null);
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
    }
}
