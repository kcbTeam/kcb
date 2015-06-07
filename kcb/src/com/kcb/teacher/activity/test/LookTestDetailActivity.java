package com.kcb.teacher.activity.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Test;
import com.kcb.common.view.TestView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class LookTestDetailActivity extends BaseActivity {

    private ButtonFlat backButton;
    private TextView testNameTextView;

    private TestView testView;

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;

    public static Test sTest;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_test_detail);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testNameTextView = (TextView) findViewById(R.id.textview_title);
        testNameTextView.setText(sTest.getName());

        testView = (TestView) findViewById(R.id.testview);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mCurrentPosition = 0;
        showTest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_last:
                lastQuestion();
                break;
            case R.id.button_next:
                nextQuestin();
                break;
            default:
                break;
        }
    }

    private void lastQuestion() {
        if (mCurrentPosition > 0) {
            mCurrentPosition--;
            showTest();
        }
    }

    private void nextQuestin() {
        if (mCurrentPosition != sTest.getQuestionNum() - 1) {
            mCurrentPosition++;
            showTest();
        }
    }

    private void showTest() {
        testView.showQuestionIndex(String.format(getString(R.string.tch_question_index),
                mCurrentPosition + 1));
        testView.showQuestion(sTest.getQuestion(mCurrentPosition));
    }

    /**
     * start
     */
    public static void start(Context context, Test test) {
        Intent intent = new Intent(context, LookTestDetailActivity.class);
        context.startActivity(intent);
        sTest = test;
        sTest.changeStringToBitmap();
    }
}
