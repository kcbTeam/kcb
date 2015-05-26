package com.kcb.teacher.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterQuestions;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class SubmitTestActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "SubmitTestPaper";

    private ListView questionListView;
    private ListAdapterQuestions mAdapter;
    private List<Question> mList;
    private Test mCurrentTest;

    private TextView testName;
    private Slider testTime;
    private TextView texttimeHint;

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
        testName.setText(mCurrentTest.getName());

        submitButton = (ButtonFlat) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(this);

        testTime = (Slider) findViewById(R.id.slider_testtime);
        texttimeHint = (TextView) findViewById(R.id.testtimeHint);
        texttimeHint.setText(String.format(getResources().getString(R.string.testtimeFormat),
                testTime.getValue()));
        testTime.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                texttimeHint.setText(String.format(getResources()
                        .getString(R.string.testtimeFormat), testTime.getValue()));
            }
        });
    }

    @Override
    protected void initData() {
        mCurrentTest =
                (Test) getIntent().getSerializableExtra(EditTestActivity.COURSE_TEST_KEY);
        mList = mCurrentTest.getQuestions();
    }

}
