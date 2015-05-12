package com.kcb.teacher.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterQuestions;
import com.kcb.teacher.model.ChoiceQuestion;
import com.kcb.teacher.model.CourseTest;
import com.kcbTeam.R;

public class SubmitTest extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "SubmitTestPaper";

    private ListView questionListView;
    private ListAdapterQuestions mAdapter;
    private List<ChoiceQuestion> mList;
    private CourseTest mCurrentTest;

    private TextView testName;
    private SeekBar testTime;

    private ButtonFlat submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_submittest);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        questionListView = (ListView) findViewById(R.id.listview_questions);
        mAdapter = new ListAdapterQuestions(this, mList);
        questionListView.setAdapter(mAdapter);
        mAdapter = (ListAdapterQuestions) questionListView.getAdapter();

        testName = (TextView) findViewById(R.id.textview_testName);
        testName.setText(mCurrentTest.getTestName());

        submitButton = (ButtonFlat) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(this);

        testTime = (SeekBar) findViewById(R.id.seekbar_testtime);
        testTime.setProgress(150);

    }

    @Override
    protected void initData() {
        mCurrentTest = (CourseTest) getIntent().getSerializableExtra(EditTestActivity.COURSE_TEST_KEY);
        mList = mCurrentTest.getQuestionList();
    }
}
