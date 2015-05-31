package com.kcb.student.activity;

import java.util.ArrayList;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckResultActivity
 * @description: look checkin result
 * @author: Ding
 * @date: 2015年5月5日 下午3:45:54
 */
public class LookCheckInActivity extends BaseActivity implements OnChartValueSelectedListener {

    private ButtonFlat backbutton;
    private ButtonFlat refreshButton;

    private TextView rateTextView;

    private PieChart mChart;
    private String[] mParties = new String[] {"签到", "未签到"};
    private float[] quarterly = new float[] {80, 20};

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_look_checkin);

        initView();
    }

    @Override
    protected void initView() {
        backbutton = (ButtonFlat) findViewById(R.id.button_back);
        backbutton.setOnClickListener(this);
        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);

        rateTextView = (TextView) findViewById(R.id.textview_rate);
        showCheckInRate();

        mChart = (PieChart) findViewById(R.id.piechart_rate);

        PieData mPieData = setData(2, 100);
        showCheckInChart(mChart, mPieData);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_refresh:
                refreshRate();
                break;
            default:
                break;
        }
    }

    private void refreshRate() {

    }

    private void showCheckInRate() {
        rateTextView.setText(String.format(getString(R.string.stu_checkin_rate), 10, 8, "0.8"));
    }

    private void showCheckInChart(PieChart pieChart, PieData pieData) {
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
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.setCenterText("");

        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();
        mLegend.setEnabled(false);

        pieChart.animateXY(1800, 1800);
    }

    private PieData setData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xValues.add(mParties[i]);
        }
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues.add(new Entry(quarterly[i], i));
        }

        PieDataSet pieDataSet = new PieDataSet(yValues, null);
        pieDataSet.setSliceSpace(0f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(192, 255, 140));
        colors.add(Color.GRAY);
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px);
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueTextColor(Color.BLUE);
        pieDataSet.setValueFormatter(new PercentFormatter());
        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }

    @Override
    public void onNothingSelected() {}

    @Override
    public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {}
}
