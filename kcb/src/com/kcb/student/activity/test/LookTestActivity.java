package com.kcb.student.activity.test;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.adapter.LookTestAdapter;
import com.kcbTeam.R;

/**
 * 
 * @className: TestResultActivity
 * @description:
 * @author: Ding
 * @date: 2015年5月7日 下午5:09:20
 */
public class LookTestActivity extends BaseActivity implements TextWatcher, OnItemClickListener {

    private ButtonFlat backButton;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private FloatingEditText searchEditText;
    private ImageView clearImageView;

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

        searchEditText = (FloatingEditText) findViewById(R.id.edittext_search);
        searchEditText.addTextChangedListener(this);
        clearImageView = (ImageView) findViewById(R.id.imageview_clear);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        // TODO get data from database;
        mTests = new ArrayList<Test>();
        mTempTests = new ArrayList<Test>();

        for (int i = 0; i < 10; i++) {
            Test test = new Test("导数的意义", 5);
            mTests.add(test);
        }

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

    private void refresh() {
        // TODO get data from server
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        mTempTests.clear();
        mSearchKey = searchEditText.getText().toString().trim().replace(" ", "");
        if (!mSearchKey.equals("")) {
            clearImageView.setVisibility(View.VISIBLE);
            for (int i = 0; i < mTests.size(); i++) {
                String name = mTests.get(i).getName();
                try {
                    if (StringMatchUtil.isMatch(name, mSearchKey)) {
                        mTempTests.add(mTests.get(i));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {}
            }
        } else {
            mTempTests.addAll(mTests);
            clearImageView.setVisibility(View.INVISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
