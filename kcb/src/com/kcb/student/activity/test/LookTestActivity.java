package com.kcb.student.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.cache.CacheSP;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.common.view.common.EmptyTipView;
import com.kcb.common.view.common.SearchEditText;
import com.kcb.common.view.common.SearchEditText.OnSearchListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.adapter.LookTestAdapter;
import com.kcb.student.model.KAccount;
import com.kcbTeam.R;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: TestResultActivity
 * @description:
 * @author: Ding
 * @date: 2015年5月7日 下午5:09:20
 */
public class LookTestActivity extends BaseActivity implements OnSearchListener {

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
    private String mSearchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_look_test);
        StatusBarUtil.setStuStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        backButton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));

        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);
        refreshButton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));

        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        searchEditText = (SearchEditText) findViewById(R.id.searchedittext);
        searchEditText.setOnSearchListener(this);
        searchEditText.setHint(R.string.stu_input_test_name_search);

        listTitleLayout = findViewById(R.id.layout_listview_title);
        listView = (ListView) findViewById(R.id.listview_test);

        emptyTipView = (EmptyTipView) findViewById(R.id.emptytipview);
    }

    @Override
    protected void initData() {
//        TestDao testDao = new TestDao(LookTestActivity.this);
//        mAllTests = testDao.getAll();
//        testDao.close();
        mAllTests = new ArrayList<Test>();
        JSONObject response = CacheSP.getStuTestListJson()
                .optJSONObject("resultCon");
        if(null != response){
            JSONArray testArray = response.optJSONArray("testLog");
            for (int i = 0; i < testArray.length(); i++) {
                Test test = Test.fromJsoSimple(testArray.optJSONObject(i));
                mAllTests.add(test);
            }
        }

        if (mAllTests.isEmpty()) {
            listTitleLayout.setVisibility(View.INVISIBLE);
            searchEditText.setVisibility(View.INVISIBLE);
            emptyTipView.setVisibility(View.VISIBLE);
            emptyTipView.setEmptyText(R.string.stu_no_test_result);
        }

        mSearchedTests = new ArrayList<Test>();
        mSearchedTests.addAll(mAllTests);

        mAdapter = new LookTestAdapter(this, mSearchedTests);
        listView.setAdapter(mAdapter);

        if (mAllTests.isEmpty()) {
            listTitleLayout.setVisibility(View.INVISIBLE);
        }
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
        mSearchedTests.clear();
        for (int i = 0; i < mAllTests.size(); i++) {
            String name = mAllTests.get(i).getName();
            try {
                if (StringMatchUtil.isMatch(name, text)) {
                    mSearchedTests.add(mAllTests.get(i));
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClear() {
        mSearchedTests.clear();
        mSearchedTests.addAll(mAllTests);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新，根据学号、本地保存的最近一次测试时间戳获取测试； 将获取到的内容存到数据库，刷新UI；
     */
    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        long date = 0;
        if (mAdapter.getCount() > 0) {
            date = mAdapter.getItem(0).getDate();
        }
        JsonObjectRequest request =
                new JsonObjectRequest(Method.GET, UrlUtil.getStuTestLookResultUrl(
                        KAccount.getAccountId()), new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 获取测试，并存到数据库中；
                        JSONObject jsonObject = response.optJSONObject("resultCon");
                        List<Test> tests = new ArrayList<Test>();
                        if(null != jsonObject){
                            JSONArray testArray = jsonObject.optJSONArray("testLog");
                            for (int i = 0; i < testArray.length(); i++) {
                                //                            Test test = Test.fromJsonObject(response.optJSONObject(i));
                                Test test = Test.fromJsoSimple(testArray.optJSONObject(i));
                                tests.add(test);
                            }
                        }
                        // 刷新UI；
                        if (!tests.isEmpty()) {
                            mAllTests.clear();
                            mAllTests.addAll(tests);
                            mSearchedTests.clear();
                            mSearchedTests.addAll(mAllTests);
                            mAdapter.notifyDataSetChanged();
                            searchEditText.setText(""); // 清空搜索框内容
                            listTitleLayout.setVisibility(View.VISIBLE);
                            searchEditText.setVisibility(View.VISIBLE);
                            emptyTipView.setVisibility(View.INVISIBLE);
                        }
                        progressBar.hide(LookTestActivity.this);
                        CacheSP.saveStuTestListJson(response.toString());
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
        searchEditText.release();
        mAdapter.release();
        mAdapter = null;
        for (Test test : mAllTests) {
            test.release();
        }
        mAllTests = null;
        mSearchedTests = null;
    }
}
