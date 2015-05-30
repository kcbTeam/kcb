package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

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
import com.kcb.teacher.adapter.ListAdapterMissCheckIn;
import com.kcb.teacher.model.StudentInfo;
import com.kcb.teacher.model.checkin.CheckInResult;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInDetailsActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月27日 下午11:14:41
 */
public class CheckInDetailActivity extends BaseFragmentActivity implements OnClickListener {

    private PieChart mChart;
    private TextView hintTextView;
    private ButtonFlat backButton;
    private ListView missCheckInListView;

    private CheckInResult mCurrentCheckInRecordInfo;
    private float mCheckInRate;

    private String[] mInfoStrings = new String[] {"已签", "未签"};
    private List<StudentInfo> mMissedCheckInStusList;
    private ListAdapterMissCheckIn mAdapter;

    private final int mDealy = 150;
    private final int mAnimateDuration = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checkindetail);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        initPieChart();
        hintTextView = (TextView) findViewById(R.id.textview_attendance_rate_hint);
        hintTextView.setText(String.format(getResources().getString(R.string.checkinRate_hint),
                (int) (100 * mCheckInRate)));
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        missCheckInListView = (ListView) findViewById(R.id.listview_not_ckeckin_stu);
        mAdapter = new ListAdapterMissCheckIn(this, mMissedCheckInStusList);
        missCheckInListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        String flagString = getIntent().getStringExtra("ACTIVITY_TAG");
        mMissedCheckInStusList = new ArrayList<StudentInfo>();
        if (flagString.equals(LookCheckInActivity.TAG)) {
            mCurrentCheckInRecordInfo =
                    (CheckInResult) getIntent().getSerializableExtra(
                            LookCheckInActivity.CURRENT_CHECKIN_RECORD_KEY);
            mMissedCheckInStusList = mCurrentCheckInRecordInfo.getUnCheckedStudentInfos();
            // mCheckInRate = mCurrentCheckInRecordInfo.getRate();
        }
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

    private static final String DATA_CHECKINRESULT = "checkin_result";

    public static void start(Context context, CheckInResult checkInResult) {
        Intent intent = new Intent(context, CheckInDetailActivity.class);
        intent.putExtra(DATA_CHECKINRESULT, checkInResult);
        context.startActivity(intent);
    }
}
