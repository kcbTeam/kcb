package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterSignRecod;
import com.kcb.teacher.model.CheckInRecordInfo;
import com.kcb.teacher.model.StudentInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInResultActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:22:02
 */
public class LookCheckInActivity extends BaseActivity implements OnItemClickListener {

    public final static String TAG = "CheckInResultActivity";
    public final static String CURRENT_CHECKIN_RECORD_KEY = "current_clicked_record";

    private ButtonFlat backButton;
    private ListView checkInRecordList;

    private List<CheckInRecordInfo> mList;
    private ListAdapterSignRecod mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_lookcheckin);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        checkInRecordList = (ListView) findViewById(R.id.listview_sign_record);
        checkInRecordList.setOnItemClickListener(this);
        mAdapter = new ListAdapterSignRecod(this, mList);
        checkInRecordList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<CheckInRecordInfo>();
        mList.clear();
        // test data.mList is a CheckInRecord list,and one CheckInRecord object
        // contains a
        // StudentInfo list which record the missed CheckIn students
        List<StudentInfo> missedCheckInStus = new ArrayList<StudentInfo>();
        missedCheckInStus.add(new StudentInfo("testName1", "00001", 12, 10));
        mList.add(new CheckInRecordInfo("2015-01-01", 0.7f, missedCheckInStus));
        List<StudentInfo> missedCheckInStus1 = new ArrayList<StudentInfo>();
        missedCheckInStus1.add(new StudentInfo("testName2", "0002", 12, 9));
        mList.add(new CheckInRecordInfo("2015-01-02", 0.5f, missedCheckInStus1));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CheckInDetailsActivity.class);
        intent.putExtra(CURRENT_CHECKIN_RECORD_KEY, mList.get(position));
        intent.putExtra("ACTIVITY_TAG", TAG);
        startActivity(intent);
    }
}
