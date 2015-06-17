package com.kcb.student.activity.checkin;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.database.checkin.CheckInDao;
import com.kcb.student.model.account.KAccount;
import com.kcb.student.model.checkin.CheckInResult;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckResultActivity
 * @description: look checkin result
 * @author: Ding
 * @date: 2015年5月5日 下午3:45:54
 */
public class LookCheckInActivity extends BaseActivity {

    private final String TAG = LookCheckInActivity.class.getName();

    private ButtonFlat backbutton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private TextView rateTextView;

    private PieChart pieChart;
    private float[] mRates = new float[2];

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_look_checkin);
        StatusBarUtil.setStuStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backbutton = (ButtonFlat) findViewById(R.id.button_back);
        backbutton.setOnClickListener(this);
        backbutton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));

        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);
        refreshButton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));

        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        rateTextView = (TextView) findViewById(R.id.textview_rate);
        pieChart = (PieChart) findViewById(R.id.piechart_rate);
    }

    @Override
    protected void initData() {
        CheckInDao checkInDao = new CheckInDao(LookCheckInActivity.this);
        int allTimes = checkInDao.getAllTimes();
        int successTimes = checkInDao.getSuccessTimes();
        checkInDao.close();

        showCheckInRate(allTimes, successTimes);

        if (allTimes == 0) {
            mRates[0] = 0;
            mRates[1] = 1;
        } else {
            mRates[0] = successTimes / allTimes;
            mRates[1] = 1 - mRates[0];
        }
        showCheckInChart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_refresh:
                refresh();
                break;
            default:
                break;
        }
    }

    private void showCheckInRate(int totalTimes, int checkedTimes) {
        rateTextView.setText(String.format(getString(R.string.stu_checkin_rate),
                KAccount.getTchName(), totalTimes, checkedTimes));
    }

    private void showCheckInChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColorTransparent(true);
        pieChart.setTouchEnabled(false);

        if (pieChart.isDrawCenterTextEnabled()) {
            pieChart.setDrawCenterText(false);
        } else {
            pieChart.setDrawCenterText(true);
        }

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);

        pieChart.setCenterText("");

        pieChart.setData(getPieData(2, 100));
        pieChart.highlightValues(null);
        pieChart.invalidate();
        pieChart.setVisibility(View.INVISIBLE);

        Legend mLegend = pieChart.getLegend();
        mLegend.setEnabled(false);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pieChart.animateY(1800);
                pieChart.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    private PieData getPieData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add(getString(R.string.stu_success_checkin));
        xValues.add(getString(R.string.stu_uncheckin));
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        yValues.add(new Entry(mRates[0], 0));
        yValues.add(new Entry((mRates[1]), 1));

        PieDataSet pieDataSet = new PieDataSet(yValues, null);
        pieDataSet.setSliceSpace(0f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(0xff427fed);
        colors.add(0xffbdbdbd);
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px);
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueTextColor(0xffffffff);
        pieDataSet.setValueFormatter(new PercentFormatter());
        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }

    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request =
                new JsonObjectRequest(Method.GET, UrlUtil.getStuCheckinResultUrl(
                        KAccount.getAccountId(), KAccount.getTchId()), new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // save to database
                        CheckInResult checkInResult = CheckInResult.fromJsonObejct(response);
                        CheckInDao checkInDao = new CheckInDao(LookCheckInActivity.this);
                        checkInDao.deleteAll();
                        checkInDao.add(checkInResult);
                        checkInDao.close();
                        // show result
                        initData();
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(LookCheckInActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        pieChart = null;
        mRates = null;
    }

    /**
     * start
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, LookCheckInActivity.class);
        context.startActivity(intent);
    }
}
