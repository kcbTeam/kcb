package com.kcb.student.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.student.adapter.TestRecycleAdapter;
import com.kcb.teacher.model.ChoiceQuestion;
import com.kcbTeam.R;

public class TestActivity extends BaseFragmentActivity {

    private TextView timeTextView;
    private TextView choiceTextView;
    private TextView questionTextView;
    private TextView answerATextView;
    private TextView answerBTextView;
    private TextView answerCTextView;
    private TextView answerDTextView;
    private PaperButton preButton;
    private PaperButton nextButton;
    private CheckBox checkboxA;
    private CheckBox checkboxB;
    private CheckBox checkboxC;
    private CheckBox checkboxD;
    private RecyclerView recyclerView;
    private TestRecycleAdapter mAdapter;
    private ChoiceQuestion mChoiceQuestion;
    private List<ChoiceQuestion> mListQuestion;
    private int currentPageIndex;
    private int questionNum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_test);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        choiceTextView = (TextView) findViewById(R.id.textview_choice);
        questionTextView = (TextView) findViewById(R.id.textview_choice_question);
        answerATextView = (TextView) findViewById(R.id.textview_choice_a);
        answerBTextView = (TextView) findViewById(R.id.textview_choice_b);
        answerCTextView = (TextView) findViewById(R.id.textview_choice_c);
        answerDTextView = (TextView) findViewById(R.id.textview_choice_d);
        preButton = (PaperButton) findViewById(R.id.button_previous);
        preButton.setColor(Color.parseColor("#808080"));
        preButton.setOnClickListener(this);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, questionNum));
        setTimeCounterDown();
        mAdapter = new TestRecycleAdapter(questionNum);
        recyclerView.setAdapter(mAdapter);
    }

    // Set the countdown Timer,5 minutes
    public void setTimeCounterDown() {
        timeTextView = (TextView) findViewById(R.id.textview_timecounter);
        fiveCountDownTimer timeCountDown = new fiveCountDownTimer(300000, 1000);
        timeCountDown.start();
    }

    public void getTestContentFromNet() {
        mChoiceQuestion.setQuestion("");
        mChoiceQuestion.setOptionA("");
        mChoiceQuestion.setOptionB("");
        mChoiceQuestion.setOptionC("");
        mChoiceQuestion.setOptionD("");
        mListQuestion = new ArrayList<ChoiceQuestion>();
        mListQuestion.add(mChoiceQuestion);
    }

    @Override
    protected void initData() {
        questionTextView.setText("这是题目显示区域");
        answerATextView.setText("答案1");
        answerBTextView.setText("答案2");
        answerCTextView.setText("答案3");
        answerDTextView.setText("答案4");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_previous:
                clickPreButton();
                break;
            case R.id.button_next:
                clickNextButton();
                break;
            default:
                break;
        }

    }

    public void clickPreButton() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            mAdapter.setCurrentIndex(currentPageIndex);
            if (currentPageIndex == 0) {
                preButton.setColor(Color.parseColor("#808080"));
            }
            if (currentPageIndex == questionNum - 1)
                nextButton.setText("已完成");
            else
                nextButton.setText("下一题");
        }
    }

    public void clickNextButton() {

        if (currentPageIndex < questionNum - 1) {
            currentPageIndex++;
            mAdapter.setCurrentIndex(currentPageIndex);
            if (currentPageIndex == questionNum - 1)
                nextButton.setText("已完成");
            else {
                nextButton.setText("下一题");
                preButton.setColor(Color.parseColor("#ffffff"));
            }
            preButton.setText("上一题");
        } else {
            DialogUtil.showNormalDialog(this, R.string.tip, R.string.if_submit_answer,
                    R.string.sure, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, R.string.cancel, null);
        }

    }

    protected class fiveCountDownTimer extends CountDownTimer {

        public fiveCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeTextView.setText(millisUntilFinished / 60000 + ":" + millisUntilFinished % 60000
                    / 1000);
        }

        @Override
        public void onFinish() {
            timeTextView.setText(R.string.end);

            DialogUtil.showNormalDialog(TestActivity.this, R.string.tip, R.string.if_submit_answer,
                    R.string.sure, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, R.string.cancel, null);

        }
    }
}
