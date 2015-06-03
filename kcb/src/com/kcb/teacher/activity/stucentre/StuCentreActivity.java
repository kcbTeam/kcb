package com.kcb.teacher.activity.stucentre;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.model.stucentre.Student;
import com.kcbTeam.R;

/**
 * 
 * @className: StuDetailsActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月29日 下午2:48:48
 */
public class StuCentreActivity extends BaseActivity {

    private ButtonFlat backButton;
    private TextView nameIdTextView;

    private PieChart mCheckInPieChart;
    private PieChart mCorrectPieChart;

    private TextView checkInRateTextView;
    private TextView testRateTextView;

    private Student mStudentInfo;

    private float mCheckInRate;
    private float mCorrectRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_stucentre);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        nameIdTextView = (TextView) findViewById(R.id.textview_name_id);

        checkInRateTextView = (TextView) findViewById(R.id.textview_stu_checkinrate);
        testRateTextView = (TextView) findViewById(R.id.textview_stu_correctrate);

        mCheckInPieChart = (PieChart) findViewById(R.id.piechart_checkinrate);
        mCorrectPieChart = (PieChart) findViewById(R.id.piechart_correctrate);
    }

    @Override
    protected void initData() {
        mStudentInfo = (Student) getIntent().getSerializableExtra(DATA_STUDENT);
        mCheckInRate = (float) mStudentInfo.getCheckInRate();
        mCorrectRate = (float) mStudentInfo.getCorrectRate();

        nameIdTextView.setText(mStudentInfo.getName() + "（" + mStudentInfo.getId() + "）");
        checkInRateTextView.setText(String.format(
                getResources().getString(R.string.checkin_rate_format), mStudentInfo.getName(),
                (int) (100 * mCheckInRate)));
        testRateTextView.setText(String.format(
                getResources().getString(R.string.correct_rate_format), mStudentInfo.getName(),
                (int) (100 * mCorrectRate)));

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
        setData(mCheckInPieChart, new String[] {"到课", "缺课"}, mCheckInRate);
        setData(mCorrectPieChart, new String[] {"正确", "错误"}, mCorrectRate);
        defaultPieChartStyle(mCheckInPieChart);
        defaultPieChartStyle(mCorrectPieChart);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mCheckInPieChart.animateY(1800);
                mCorrectPieChart.animateY(1800);
                mCheckInPieChart.setVisibility(View.VISIBLE);
                mCorrectPieChart.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    private void setData(PieChart pieChart, String[] infoStrings, float mult) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(mult, 0));
        yVals1.add(new Entry(1.0f - mult, 1));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            xVals.add(infoStrings[i % infoStrings.length]);
        }

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

    private void defaultPieChartStyle(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(false);
        pieChart.setClickable(false);
        pieChart.setTouchEnabled(false);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);
        pieChart.setVisibility(View.INVISIBLE);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
    }

    /**
     * start
     */
    private static final String DATA_STUDENT = "data_student";

    public static void start(Context context, Student student) {
        Intent intent = new Intent(context, StuCentreActivity.class);
        intent.putExtra(DATA_STUDENT, student);
        context.startActivity(intent);
    }
}
