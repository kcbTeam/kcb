package com.kcb.teacher.activity.checkin;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.model.KAccount;
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

    private ButtonFlat backButton;

    // get checkin num layout
    private TextView signnum1TextView;
    private TextView signnum2TextView;
    private TextView signnum3TextView;
    private TextView signnum4TextView;

    // start checkin layout
    private TextView startTipTextView;
    private PaperButton startButton;
    private SmoothProgressBar startProgressBar;

    private int mNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_start_checkin);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        signnum1TextView = (TextView) findViewById(R.id.textview_showsignnum1);
        signnum2TextView = (TextView) findViewById(R.id.textview_showsignnum2);
        signnum3TextView = (TextView) findViewById(R.id.textview_showsignnum3);
        signnum4TextView = (TextView) findViewById(R.id.textview_showsignnum4);

        startTipTextView = (TextView) findViewById(R.id.tip2);
        startButton = (PaperButton) findViewById(R.id.button_start);
        startButton.setOnClickListener(mClickListener);
        startProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_start);
    }

    @Override
    protected void initData() {
        mNum = (int) (Math.random() * 9000 + 1000);
        signnum1TextView.setText(String.valueOf(mNum / 1000));
        signnum2TextView.setText(String.valueOf(mNum % 1000 / 100));
        signnum3TextView.setText(String.valueOf(mNum % 100 / 10));
        signnum4TextView.setText(String.valueOf(mNum % 10));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_start:
                    start();
                    break;
                default:
                    break;
            }
        }
    };

    private void start() {
        if (startProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        OnClickListener sureClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                startProgressBar.setVisibility(View.VISIBLE);
                startTipTextView.setText(R.string.tch_wait);
                LogUtil.i(TAG, "tch start checkin, checkin num is " + mNum);
                StringRequest request =
                        new StringRequest(Method.POST, UrlUtil.getTchCheckinStartUrl(
                                KAccount.getAccountId(), mNum), new Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                LogUtil.i(TAG, getString(R.string.tch_checkin_started));
                                ToastUtil.toast(R.string.tch_checkin_started);
                                finish();
                            }
                        }, new ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                startTipTextView
                                        .setText(getString(R.string.tch_two_min_finish_checkin));
                                startProgressBar.hide(StartCheckInActivity.this);
                                NetworkResponse networkResponse = error.networkResponse;
                                if (null != networkResponse) {
                                    int statusCode = networkResponse.statusCode;
                                    if (statusCode == 400) {
                                        ToastUtil.toast(R.string.tch_checkin_unfinish);
                                    } else {
                                        ResponseUtil.toastError(error);
                                    }
                                } else {
                                    ResponseUtil.toastError(error);
                                }
                            }
                        });
                RequestUtil.getInstance().addToRequestQueue(request, TAG);
            }
        };
        DialogUtil.showNormalDialog(StartCheckInActivity.this, R.string.tch_comm_start,
                R.string.tch_start_checkin_tip, R.string.tch_comm_sure, sureClickListener,
                R.string.tch_comm_cancel, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
    }
}
