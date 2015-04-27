package com.kcb.teacher.activity;


import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
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

    private String mHintFormat = "本次到课率为%d%%";
    private int mCheckInRate = 45;

    private String[] mParties = new String[] {"已签", "未签"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checkindetails);
        initView();
    }

    @Override
    protected void initView() {
        initPieChart();
        hintTextView = (TextView) findViewById(R.id.textview_attendance_rate_hint);
        hintTextView.setText(String.format(mHintFormat, mCheckInRate));
        backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void initData() {}

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
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);

        mChart.setHoleRadius(1f);
        mChart.setTransparentCircleRadius(2f);
        mChart.setHoleColor(Color.WHITE);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        setData(1, mCheckInRate);
        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        //
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        // l.setPosition(LegendPosition.PIECHART_CENTER);
        // l.setXEntrySpace(7f);
        // l.setYEntrySpace(5f);

    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(mult / 100, 0));
        yVals1.add(new Entry((float) (1 - mult / 100), 1));
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        // for (int i = 0; i < count + 1; i++) {
        // yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        // }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(192, 255, 140));
        colors.add(Color.GRAY);
        // for (int c : ColorTemplate.VORDIPLOM_COLORS)
        // colors.add(c);

        // for (int c : ColorTemplate.JOYFUL_COLORS)
        // colors.add(c);
        //
        // for (int c : ColorTemplate.COLORFUL_COLORS)
        // colors.add(c);
        //
        // for (int c : ColorTemplate.LIBERTY_COLORS)
        // colors.add(c);
        //
        // for (int c : ColorTemplate.PASTEL_COLORS)
        // colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}
