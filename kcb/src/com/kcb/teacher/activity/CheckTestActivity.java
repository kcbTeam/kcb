package com.kcb.teacher.activity;

import java.util.ArrayList;
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
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterCourseTest;
import com.kcb.teacher.model.CourseTest;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.util.NameUtils;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckTest
 * @description:
 * @author: ZQJ
 * @date: 2015年5月16日 下午4:00:49
 */
public class CheckTestActivity extends BaseActivity implements TextWatcher, OnItemClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "CheckTest";

    private ButtonFlat backButton;
    private FloatingEditText searchEditText;
    private ListView testListView;

    private List<CourseTest> mTestList;
    private List<CourseTest> mTempTestList;
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
        List<Question> questionList = new ArrayList<Question>();
        // questionList.add(new Question(new QuestionItem("一年有可能多少天呢"), new QuestionItem("365"),
        // new QuestionItem("366"), new QuestionItem("367"), new QuestionItem("368"),
        // new boolean[] {true, true, false, false}));
        // questionList.add(new Question(new QuestionItem("天上有多少颗星星"), new QuestionItem("1"),
        // new QuestionItem("5"), new QuestionItem("10"), new QuestionItem("数不清"), new boolean[] {
        // false, false, false, true}));
        //
        List<Question> questionList1 = new ArrayList<Question>();
        // questionList1.add(new Question(new QuestionItem("1 + 1 = ?"), new QuestionItem("1"),
        // new QuestionItem("11"), new QuestionItem("2"), new QuestionItem("111"), new boolean[] {
        // false, false, true, false}));
        // questionList1.add(new Question(new QuestionItem("天上有多少颗星星"), new QuestionItem("1"),
        // new QuestionItem("5"), new QuestionItem("10"), new QuestionItem("数不清"), new boolean[] {
        // false, false, false, true}));
        // questionList1.add(new Question(new QuestionItem("一年有可能多少天呢"), new QuestionItem("365"),
        // new QuestionItem("366"), new QuestionItem("367"), new QuestionItem("368"),
        // new boolean[] {true, true, false, false}));
        mTestList = new ArrayList<CourseTest>();
        mTestList.add(new CourseTest("高考数学", questionList, 300, "2015-6-5"));
        mTestList.add(new CourseTest("考研数学", questionList1, 600, "2016-1-5"));

        mTempTestList = new ArrayList<CourseTest>();
        mTempTestList.addAll(mTestList);

        mAdapter = new ListAdapterCourseTest(this, mTempTestList);
        testListView.setAdapter(mAdapter);
        testListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CheckTestDetailsActivity.class);
        intent.putExtra(CheckTestActivity.CLICKED_TEST_KEY,
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
            String name = mTempTestList.get(i).getTestName();
            try {
                if (NameUtils.isMatch(name, searchContent)) {
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
