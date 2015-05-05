package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.teacher.adapter.ListAdapterSignRecod;
import com.kcb.teacher.model.CheckInRecordInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInResultActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:22:02
 */
// TODO change button to ButtonFlat
public class CheckInResultActivity extends BaseActivity {

    private Button backButton;
    private ListView checkInRecordList;

    private List<CheckInRecordInfo> mList;
    private ListAdapterSignRecod mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checkinresult);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        checkInRecordList = (ListView) findViewById(R.id.listview_sign_record);
        mAdapter = new ListAdapterSignRecod(this, mList);
        checkInRecordList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<CheckInRecordInfo>();
        mList.clear();
        mList.add(new CheckInRecordInfo("2015-01-01", "0%"));
        mList.add(new CheckInRecordInfo("2015-01-02", "0%"));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            finish();
        }
    }
}
