package com.kcb.teacher.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class CheckTestDetailsActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "CheckTest";

    private ButtonFlat backButton;

    private TextView testName;
    private TextView questionTextView;
    private TextView correctRate;

    private TextView optionAtitle;
    private TextView optionAcontent;
    private TextView optionArate;

    private TextView optionBtitle;
    private TextView optionBcontent;
    private TextView optionBrate;

    private TextView optionCtitle;
    private TextView optionCcontent;
    private TextView optionCrate;

    private TextView optionDtitle;
    private TextView optionDcontent;
    private TextView optionDrate;

    private PaperButton lastButton;
    private PaperButton nextButton;

    private Test mTest;
    private List<Question> mQuestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checktestdetails);
        initData();
        initView();
    }

    @Override
    protected void initView() {

        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testName = (TextView) findViewById(R.id.textview_testname);
        testName.setText(mTest.getName());

        questionTextView = (TextView) findViewById(R.id.textview_question);
        correctRate = (TextView) findViewById(R.id.textview_correct_rate);

        optionAtitle = (TextView) findViewById(R.id.textview_A_title);
        optionAcontent = (TextView) findViewById(R.id.textview_A_content);
        optionArate = (TextView) findViewById(R.id.textview_A_rate);

        optionBtitle = (TextView) findViewById(R.id.textview_B_title);
        optionBcontent = (TextView) findViewById(R.id.textview_B_content);
        optionBrate = (TextView) findViewById(R.id.textview_B_rate);

        optionCtitle = (TextView) findViewById(R.id.textview_C_title);
        optionCcontent = (TextView) findViewById(R.id.textview_C_content);
        optionCrate = (TextView) findViewById(R.id.textview_C_rate);

        optionDtitle = (TextView) findViewById(R.id.textview_D_title);
        optionDcontent = (TextView) findViewById(R.id.textview_D_content);
        optionDrate = (TextView) findViewById(R.id.textview_D_rate);

        lastButton = (PaperButton) findViewById(R.id.pagerbutton_last);
        nextButton = (PaperButton) findViewById(R.id.pagerbutton_next);

        setContent(mQuestionList.get(0));

    }

    @Override
    protected void initData() {

        mTest = (Test) getIntent().getSerializableExtra(CheckTestActivity.CLICKED_TEST_KEY);
        mQuestionList = mTest.getQuestions();
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

    @SuppressLint("NewApi")
    private void setContent(Question temp) {

        questionTextView.setText("1、" + temp.getTitle().getText());
        correctRate.setText("正确率：76%");

        // optionAcontent.setText(temp.getOptionA().getContentString());
        optionAcontent.setBackground(getResources().getDrawable(R.drawable.option_a));
        optionBcontent.setText(temp.getChoiceB().getText());
        // optionCcontent.setText(temp.getOptionC().getContentString());
        optionCcontent.setBackground(getResources().getDrawable(R.drawable.option_a));
        optionDcontent.setText(temp.getChoiceD().getText());

        optionArate.setText("76%");
        optionBrate.setText("10%");
        optionCrate.setText("5%");
        optionDrate.setText("9%");

        setOptionTitle(temp.getCorrectId());

    }

    private void setOptionTitle(boolean[] correctId) {
        if (correctId.length != 4) {
            return;
        }
        Resources res = getResources();
        if (correctId[0]) {
            optionAtitle.setTextColor(res.getColor(R.color.red));
            optionAtitle.setTextSize(22f);
        }
        if (correctId[1]) {
            optionBtitle.setTextColor(res.getColor(R.color.red));
            optionBtitle.setTextSize(22f);
        }
        if (correctId[2]) {
            optionCtitle.setTextColor(res.getColor(R.color.red));
            optionCtitle.setTextSize(22f);
        }
        if (correctId[3]) {
            optionDtitle.setTextColor(res.getColor(R.color.red));
            optionDtitle.setTextSize(22f);
        }

    }

}
