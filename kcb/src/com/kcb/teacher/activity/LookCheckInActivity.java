package com.kcb.teacher.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.LookCheckInAdapter;
import com.kcb.teacher.database.checkin.CheckInDao;
import com.kcb.teacher.model.checkin.CheckInResult;
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
    List<CheckInResult> mCheckInResults = new ArrayList<CheckInResult>();
    private CheckInDao mCheckInDao;
    private GetCheckInResultsTask mGetCheckInResultsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_checkin);

        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGetCheckInResultsTask = new GetCheckInResultsTask();
        mGetCheckInResultsTask.execute(0);
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
        mCheckInDao = new CheckInDao(this);
        // TODO I don't know why Asynctask didn't work..
        try {
            mCheckInResults = mCheckInDao.getAllCheckInResults();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new LookCheckInAdapter(this, mCheckInResults);
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

    private class GetCheckInResultsTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                mCheckInResults = mCheckInDao.getAllCheckInResults();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (null != mAdapter) {
                mAdapter.notifyDataSetChanged();
            }
        }

    }
}
