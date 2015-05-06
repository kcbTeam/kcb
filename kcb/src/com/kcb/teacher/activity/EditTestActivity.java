package com.kcb.teacher.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageButton;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.fragment.EditTestFragment;
import com.kcbTeam.R;

public class EditTestActivity extends BaseFragmentActivity {

    PaperButton lastItem;
    PaperButton nextItem;
    ImageButton deleteButton;
    EditTestFragment mEditTestFragment;
    
    private FragmentManager mFragmentManager;
    
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
        mFragmentManager = getSupportFragmentManager();
        mEditTestFragment = new EditTestFragment();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_content, mEditTestFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void initData() {}
}
