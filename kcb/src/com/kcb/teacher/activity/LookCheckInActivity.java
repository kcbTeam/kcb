package com.kcb.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.LookCheckInAdapter;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInResultActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:22:02
 */
public class LookCheckInActivity extends BaseActivity implements OnItemClickListener {

    private ButtonFlat backButton;
    private ListView checkInRecordList;

    private LookCheckInAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_checkin);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        checkInRecordList = (ListView) findViewById(R.id.listview);
        checkInRecordList.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        // TODO get checkin result from db;
        mAdapter = new LookCheckInAdapter(this, null);
        checkInRecordList.setAdapter(mAdapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LookCheckInDetailActivity.start(LookCheckInActivity.this, mAdapter.getItem(position));
    }
}
