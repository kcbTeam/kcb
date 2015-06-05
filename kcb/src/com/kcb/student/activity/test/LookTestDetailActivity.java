package com.kcb.student.activity.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.model.test.Test;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: LookTestDetailActivity
 * @description:
 * @author: taoli
 * @date: 2015-6-5 下午5:30:46
 */
public class LookTestDetailActivity extends BaseActivity {

    private ButtonFlat backButton;

    private TextView testNameNumTextView;
    private TextView questionIndexTextView;

    private TextView titleTextView;
    private TextView choiceATextView;
    private TextView choiceBTextView;
    private TextView choiceCTextView;
    private TextView choiceDTextView;

    private ImageView checkBoxA;
    private ImageView checkBoxB;
    private ImageView checkBoxC;
    private ImageView checkBoxD;

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;

    // test and current question index;
    public static Test sTest;
    private int mCurrentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_look_test_detail);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testNameNumTextView = (TextView) findViewById(R.id.textview_test_name_num);
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_index);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        choiceATextView = (TextView) findViewById(R.id.textview_A);
        choiceBTextView = (TextView) findViewById(R.id.textview_B);
        choiceCTextView = (TextView) findViewById(R.id.textview_C);
        choiceDTextView = (TextView) findViewById(R.id.textview_D);

        checkBoxA = (ImageView) findViewById(R.id.checkBox_A);
        checkBoxB = (ImageView) findViewById(R.id.checkBox_B);
        checkBoxC = (ImageView) findViewById(R.id.checkBox_C);
        checkBoxD = (ImageView) findViewById(R.id.checkBox_D);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        testNameNumTextView.setText(String.format(getString(R.string.stu_test_name_num),
                sTest.getName(), sTest.getQuestionNum()));
        showQuestion();
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
                nextQuesion();
                break;
            default:
                break;
        }
    }

    private void showQuestion() {
        questionIndexTextView.setText(String.format(getString(R.string.stu_question_index),
                mCurrentQuestionIndex + 1));

        Question question = sTest.getQuestion(mCurrentQuestionIndex);
        showQuestionItem(titleTextView, null, question.getTitle());
        showQuestionItem(choiceATextView, checkBoxA, question.getChoiceA());
        showQuestionItem(choiceBTextView, checkBoxB, question.getChoiceB());
        showQuestionItem(choiceCTextView, checkBoxC, question.getChoiceC());
        showQuestionItem(choiceDTextView, checkBoxD, question.getChoiceD());
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(TextView view, ImageView checkIcon, QuestionItem item) {
        if (item.isText()) {
            view.setText(item.getText());
            view.setBackgroundResource(0);
        } else {
            view.setText("");
            view.setBackgroundDrawable(new BitmapDrawable(item.getBitmap()));
        }
        if (null != checkIcon) {
            if (item.isSelected()) {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
            } else {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_outline_blank_grey600_18dp);
            }
        }
    }

    private void lastQuestion() {
        if (mCurrentQuestionIndex > 0) {
            mCurrentQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuesion() {
        if (mCurrentQuestionIndex < sTest.getQuestionNum() - 1) {
            mCurrentQuestionIndex++;
            showQuestion();
        }
    }

    /**
     * start
     */
    public static void start(Context context, Test test) {
        Intent intent = new Intent(context, LookTestDetailActivity.class);
        context.startActivity(intent);
        sTest = test;
    }
}
