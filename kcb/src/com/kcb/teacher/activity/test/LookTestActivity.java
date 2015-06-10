package com.kcb.teacher.activity.test;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
import com.kcb.common.model.answer.TestAnswer;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.common.view.EmptyTipView;
import com.kcb.common.view.SearchEditText;
import com.kcb.common.view.SearchEditText.OnSearchListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.adapter.LookTestAdapter;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.model.account.KAccount;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckTest
 * @description:
 * @author: ZQJ
 * @date: 2015年5月16日 下午4:00:49
 */
public class LookTestActivity extends BaseActivity implements OnSearchListener, OnItemClickListener {

    private static final String TAG = LookTestActivity.class.getName();

    private ButtonFlat backButton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private SearchEditText searchEditText;

    private View listTitleLayout;
    private ListView listView;

    private EmptyTipView emptyTipView;

    private LookTestAdapter mAdapter;

    private List<Test> mAllTests;
    private List<Test> mSearchedTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_test);

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

        searchEditText = (SearchEditText) findViewById(R.id.searchedittext);
        searchEditText.setOnSearchListener(this);
        searchEditText.setHint(R.string.tch_input_test_name_search);

        listTitleLayout = findViewById(R.id.layout_listview_title);
        listView = (ListView) findViewById(R.id.listview_test);

        emptyTipView = (EmptyTipView) findViewById(R.id.emptytipview);
    }

    @Override
    protected void initData() {
        TestDao testDao = new TestDao(LookTestActivity.this);
        mAllTests = testDao.getHasTested();
        testDao.close();

        if (mAllTests.isEmpty()) {
            listTitleLayout.setVisibility(View.INVISIBLE);
            emptyTipView.setVisibility(View.VISIBLE);
            emptyTipView.setEmptyText(R.string.tch_no_test_result);
        }

        mSearchedTests = new ArrayList<Test>();
        mSearchedTests.addAll(mAllTests);

        mAdapter = new LookTestAdapter(this, mSearchedTests);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
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
     * search listener
     */
    @Override
    public void onSearch(String text) {
        if (mAllTests.isEmpty()) {
            return;
        }
        mSearchedTests.clear();
        mSearchedTests.addAll(mAllTests);
        for (int i = 0; i < mSearchedTests.size(); i++) {
            String name = mSearchedTests.get(i).getName();
            try {
                if (StringMatchUtil.isMatch(name, text)) {
                    continue;
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            mSearchedTests.remove(i);
            i--;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClear() {
        if (mAllTests.isEmpty()) {
            return;
        }
        mSearchedTests.clear();
        mSearchedTests.addAll(mAllTests);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LookTestDetailActivity.start(LookTestActivity.this, mAdapter.getItem(position));
    }

    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request =
                new JsonArrayRequest(Method.GET, UrlUtil.getTchTestLookresultUrl(KAccount
                        .getAccountId()), new Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // get answer
                        List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                testAnswers.add(TestAnswer.fromJsonObject(response.getJSONObject(i)));
                            } catch (JSONException e) {}
                        }
                        // set answer
                        for (Test test : mAllTests) {
                            for (TestAnswer testAnswer : testAnswers) {
                                if (test.getId().equals(testAnswer.getId())) {
                                    test.setAnswer(testAnswer);
                                }
                            }
                        }
                        // save test
                        TestDao testDao = new TestDao(LookTestActivity.this);
                        for (Test test : mAllTests) {
                            testDao.add(test);
                        }
                        testDao.close();
                        // switch view
                        progressBar.hide(LookTestActivity.this);
                        if (!mAllTests.isEmpty()) {
                            listTitleLayout.setVisibility(View.VISIBLE);
                            emptyTipView.setVisibility(View.GONE);
                        }
                        // show test
                        mSearchedTests.clear();
                        mSearchedTests.addAll(mAllTests);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(LookTestActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        searchEditText.release();
        searchEditText = null;
        mAdapter.release();
        mAdapter = null;
        for (Test test : mAllTests) {
            test.release();
        }
        mAllTests = null;
        mSearchedTests = null;
    }
}
