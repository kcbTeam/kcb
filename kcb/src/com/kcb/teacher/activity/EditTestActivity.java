package com.kcb.teacher.activity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.PaperButton;
import com.kcbTeam.R;

public class EditTestActivity extends BaseActivity {

    PaperButton lastItem;
    PaperButton nextItem;
    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edittest);
        initView();
    }

    @Override
    protected void initView() {
        lastItem = (PaperButton) findViewById(R.id.pagerbutton_last);
        nextItem = (PaperButton) findViewById(R.id.pagerbutton_next);
        nextItem.setTextColor(getResources().getColor(R.color.black));
        lastItem.setTextColor(getResources().getColor(R.color.gray));
    }

    @Override
    protected void initData() {}
}
