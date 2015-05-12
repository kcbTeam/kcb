package com.kcb.student.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckResultActivity
 * @description:
 * @author: Ding
 * @date: 2015年5月5日 下午3:45:54
 */
// TODO delete top-right tip;
// TODO change colors and size;
public class CheckInResultActivity extends BaseActivity implements
		OnChartValueSelectedListener {

	private ButtonFlat backbutton;
	private PieChart mChart;
	private String[] mParties = new String[] { "Sign", "Not Sign" };
	private float[] quarterly = new float[] { 80, 20 };

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_activity_checkinresult);

		initView();
	}

	@Override
	protected void initView() {
		backbutton = (ButtonFlat) findViewById(R.id.button_back);
		backbutton.setOnClickListener(this);

		mChart = (PieChart) findViewById(R.id.chart);

		PieData mPieData = setData(2, 100);
		showChart(mChart, mPieData);
	}

	@Override
	protected void initData() {
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	private void showChart(PieChart pieChart, PieData pieData) {
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

		pieChart.setCenterText("MPAndroidChart\nby Philipp Jahoda");

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
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		pieDataSet.setColors(colors);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = 5 * (metrics.densityDpi / 160f);
		pieDataSet.setSelectionShift(px);
		PieData pieData = new PieData(xValues, pieDataSet);
		return pieData;
	}

	@Override
	public void onNothingSelected() {
	}

	@Override
	public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {
	}
}
