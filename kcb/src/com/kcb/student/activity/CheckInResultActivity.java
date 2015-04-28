package com.kcb.student.activity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * @className: CheckInResultActivity
 * @description:
 * @author: Ding
 * @date: 2015年4月28日 下午3:01:50
 */
public class CheckInResultActivity extends BaseActivity {

    private int[] colors = new int[] {Color.RED, Color.GREEN};
    private GraphicalView graphicalView;
    private LinearLayout layout;
    private Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_checkinresult);

        initView();
    }

    @Override
    protected void initView() {
        layout = (LinearLayout) findViewById(R.id.linearlayout);
        backbutton = (Button) findViewById(R.id.signinresult);
        graphicalView = execute(this);
        layout.removeAllViews();
        layout.addView(graphicalView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        backbutton.setOnClickListener(this);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public GraphicalView execute(Context context) {
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        CategorySeries categorySeries = new CategorySeries(null);
        categorySeries.add("Sign in", 8);
        categorySeries.add("Not sign in", 2);
        return ChartFactory.getPieChartView(context, categorySeries, renderer);
    }

    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLegendTextSize(50);
        renderer.setLabelsTextSize(30);
        renderer.setClickEnabled(true);
        renderer.setPanEnabled(true);
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

}
