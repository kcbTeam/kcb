package com.kcb.teacher.activity.checkin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.adapter.checkin.LookCheckInDetailAdapter;
import com.kcb.teacher.database.checkin.CheckInDao;
import com.kcb.teacher.database.checkin.CheckInResult;
import com.kcb.teacher.database.checkin.UncheckedStudent;
import com.kcb.teacher.model.KAccount;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInDetailsActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月27日 下午11:14:41
 */
public class LookCheckInDetailActivity extends BaseFragmentActivity implements OnClickListener {

    private static final String TAG = LookCheckInDetailActivity.class.getName();

    private ButtonFlat backButton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private TextView dateTextView;
    private TextView rateTextView;

    private PieChart pieChart;

    private TextView stuInfoTipTextView;
    private View listviewTitleLayout;
    private ListView listView;

    // 静态变量，由LookCheckInActivity传入；因为此类中可能请求签到详情，所以使用静态变量可以方便更新上一个界面中对应的签到详情，避免再次读取数据库
    private static CheckInResult sCheckInResult;
    private float mCheckInRate;

    private LookCheckInDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_checkin_detail);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);

        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        dateTextView = (TextView) findViewById(R.id.textview_date);
        rateTextView = (TextView) findViewById(R.id.textview_rate);

        pieChart = (PieChart) findViewById(R.id.piechart);

        stuInfoTipTextView = (TextView) findViewById(R.id.textview_stuinfotip);
        listviewTitleLayout = findViewById(R.id.layout_listview_title);
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void initData() {
        mCheckInRate = (float) sCheckInResult.getRate();

        dateTextView.setText(sCheckInResult.getDateString());
        rateTextView.setText(String.format(getResources().getString(R.string.tch_checkin_rate_tip),
                (int) (100 * mCheckInRate)));

        List<UncheckedStudent> students = sCheckInResult.getUnCheckedStudents();
        if (mCheckInRate != 1) { // 如果签到率不为1
            if (students.isEmpty()) { // 但是有没有未签到的学生信息，需要请求学生信息；
                refreshButton.setVisibility(View.VISIBLE);
                refresh();
            } else { // 否则，显示学生信息即可
                showUncheckedStudents();
            }
        }
        initPieChart();
    }

    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        // 返回的结果是多个学生信息，包括姓名、学号、未签到率，需要终端按未签到率排序
        JsonArrayRequest request =
                new JsonArrayRequest(Method.GET, UrlUtil.getTchCheckinGetResultDetailUrl(
                        KAccount.getAccountId(), sCheckInResult.getDate()),
                        new Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                // 解析未签到学生详情，保存到数据库中
                                // 更新UI
                                try {
                                    List<UncheckedStudent> students =
                                            new ArrayList<UncheckedStudent>();
                                    for (int i = 0; i < response.length(); i++) {
                                        UncheckedStudent student =
                                                UncheckedStudent.fromJsonObject(response
                                                        .getJSONObject(i));
                                        students.add(student);
                                    }
                                    sCheckInResult.getUnCheckedStudents().addAll(students);
                                    CheckInDao checkInDao =
                                            new CheckInDao(LookCheckInDetailActivity.this);
                                    checkInDao.update(sCheckInResult);
                                    checkInDao.close();

                                    showUncheckedStudents();
                                } catch (JSONException e) {}

                                refreshButton.setVisibility(View.GONE);
                                progressBar.hide(LookCheckInDetailActivity.this);
                            }
                        }, new ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.hide(LookCheckInDetailActivity.this);
                                ResponseUtil.toastError(error);
                            }
                        });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    private void showUncheckedStudents() {
        stuInfoTipTextView.setVisibility(View.VISIBLE);
        listviewTitleLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        mAdapter = new LookCheckInDetailAdapter(this, sCheckInResult.getUnCheckedStudents());
        listView.setAdapter(mAdapter);
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

    private void initPieChart() {
        setData(pieChart, new String[] {getResources().getString(R.string.tch_checkin_success),
                getResources().getString(R.string.tch_checkin_failed)});
        setDefaultPieChartStyle(pieChart);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pieChart.animateY(1800);
                pieChart.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    private void setData(PieChart pieChart, String[] infoStrings) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(mCheckInRate, 0));
        yVals1.add(new Entry(1.0f - mCheckInRate, 1));
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 2; i++)
            xVals.add(infoStrings[i % infoStrings.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0);
        // dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(0xff427fed);
        colors.add(0xffbdbdbd);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    private void setDefaultPieChartStyle(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.setClickable(false);
        pieChart.setTouchEnabled(false);
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(false);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);
        pieChart.setVisibility(View.INVISIBLE);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pieChart = null;
        sCheckInResult = null;
        mAdapter.release();
        mAdapter = null;
    }

    /**
     * start this activity
     */

    public static void start(Context context, CheckInResult checkInResult) {
        Intent intent = new Intent(context, LookCheckInDetailActivity.class);
        context.startActivity(intent);
        sCheckInResult = checkInResult;
    }
}
