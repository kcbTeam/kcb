package com.kcb.student.activity;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.LogUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.student.util.RedHookDraw;
import com.kcbTeam.R;

public class LookTestResultActivity extends BaseActivity {

    private static final String TAG = "hfdjshng";
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
        backButton=(ButtonFlat) findViewById(R.id.button_back);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_tab_question1:
                break;
            case R.id.button_tab_question2:

                break;
            case R.id.button_tab_question3:

                break;
            case R.id.button_tab_question4:

                break;
            default:
                break;
        }
    }
}
