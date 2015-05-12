package com.kcb.teacher.activity;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.kcb.teacher.fragment.StuCentreFragment;
import com.kcb.teacher.model.StudentInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: StuDetailsActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月29日 下午2:48:48
 */
public class StuDetailsActivity extends BaseActivity {

	private PieChart mCheckInPieChart;
	private PieChart mCorrectPieChart;

	private TextView titleText;
	private TextView checkInRateText;
	private TextView correctRateText;

	private ButtonFlat backButton;

	private StudentInfo mCurrentStuInfo;

	private String[] mCheckInStrings = new String[] { "到课", "缺课" };
	private String[] mCorrectStrings = new String[] { "正确", "错误" };

	private float mCheckInRate;
	private float mCorrectRate;

	private final int mDelay = 150;
	private final int mAnimateDuration = 1800;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tch_activity_studetails);
		initData();
		initView();
	}

	@Override
	protected void initData() {
		initCurrentStu();
	}

	@Override
	protected void initView() {
		titleText = (TextView) findViewById(R.id.textview_title);
		checkInRateText = (TextView) findViewById(R.id.textview_stu_checkinrate);
		correctRateText = (TextView) findViewById(R.id.textview_stu_correctrate);
		titleText.setText(String.format(
				getResources().getString(R.string.studetails_title_format),
				mCurrentStuInfo.getStudentName()));
		checkInRateText.setText(String.format(
				getResources().getString(R.string.checkin_rate_format),
				mCurrentStuInfo.getStudentName(), (int) (100 * mCheckInRate)));
		correctRateText.setText(String.format(
				getResources().getString(R.string.correct_rate_format),
				mCurrentStuInfo.getStudentName(), (int) (100 * mCorrectRate)));

		backButton = (ButtonFlat) findViewById(R.id.button_back);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		initPieChart();
	}

	private void initCurrentStu() {
		mCurrentStuInfo = (StudentInfo) getIntent().getSerializableExtra(
				StuCentreFragment.CURRENT_STU_KEY);
		mCheckInRate = 1.0f - (float) mCurrentStuInfo.getMissTimes()
				/ mCurrentStuInfo.getCheckInTimes();
		mCorrectRate = 0.8f;
	}

	private void initPieChart() {
		mCheckInPieChart = (PieChart) findViewById(R.id.piechart_checkinrate);
		mCorrectPieChart = (PieChart) findViewById(R.id.piechart_correctrate);
		setData(mCheckInPieChart, mCheckInStrings, mCheckInRate);
		setData(mCorrectPieChart, mCorrectStrings, mCorrectRate);
		defaultPieChartStyle(mCheckInPieChart);
		defaultPieChartStyle(mCorrectPieChart);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				mCheckInPieChart.animateY(mAnimateDuration);
				mCorrectPieChart.animateY(mAnimateDuration);
				mCheckInPieChart.setVisibility(View.VISIBLE);
				mCorrectPieChart.setVisibility(View.VISIBLE);
			}
		}, mDelay);

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

}
