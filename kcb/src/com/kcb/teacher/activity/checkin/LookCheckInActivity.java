package com.kcb.teacher.activity.checkin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.adapter.LookCheckInAdapter;
import com.kcb.teacher.database.checkin.CheckInDao;
import com.kcb.teacher.model.account.KAccount;
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

    private static final String TAG = LookCheckInActivity.class.getName();

    private ButtonFlat backButton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private ListView listView;

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
        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);
        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        CheckInDao checkInDao = new CheckInDao(LookCheckInActivity.this);
        List<CheckInResult> results = checkInDao.getAll();
        checkInDao.close();
        mAdapter = new LookCheckInAdapter(LookCheckInActivity.this, results);
        listView.setAdapter(mAdapter);
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

    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request =
                new JsonArrayRequest(Method.GET, UrlUtil.getTchCheckinGetresultUrl(KAccount
                        .getAccountId()), new Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.hide(LookCheckInActivity.this);
                        List<CheckInResult> results = new ArrayList<CheckInResult>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                CheckInResult checkInResult =
                                        CheckInResult.fromJsonObject(response.getJSONObject(i));
                                results.add(checkInResult);
                            } catch (JSONException e) {}
                        }
                        mAdapter.setData(results);
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(LookCheckInActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
    }
}
