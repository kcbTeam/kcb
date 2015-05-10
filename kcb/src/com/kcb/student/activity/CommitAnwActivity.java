package com.kcb.student.activity;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.observablescrollview.ObservableScrollView;
import com.kcb.library.view.observablescrollview.ParallaxScrollView;
import com.kcbTeam.R;

public class CommitAnwActivity extends BaseActivity {


    private ParallaxScrollView mScrollView;
    private ButtonFlat commitButton;
    private List<View> questionViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_commitanw);

        initView();
    }


    @Override
    protected void initView() {
        questionViews=new ArrayList<View>();
        mScrollView = (ParallaxScrollView) findViewById(R.id.scroll_view);
        LinearLayout linearLayoutView1 = (LinearLayout) findViewById(R.id.scroll_view1);
        View view = getLayoutInflater().inflate(R.layout.stu_item_questiontext, null);
        view.setOnClickListener(this);
        questionViews.add(view);
        linearLayoutView1.addView(view);
        commitButton = (ButtonFlat) findViewById(R.id.button_commit);
        commitButton.setOnClickListener(this);
        commitButton.setRippleSpeed(6f);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
       
        }
    }
}
