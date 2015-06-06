package com.kcb.teacher.activity.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.model.test.Test;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class LookTestDetailActivity extends BaseActivity {

    private ButtonFlat backButton;
    private ButtonFlat lastButton;
    private ButtonFlat nextButton;

    private TextView testNameTextView;
    private TextView questionNumTextView;

    private EditText questionTitle;
    private TextView correctRate;

    public static Test sTest;

    private int mQuestionNum;
    private int mCurrentPosition;

    private final String mRateFormat = "正确率：%1$d%%";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_test_detail);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testNameTextView = (TextView) findViewById(R.id.textview_title);
        testNameTextView.setText(sTest.getName());

        questionNumTextView = (TextView) findViewById(R.id.textview_question_num);

        questionTitle = (EditText) findViewById(R.id.edittext_question_title);
        correctRate = (TextView) findViewById(R.id.textview_correctrate);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);

        showContent();
    }

    @Override
    protected void initData() {
        mQuestionNum = sTest.getQuestionNum();
        mCurrentPosition = 0;
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
        showContent();
    }

    private void lastQuestion() {
        if (mCurrentPosition > 0) {
            mCurrentPosition--;
        }
    }

    private void nextQuestin() {
        if (mCurrentPosition != mQuestionNum - 1) {
            mCurrentPosition++;
        }
    }

    private void showContent() {
        Question question = sTest.getQuestion(mCurrentPosition);
        showQuestionNum();
        showQuestionItem(questionTitle, question.getTitle());
        correctRate.setText(String.format(mRateFormat, (int) (100 * question.getRate())));
    }

    private void showQuestionNum() {
        // questionNumTextView.setText(String.format(
        // getResources().getString(R.string.format_question_num), mCurrentPosition + 1,
        // mQuestionNum));
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(EditText view, QuestionItem item) {
        view.setBackgroundResource(R.drawable.stu_checkin_textview);
        if (item.isText()) {
            view.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                view.setText("");
                view.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            view.setFocusable(false);
        }
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
