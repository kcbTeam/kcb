package com.kcb.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.parallaxview.ParallaxScrollView;
import com.kcbTeam.R;

public class SubmitTest extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "SubmitTestPaper";

    private ParallaxScrollView testContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_submittest);
        initView();
    }

    @Override
    protected void initView() {
        testContent = (ParallaxScrollView) findViewById(R.id.scrollView);
        View view = View.inflate(this, R.layout.tch_scrollview_item, null);
        TextView item1 = (TextView) view.findViewById(R.id.textview);
        item1.setText("1");
        testContent.addView(view);
        view = View.inflate(this, R.layout.tch_scrollview_item, null);
        item1 = (TextView) view.findViewById(R.id.textview);
        item1.setText("2");
        testContent.addView(view);
    }

    @Override
    protected void initData() {}
}
