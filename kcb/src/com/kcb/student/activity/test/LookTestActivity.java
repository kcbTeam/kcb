package com.kcb.student.activity.test;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.common.view.SearchEditText;
import com.kcb.common.view.SearchEditText.SearchListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.adapter.LookTestAdapter;
import com.kcb.student.database.test.TestDao;
import com.kcbTeam.R;

/**
 * 
 * @className: TestResultActivity
 * @description:
 * @author: Ding
 * @date: 2015年5月7日 下午5:09:20
 */
public class LookTestActivity extends BaseActivity implements SearchListener, OnItemClickListener {

    private ButtonFlat backButton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private SearchEditText searchEditText;

    private ListView listView;
    private LookTestAdapter mAdapter;

    private List<Test> mTests;
    private List<Test> mTempTests;
    private String mSearchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_look_test);

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
        searchEditText.setHint(R.string.stu_input_test_name_search);

        listView = (ListView) findViewById(R.id.listview_test);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        TestDao testDao = new TestDao(LookTestActivity.this);
        mTests = testDao.getAll();
        testDao.close();

        mTempTests = new ArrayList<Test>();
        mTempTests.addAll(mTests);

        mAdapter = new LookTestAdapter(this, mTempTests);
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
     * search listener
     */
    @Override
    public void onSearch(String text) {
        mTempTests.clear();
        for (int i = 0; i < mTests.size(); i++) {
            String name = mTests.get(i).getName();
            try {
                if (StringMatchUtil.isMatch(name, mSearchKey)) {
                    mTempTests.add(mTests.get(i));
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {}
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClear() {
        mTempTests.clear();
        mTempTests.addAll(mTests);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LookTestDetailActivity.start(LookTestActivity.this, mAdapter.getItem(position));
    }

    // TODO
    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }
}
