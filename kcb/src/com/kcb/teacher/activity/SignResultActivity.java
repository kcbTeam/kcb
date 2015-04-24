package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.SignRecordInfo;
import com.kcb.teacher.adapter.ListAdapterSignRecod;
import com.kcbTeam.R;

/**
 * 
 * @className: SignResultActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:22:02
 */
public class SignResultActivity extends BaseActivity {

    private Button backButton;
    private ListView signRecordList;

    private List<SignRecordInfo> mList;
    private ListAdapterSignRecod mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_signresult);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        signRecordList = (ListView) findViewById(R.id.listview_sign_record);
        mAdapter = new ListAdapterSignRecod(this, mList);
        signRecordList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<SignRecordInfo>();
        mList.clear();
        mList.add(new SignRecordInfo("签到日期", "签到率"));
        mList.add(new SignRecordInfo("2015-01-01", "0%"));
        mList.add(new SignRecordInfo("2015-01-02", "0%"));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            finish();
        }
    }
}
