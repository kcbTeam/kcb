package com.kcb.student.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import com.kcb.student.util.JsonObjectParserUtil;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

/**
 * 
 * @className: TestActivity
 * @description:
 * @author: Tao Li
 * @date: 2015-5-17 上午10:53:44
 */
public class TestActivity extends BaseFragmentActivity {

    private TextView timeTextView;
    private TextView titleTextView;
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
    private Test mTest;
    private fiveCountDownTimer timeCountDown;
    private RecyclerView recyclerView;
    private TestRecycleAdapter mAdapter;
    private int currentPageIndex;
    private int questionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_test);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.textview_testtitle);
        questionTextView = (TextView) findViewById(R.id.textview_choice_question);
        answerATextView = (TextView) findViewById(R.id.textview_choice_a);
        answerBTextView = (TextView) findViewById(R.id.textview_choice_b);
        answerCTextView = (TextView) findViewById(R.id.textview_choice_c);
        answerDTextView = (TextView) findViewById(R.id.textview_choice_d);
        checkboxA = (CheckBox) findViewById(R.id.checkbox_a);
        checkboxB = (CheckBox) findViewById(R.id.checkbox_b);
        checkboxC = (CheckBox) findViewById(R.id.checkbox_c);
        checkboxD = (CheckBox) findViewById(R.id.checkbox_d);
        preButton = (PaperButton) findViewById(R.id.button_previous);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview1);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    // Set the countdown Timer,5 minutes
    public void setTimeCounterDown(long totalTime) {
        timeTextView = (TextView) findViewById(R.id.textview_timecounter);
        timeCountDown = new fiveCountDownTimer(totalTime, 1000);
        timeCountDown.start();
    }

   

    @Override
    protected void initData() {
        String string = getIntent().getStringExtra("questionInfo");
        JsonObjectParserUtil questionData = new JsonObjectParserUtil(string);
        mTest = questionData.ParserJsonObject();
        questionNum = mTest.getQuestionNum();
        recyclerView.setLayoutManager(new GridLayoutManager(this, questionNum));
        mAdapter = new TestRecycleAdapter(questionNum);
        recyclerView.setAdapter(mAdapter);
        if (questionNum == 1) nextButton.setText("已完成");
        titleTextView.setText(mTest.getName());
        showCurrentQuestion(0);
        setTimeCounterDown(mTest.getmTime()*60000);
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
            if (currentPageIndex == questionNum - 1) {
                nextButton.setText("已完成");
            } else {
                nextButton.setText("下一题");
            }
            getCheckBoxsAnswer(currentPageIndex + 1);
            showCurrentQuestion(currentPageIndex);
            setCheckBoxsAnswer(currentPageIndex);
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
            }
            preButton.setText("上一题");
            getCheckBoxsAnswer(currentPageIndex - 1);
            showCurrentQuestion(currentPageIndex);
            setCheckBoxsAnswer(currentPageIndex);
        } else {
            getCheckBoxsAnswer(currentPageIndex);
            String string;
            int i = collectAnsweredNum();
            if (i < questionNum) {
                string = new String("已完成了" + i + "题，还有" + (questionNum - i) + "题未完成，是否提交？");
            } else {
                string = new String("已全完成，是否提交？");
            }
            DialogUtil.showNormalDialog(this, R.string.tip, string, R.string.sure,
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, R.string.cancel, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void showCurrentQuestion(int position) {

        if (mTest.getQuestion(position).getTitle().isText()) {
            questionTextView.setText(mTest.getQuestion(position).getTitle().getText());
            questionTextView.setBackgroundColor(Color.WHITE);
        } else {
            questionTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getTitle().getBitmap()));
            questionTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceA().isText()) {
            answerATextView.setText(mTest.getQuestion(position).getChoiceA().getText());
            answerATextView.setBackgroundColor(Color.WHITE);
        } else {
            answerATextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceA().getBitmap()));
            answerATextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceB().isText()) {
            answerBTextView.setText(mTest.getQuestion(position).getChoiceB().getText());
            answerBTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerBTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceB().getBitmap()));
            answerBTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceC().isText()) {
            answerCTextView.setText(mTest.getQuestion(position).getChoiceC().getText());
            answerCTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerCTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceC().getBitmap()));
            answerCTextView.setText("");
        }
        if (mTest.getQuestion(position).getTitle().isText()) {
            answerDTextView.setText(mTest.getQuestion(position).getChoiceD().getText());
            answerDTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerDTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceD().getBitmap()));
            answerDTextView.setText("");
        }
    }

    public void getCheckBoxsAnswer(int position) {
        boolean[] mCorrectId = new boolean[] {false, false, false, false};
        mCorrectId[0] = checkboxA.isCheck();
        mCorrectId[1] = checkboxB.isCheck();
        mCorrectId[2] = checkboxC.isCheck();
        mCorrectId[3] = checkboxD.isCheck();
        mTest.getQuestion(position).setCorrectId(mCorrectId);
    }

    public void setCheckBoxsAnswer(int position) {
        boolean[] mCorrectId;
        mCorrectId = mTest.getQuestion(position).getCorrectId();
        checkboxA.setChecked(mCorrectId[0]);
        checkboxB.setChecked(mCorrectId[1]);
        checkboxC.setChecked(mCorrectId[2]);
        checkboxD.setChecked(mCorrectId[3]);
    }

    public int collectAnsweredNum() {
        int AnsweredNum = 0;
        for (int i = 0; i < mTest.getQuestionNum(); i++) {
            boolean[] bleans = mTest.getQuestion(i).getCorrectId();
            for (int j = 0; j < bleans.length; j++) {
                if (bleans[j]) {
                    AnsweredNum++;
                    break;
                }
            }
        }
        return AnsweredNum;
    }

    protected class fiveCountDownTimer extends CountDownTimer {

        private long millisUntilFinished;

        public fiveCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public long getMillisUntilFinished() {
            return this.millisUntilFinished;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeTextView.setText(millisUntilFinished / 60000 + ":" + millisUntilFinished % 60000
                    / 1000);
            this.millisUntilFinished = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            DialogUtil.showNormalDialog(TestActivity.this, R.string.tip, R.string.if_submit_answer,
                    R.string.sure, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }, -1, null);
        }
    }

    @Override
    public void onBackPressed() {
        DialogUtil.showNormalDialog(this, R.string.tip, R.string.if_giveup_answer, R.string.sure,
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }, R.string.cancel, null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPref = getSharedPreferences("CounterTimes", MODE_PRIVATE);
        long useFirst = sharedPref.getLong("CounterTimes", 0);
        timeCountDown = new fiveCountDownTimer(useFirst, 1000);
        timeCountDown.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences("CounterTimes", MODE_PRIVATE);
        long useFirst = sharedPref.getLong("CounterTimes", 0);
        Editor mEditor = sharedPref.edit();
        mEditor.putLong("CounterTimes", timeCountDown.getMillisUntilFinished());
        mEditor.commit();
        timeCountDown.cancel();
    }
}
