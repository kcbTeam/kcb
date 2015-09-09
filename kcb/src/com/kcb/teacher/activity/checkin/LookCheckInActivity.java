package com.kcb.teacher.activity.checkin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.view.common.EmptyTipView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.adapter.checkin.LookCheckInAdapter;
import com.kcb.teacher.database.checkin.CheckInDao;
import com.kcb.teacher.database.checkin.CheckInResult;
import com.kcb.teacher.model.KAccount;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckInResultActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:22:02
 */
public class LookCheckInActivity extends BaseActivity {

    private static final String TAG = LookCheckInActivity.class.getName();

    private ButtonFlat backButton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private View listTitleLayout;
    private ListView listView;

    private EmptyTipView emptyTipView;

    List<CheckInResult> mCheckInResults;
    private LookCheckInAdapter mAdapter;

    public static int mCheckInNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_checkin);
        StatusBarUtil.setTchStatusBarColor(this);

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

        listTitleLayout = findViewById(R.id.layout_listview_title);
        listView = (ListView) findViewById(R.id.listview);

        emptyTipView = (EmptyTipView) findViewById(R.id.emptytipview);
    }

    @Override
    protected void initData() {
        CheckInDao checkInDao = new CheckInDao(LookCheckInActivity.this);
        mCheckInResults = checkInDao.getAll();
        checkInDao.close();

        if (mCheckInResults.isEmpty()) {
            listTitleLayout.setVisibility(View.INVISIBLE);
            emptyTipView.setVisibility(View.VISIBLE);
            emptyTipView.setEmptyText(R.string.tch_no_checkin_result);
        }
        mCheckInNum = mCheckInResults.size();
        mAdapter = new LookCheckInAdapter(LookCheckInActivity.this, mCheckInResults);
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

    /**
     * 刷新，获取签到结果； 根据tchId和本地最近一次的签到时间
     */
    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request =
                new JsonObjectRequest(Method.GET, UrlUtil.getTchCheckinGetresultUrl(KAccount
                        .getAccountId()), new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.i(TAG, "tch get checkin result, response is " + response.toString());
                        // TODO
                        // 解析结果，保存到数据库
                        // 因为后台查询耗时很长，所以结果中只有日期和签到率
                        JSONArray jsonArray = response.optJSONArray("content");
                        List<CheckInResult> results = new ArrayList<CheckInResult>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                CheckInResult checkInResult =
                                        CheckInResult.fromJsonObject(jsonArray.getJSONObject(i));
                                results.add(checkInResult);
                            } catch (JSONException e) {}
                        }

                        // show data
                        if (!results.isEmpty()) {
                            if (mCheckInResults.isEmpty()) {
                                listTitleLayout.setVisibility(View.VISIBLE);
                                emptyTipView.setVisibility(View.GONE);
                            }

                            CheckInDao checkInDao = new CheckInDao(LookCheckInActivity.this);
                            checkInDao.deleteAll();
                            for (CheckInResult result : results) {
                                checkInDao.add(result);
                            }
                            checkInDao.close();
                            mCheckInResults.clear();
                            mCheckInResults.addAll(results);
                            mCheckInNum = mCheckInResults.size();
                            mAdapter.notifyDataSetChanged();
                        }
                        progressBar.hide(LookCheckInActivity.this);
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
        mAdapter.release();
        mAdapter = null;
    }
}
