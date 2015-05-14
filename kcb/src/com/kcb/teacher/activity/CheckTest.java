package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.model.ChoiceQuestion;
import com.kcb.teacher.model.CourseTest;
import com.kcbTeam.R;

public class CheckTest extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "CheckTest";

    private ButtonFlat backButton;

    private TextView testName;
    private TextView questionNum;
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

    private CourseTest mTest;
    private List<ChoiceQuestion> mQuestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checktest);
        initData();
        initView();
    }

    @Override
    protected void initView() {

        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testName = (TextView) findViewById(R.id.textview_testname);
        testName.setText(mTest.getTestName());

        questionNum = (TextView) findViewById(R.id.textview_question_num);
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

        setContent(mQuestionList.get(0));

    }

    private void setContent(ChoiceQuestion temp) {

        questionNum.setText("第一题");
        questionTextView.setText(temp.getQuestion());
        correctRate.setText("正确率：76%");

        optionAcontent.setText(temp.getOptionA());
        optionBcontent.setText(temp.getOptionB());
        optionCcontent.setText(temp.getOptionC());
        optionDcontent.setText(temp.getOptionD());

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

    @Override
    protected void initData() {
        mQuestionList = new ArrayList<ChoiceQuestion>();
        mQuestionList.add(new ChoiceQuestion("一年可能有多少天？", "365" + '\n' + "365" + '\n' + "365",
                "365" + '\n' + "365" + '\n' + "365", "367", "368", new boolean[] {true, true,
                        false, false}));
        mTest = new CourseTest("高考数学", mQuestionList);
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
