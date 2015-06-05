package com.kcb.teacher.activity.checkin;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.LookCheckInDetailAdapter;
import com.kcb.teacher.model.checkin.CheckInResult;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInDetailsActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月27日 下午11:14:41
 */
public class LookCheckInDetailActivity extends BaseFragmentActivity implements OnClickListener {

    private ButtonFlat backButton;
    private TextView dateTextView;
    private TextView rateTextView;

    private PieChart pieChart;

    private ListView listView;

    private CheckInResult mCheckInResult;
    private float mCheckInRate;

    private LookCheckInDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_checkin_detail);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        dateTextView = (TextView) findViewById(R.id.textview_date);
        rateTextView = (TextView) findViewById(R.id.textview_rate);

        pieChart = (PieChart) findViewById(R.id.piechart);
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void initData() {
        mCheckInResult = (CheckInResult) getIntent().getSerializableExtra(DATA_CHECKIN_RESULT);
        mCheckInRate = (float) mCheckInResult.getRate();

        dateTextView.setText(mCheckInResult.getDate().toString());
        rateTextView.setText(String.format(getResources().getString(R.string.tch_checkin_rate_tip),
                (int) (100 * mCheckInRate)));

        mAdapter = new LookCheckInDetailAdapter(this, mCheckInResult.getUnCheckedStudents());
        listView.setAdapter(mAdapter);

        initPieChart();
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

        PieDataSet dataSet = new PieDataSet(yVals1, " ");
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

    /**
     * start this activity
     */
    private static final String DATA_CHECKIN_RESULT = "data_checkin_result";

    public static void start(Context context, CheckInResult checkInResult) {
        Intent intent = new Intent(context, LookCheckInDetailActivity.class);
        intent.putExtra(DATA_CHECKIN_RESULT, checkInResult);
        context.startActivity(intent);
    }
}
