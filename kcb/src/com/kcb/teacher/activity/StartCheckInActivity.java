package com.kcb.teacher.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
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

    // get checkin num layout
    private PaperButton getNumButton;
    private TextView signnum1TextView;
    private TextView signnum2TextView;
    private TextView signnum3TextView;
    private TextView signnum4TextView;

    // start checkin layout
    private View startLayout;
    private TextView startTipTextView;
    private PaperButton startButton;
    private SmoothProgressBar startProgressBar;

    private boolean hasGetNum;
    private int mNum;
    private Handler mShowNumHandler;
    private final int MESSAGE_SHOW_NUM1 = 1;
    private final int MESSAGE_SHOW_NUM2 = 2;
    private final int MESSAGE_SHOW_NUM3 = 3;
    private final int MESSAGE_SHOW_NUM4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_startcheckin);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        getNumButton = (PaperButton) findViewById(R.id.button_getnum);
        getNumButton.setOnClickListener(mClickListener);
        signnum1TextView = (TextView) findViewById(R.id.textview_showsignnum1);
        signnum2TextView = (TextView) findViewById(R.id.textview_showsignnum2);
        signnum3TextView = (TextView) findViewById(R.id.textview_showsignnum3);
        signnum4TextView = (TextView) findViewById(R.id.textview_showsignnum4);

        startLayout = findViewById(R.id.layout_start);
        startTipTextView = (TextView) findViewById(R.id.tip2);
        startButton = (PaperButton) findViewById(R.id.button_start);
        startButton.setOnClickListener(mClickListener);
        startProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_start);
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
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_getnum:
                    getNum();
                    break;
                case R.id.button_start:
                    start();
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
        if (startProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        startProgressBar.setVisibility(View.VISIBLE);
        startTipTextView.setText("请稍等...");
        // start checkin
        StringRequest request =
                new StringRequest(Method.POST, UrlUtil.getTchCheckinStartUrl(
                        KAccount.getAccountId(), mNum), new Listener<String>() {

                    @Override
                    public void onResponse(String response) {}
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        startTipTextView.setText(getString(R.string.tipforteacher2));
                        startProgressBar.hide(StartCheckInActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
    }
}
