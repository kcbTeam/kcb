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
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
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
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private ListView listView;

    private LookCheckInAdapter mAdapter;

    private GetCheckInResultsTask mGetCheckInResultsTask;

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
        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);
        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        mGetCheckInResultsTask = new GetCheckInResultsTask();
        mGetCheckInResultsTask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_refresh:
                refresh();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LookCheckInDetailActivity.start(LookCheckInActivity.this, mAdapter.getItem(position));
    }

    private class GetCheckInResultsTask extends AsyncTask<Integer, Integer, List<CheckInResult>> {

        @Override
        protected List<CheckInResult> doInBackground(Integer... params) {
            List<CheckInResult> results = new ArrayList<CheckInResult>();
            try {
                CheckInDao checkInDao = new CheckInDao(LookCheckInActivity.this);
                results = checkInDao.getAllCheckInResults();
                checkInDao.close();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(List<CheckInResult> result) {
            super.onPostExecute(result);
            mAdapter = new LookCheckInAdapter(LookCheckInActivity.this, result);
            listView.setAdapter(mAdapter);
        }
    }

    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
