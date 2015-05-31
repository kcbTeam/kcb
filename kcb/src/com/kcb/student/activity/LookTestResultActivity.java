package com.kcb.student.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.LogUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.student.util.JsonObjectParserUtil;
import com.kcb.student.util.RedHookDraw;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class LookTestResultActivity extends BaseActivity {

    private TextView titleTextView;
    private TextView choiceNumTextView;
    private TextView questionTextView;
    private TextView answerATextView;
    private TextView answerBTextView;
    private TextView answerCTextView;
    private TextView answerDTextView;
    private CheckBox checkboxA;
    private CheckBox checkboxB;
    private CheckBox checkboxC;
    private CheckBox checkboxD;
    private ButtonFlat qTab1Button;
    private ButtonFlat qTab2Button;
    private ButtonFlat qTab3Button;
    private ButtonFlat qTab4Button;
    private ButtonFlat backButton;
    private Test mTest;
    private int numQuestion;
    private RedHookDraw layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_looktestresult);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.testresult_title);
        choiceNumTextView = (TextView) findViewById(R.id.textview_testresultchioce);
        questionTextView = (TextView) findViewById(R.id.textview_choice_question1);
        answerATextView = (TextView) findViewById(R.id.textview_choice_a1);
        answerBTextView = (TextView) findViewById(R.id.textview_choice_b1);
        answerCTextView = (TextView) findViewById(R.id.textview_choice_c1);
        answerDTextView = (TextView) findViewById(R.id.textview_choice_d1);
        checkboxA = (CheckBox) findViewById(R.id.checkbox_a1);
        checkboxB = (CheckBox) findViewById(R.id.checkbox_b1);
        checkboxC = (CheckBox) findViewById(R.id.checkbox_c1);
        checkboxD = (CheckBox) findViewById(R.id.checkbox_d1);
        qTab1Button = (ButtonFlat) findViewById(R.id.button_tab_question1);
        qTab2Button = (ButtonFlat) findViewById(R.id.button_tab_question2);
        qTab3Button = (ButtonFlat) findViewById(R.id.button_tab_question3);
        qTab4Button = (ButtonFlat) findViewById(R.id.button_tab_question4);
        qTab1Button.setOnClickListener(this);
        qTab2Button.setOnClickListener(this);
        qTab3Button.setOnClickListener(this);
        qTab4Button.setOnClickListener(this);
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        layout = (RedHookDraw) findViewById(R.id.relativelayout_redhook);
        layout.setVisibility(View.INVISIBLE);
    }

    protected void setTabNums() {
        switch (numQuestion) {
            case 1:
                qTab2Button.setVisibility(View.GONE);
                qTab3Button.setVisibility(View.GONE);
                qTab4Button.setVisibility(View.GONE);
                break;
            case 2:
                qTab3Button.setVisibility(View.GONE);
                qTab4Button.setVisibility(View.GONE);
                break;
            case 3:
                qTab4Button.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    protected void setCurrent() {}

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        String string = intent.getStringExtra("testTitle1");
        titleTextView.setText(string);
        numQuestion = 3;
        setTabNums();
        String string1 = getIntent().getStringExtra("questionInfo");
        // LogUtil.i("gdf", string1);
        JsonObjectParserUtil questionData = new JsonObjectParserUtil(string1);
        mTest = questionData.ParserJsonObject();
        if (mTest.getQuestion(0).getTitle().isText()) LogUtil.i("df", "gg");
        showCurrentQuestion(0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("NewApi")
    public void showCurrentQuestion(int position) {

        if (mTest.getQuestion(position).getTitle().isText()) {
            questionTextView.setText(Integer.toString(position + 1) + "、"
                    + mTest.getQuestion(position).getTitle().getText());
            questionTextView.setBackgroundColor(Color.WHITE);
        } else {
            questionTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getTitle().getBitmap()));
            questionTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceA().isText()) {
            answerATextView.setText("A、" + mTest.getQuestion(position).getChoiceA().getText());
            answerATextView.setBackgroundColor(Color.WHITE);
        } else {
            answerATextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceA().getBitmap()));
            answerATextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceB().isText()) {
            answerBTextView.setText("B、" + mTest.getQuestion(position).getChoiceB().getText());
            answerBTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerBTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceB().getBitmap()));
            answerBTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceC().isText()) {
            answerCTextView.setText("C、" + mTest.getQuestion(position).getChoiceC().getText());
            answerCTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerCTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceC().getBitmap()));
            answerCTextView.setText("");
        }
        if (mTest.getQuestion(position).getTitle().isText()) {
            answerDTextView.setText("D、" + mTest.getQuestion(position).getChoiceD().getText());
            answerDTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerDTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceD().getBitmap()));
            answerDTextView.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_tab_question1:
                showCurrentQuestion(0);
                layout.setVisibility(View.INVISIBLE);
                break;
            case R.id.button_tab_question2:
                showCurrentQuestion(1);
                layout.setVisibility(View.VISIBLE);

                break;
            case R.id.button_tab_question3:
                showCurrentQuestion(2);
                layout.setVisibility(View.INVISIBLE);
                break;
            case R.id.button_tab_question4:
                showCurrentQuestion(3);
                layout.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
}
