package com.kcb.teacher.activity.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterCourseTest;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckTest
 * @description:
 * @author: ZQJ
 * @date: 2015年5月16日 下午4:00:49
 */
public class LookTestActivity extends BaseActivity implements TextWatcher, OnItemClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "CheckTest";

    private ButtonFlat backButton;
    private FloatingEditText searchEditText;
    private ListView testListView;

    private List<Test> mTestList;
    private List<Test> mTempTestList;
    private ListAdapterCourseTest mAdapter;

    public final static String CLICKED_TEST_KEY = "clicked_test_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checktest);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        searchEditText = (FloatingEditText) findViewById(R.id.edittext_search);
        searchEditText.addTextChangedListener(this);
        testListView = (ListView) findViewById(R.id.listview_test);
    }

    @Override
    protected void initData() {
        /**
         * test data
         */

        // TODO getText from local

        mTestList = new ArrayList<Test>();
        Test tempTest = new Test("高考数学", 2);
        tempTest.setDate(new Date());
        tempTest.addQuestion();
        tempTest.getQuestion(0).getTitle().setText("1+1=?");
        tempTest.getQuestion(0).getChoiceA().setText("1");
        tempTest.getQuestion(0).getChoiceB().setText("2");
        tempTest.getQuestion(0).getChoiceB().setIsRight(true);
        tempTest.getQuestion(0).getChoiceC().setText("3");
        tempTest.getQuestion(0).getChoiceD().setText("4");
        mTestList.add(tempTest);

        tempTest = new Test("影视艺术赏析", 1);
        tempTest.setDate(new Date());
        tempTest.addQuestion();
        tempTest.getQuestion(0).getTitle().setText("乱世佳人是根据哪部小说改编而来的？");
        tempTest.getQuestion(0).getChoiceA().setText("战争与和平");
        tempTest.getQuestion(0).getChoiceB().setText("安娜卡列尼娜");
        tempTest.getQuestion(0).getChoiceC().setText("飘");
        tempTest.getQuestion(0).getChoiceC().setIsRight(true);
        tempTest.getQuestion(0).getChoiceD().setText("复活");
        mTestList.add(tempTest);


        mTempTestList = new ArrayList<Test>();
        mTempTestList.addAll(mTestList);

        mAdapter = new ListAdapterCourseTest(this, mTempTestList);
        testListView.setAdapter(mAdapter);
        testListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, LookTestDetailActivity.class);
        intent.putExtra(LookTestActivity.CLICKED_TEST_KEY,
                mTempTestList.get(mTempTestList.size() - 1 - position));
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        mTempTestList.clear();
        mTempTestList.addAll(mTestList);
        String searchContent = searchEditText.getText().toString();
        for (int i = 0; i < mTempTestList.size(); i++) {
            String name = mTempTestList.get(i).getName();
            try {
                if (StringMatchUtil.isMatch(name, searchContent)) {
                    continue;
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            mTempTestList.remove(i);
            i--;
        }
        mAdapter.notifyDataSetChanged();
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
}
