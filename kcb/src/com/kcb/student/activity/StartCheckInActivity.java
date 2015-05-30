package com.kcb.student.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.adapter.StartCheckInAdapter;
import com.kcb.student.adapter.StartCheckInAdapter.RecyclerItemClickListener;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description: Check in,six Textview,one reclyclerView,one Button
 * @author: Tao Li
 * @date: 2015-4-24 下午9:16:22
 */
public class StartCheckInActivity extends BaseActivity {

    private final String TAG = StartCheckInActivity.class.getName();

    private View numView;
    private TextView num1TextView;
    private TextView num2TextView;
    private TextView num3TextView;
    private TextView num4TextView;

    private RecyclerView recyclerView;

    private PaperButton finishButton;
    private SmoothProgressBar progressBar;

    private StartCheckInAdapter mAdapter;
    private int currentInputIndex = 0;

    private int mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_checkin);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        numView = findViewById(R.id.linearlayout_shownum);
        num1TextView = (TextView) findViewById(R.id.textview_shownum1);
        num2TextView = (TextView) findViewById(R.id.textview_shownum2);
        num3TextView = (TextView) findViewById(R.id.textview_shownum3);
        num4TextView = (TextView) findViewById(R.id.textview_shownum4);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_finish);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new StartCheckInAdapter();
        mAdapter.setRecyclerItemClickListener(mRecyclerItemClickListener);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mTime = Integer.valueOf(getIntent().getStringExtra(DATA_TIME));
        // TODO show time, if time is 0, dialog tip
    }

    @Override
    public void onClick(View v) {}

    private RecyclerItemClickListener mRecyclerItemClickListener = new RecyclerItemClickListener() {

        @Override
        public void onItemClick(View view, int postion) {
            if (postion == 9) { // clean all num
                clearNum();
            } else if (postion == 11) { // clean last num
                switch (currentInputIndex) {
                    case 1:
                        num1TextView.setText("");
                        break;
                    case 2:
                        num2TextView.setText("");
                        break;
                    case 3:
                        num3TextView.setText("");
                        break;
                    case 4:
                        num4TextView.setText("");
                        break;
                    default:
                        break;
                }
                if (currentInputIndex > 0) {
                    currentInputIndex--;
                } else {
                    currentInputIndex = 0;
                }
            } else { // input num
                if (postion == 10) {
                    postion = -1;
                }
                if (currentInputIndex != 4) {
                    currentInputIndex++;
                    switch (currentInputIndex) {
                        case 1:
                            num1TextView.setText(String.valueOf(postion + 1));
                            break;
                        case 2:
                            num2TextView.setText(String.valueOf(postion + 1));
                            break;
                        case 3:
                            num3TextView.setText(String.valueOf(postion + 1));
                            break;
                        case 4:
                            num4TextView.setText(String.valueOf(postion + 1));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    };

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                return;
            }
            if (!isNumCompleted()) {
                AnimationUtil.shake(numView);
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            StringRequest request =
                    new StringRequest(Method.POST, UrlUtil.getStuCheckinSubmitUrl(
                            KAccount.getAccountId(), getNum()), new Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            // TODO save checkin rate
                            ToastUtil.toast(R.string.checkin_success);
                            finish();
                        }
                    }, new ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.hide(StartCheckInActivity.this);
                            if (error.networkResponse.statusCode == 400) {
                                ToastUtil.toast(R.string.num_error);
                                clearNum();
                            } else {
                                ResponseUtil.toastError(error);
                            }
                        }
                    });
            RequestUtil.getInstance().addToRequestQueue(request, TAG);
        }
    };

    private boolean isNumCompleted() {
        return getNum().length() == 4;
    }

    private String getNum() {
        String num1 = num1TextView.getText().toString();
        String num2 = num2TextView.getText().toString();
        String num3 = num3TextView.getText().toString();
        String num4 = num4TextView.getText().toString();
        return num1 + num2 + num3 + num4;
    }

    private void clearNum() {
        num1TextView.setText("");
        num2TextView.setText("");
        num3TextView.setText("");
        num4TextView.setText("");
        currentInputIndex = 0;
    }

    @Override
    public void onBackPressed() {
        ToastUtil.toast("签到未完成");
    }

    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
    };

    private static final String DATA_TIME = "data_time";

    public static void start(Context context, String time) {
        Intent intent = new Intent(context, StartCheckInActivity.class);
        intent.putExtra(DATA_TIME, time);
        context.startActivity(intent);
    }
}
