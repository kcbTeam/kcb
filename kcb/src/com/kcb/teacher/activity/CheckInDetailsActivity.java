package com.kcb.teacher.activity;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.teacher.adapter.ListAdapterMissCheckIn;
import com.kcb.teacher.model.StudentInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInDetailsActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月27日 下午11:14:41
 */
public class CheckInDetailsActivity extends BaseFragmentActivity
        implements
            OnChartValueSelectedListener,
            OnClickListener {

    private PieChart mChart;
    private TextView hintTextView;
    private Button backButton;
    private ListView missCheckInListView;

    private String mHintFormat = "本次到课率为%d%%";
    private float mCheckInRate = 0.7f;

    private String[] mInfoStrings = new String[] {"已签", "未签"};
    private List<StudentInfo> mList;
    private ListAdapterMissCheckIn mAdapter;

    private final int mDealy = 150;
    private final int mAnimateDuration = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checkindetails);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        initPieChart();
        hintTextView = (TextView) findViewById(R.id.textview_attendance_rate_hint);
        hintTextView.setText(String.format(mHintFormat, (int) (100 * mCheckInRate)));
        backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        missCheckInListView = (ListView) findViewById(R.id.listview_not_ckeckin_stu);
        mAdapter = new ListAdapterMissCheckIn(this, mList);
        missCheckInListView.setAdapter(mAdapter);
    }



    @Override
    protected void initData() {
        mList = new ArrayList<StudentInfo>();
        mList.clear();
        mList.add(new StudentInfo("123", "123456", 1, 0));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public void onNothingSelected() {}

    @Override
    public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {

    }

    private void initPieChart() {
        mChart = (PieChart) findViewById(R.id.piechart);
        setData(mChart, mInfoStrings);
        setDefaultPieChartStyle(mChart);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mChart.animateY(mAnimateDuration);
                mChart.setVisibility(View.VISIBLE);
            }
        }, mDealy);
    }

    private void setData(PieChart pieChart, String[] infoStrings) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(mCheckInRate, 0));
        yVals1.add(new Entry(1.0f - mCheckInRate, 1));
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 2; i++)
            xVals.add(infoStrings[i % infoStrings.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(192, 255, 140));
        colors.add(Color.GRAY);
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
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setHoleRadius(1f);
        pieChart.setTransparentCircleRadius(2f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setVisibility(View.INVISIBLE);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
    }
}
