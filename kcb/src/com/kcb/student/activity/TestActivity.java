package com.kcb.student.activity;

import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import com.kcb.teacher.model.test.Question;
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
    private fiveCountDownTimer timeCountDown;
    private RecyclerView recyclerView;
    private TestRecycleAdapter mAdapter;
    private Question mChoiceQuestion;
    private Question mChoiceQuestion1;
    private Question mChoiceQuestion2;
    private List<Question> mListQuestion;

    private int currentPageIndex;
    private int questionNum = 3;

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
        checkboxA = (CheckBox) findViewById(R.id.checkbox_a);
        checkboxB = (CheckBox) findViewById(R.id.checkbox_b);
        checkboxC = (CheckBox) findViewById(R.id.checkbox_c);
        checkboxD = (CheckBox) findViewById(R.id.checkbox_d);
        preButton = (PaperButton) findViewById(R.id.button_previous);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, questionNum));
        mAdapter = new TestRecycleAdapter(questionNum);
        recyclerView.setAdapter(mAdapter);

    }

    // Set the countdown Timer,5 minutes
    public void setTimeCounterDown() {
        timeTextView = (TextView) findViewById(R.id.textview_timecounter);
        timeCountDown = new fiveCountDownTimer(300000, 1000);
        timeCountDown.start();
    }

    public void getTestContentFromNet() {
        // mChoiceQuestion = new ChoiceQuestion();
        // mChoiceQuestion.setQuestionNum(1);
        // mChoiceQuestion.setQuestion(new TextContent("这是题目显示区域1"));
        // mChoiceQuestion.setOptionA(new TextContent("答案1"));
        // mChoiceQuestion.setOptionB(new TextContent("答案2"));
        // mChoiceQuestion.setOptionC(new TextContent("答案3"));
        // mChoiceQuestion.setOptionD(new TextContent("答案4"));
        // mListQuestion = new ArrayList<ChoiceQuestion>();
        // mListQuestion.add(mChoiceQuestion);
        // mChoiceQuestion1 = new ChoiceQuestion();
        // mChoiceQuestion1.setQuestionNum(2);
        // mChoiceQuestion1.setQuestion(new TextContent("这是题目显示区域2"));
        // mChoiceQuestion1.setOptionA(new TextContent("答案1"));
        // mChoiceQuestion1.setOptionB(new TextContent("答案2"));
        // mChoiceQuestion1.setOptionC(new TextContent("答案3"));
        // mChoiceQuestion1.setOptionD(new TextContent("答案4"));
        // mListQuestion.add(mChoiceQuestion1);
        // mChoiceQuestion2 = new ChoiceQuestion();
        // mChoiceQuestion2.setQuestionNum(3);
        // mChoiceQuestion2.setQuestion(new TextContent("这是题目显示区域3"));
        // mChoiceQuestion2.setOptionA(new TextContent("答案1"));
        // mChoiceQuestion2.setOptionB(new TextContent("答案2"));
        // mChoiceQuestion2.setOptionC(new TextContent("答案3"));
        // mChoiceQuestion2.setOptionD(new TextContent("答案4"));
        // mListQuestion.add(mChoiceQuestion2);
    }

    @Override
    protected void initData() {
        getTestContentFromNet();
        showCurrentQuestion(0);
        setTimeCounterDown();
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

    public void showCurrentQuestion(int position) {
        // choiceTextView.setText("选择题" + mListQuestion.get(position).getQuestionNum());
        questionTextView.setText(mListQuestion.get(position).getTitle().getText());
        answerATextView.setText(mListQuestion.get(position).getChoiceA().getText());
        answerBTextView.setText(mListQuestion.get(position).getChoiceB().getText());
        answerCTextView.setText(mListQuestion.get(position).getChoiceC().getText());
        answerDTextView.setText(mListQuestion.get(position).getChoiceD().getText());
    }

    public void getCheckBoxsAnswer(int position) {
        boolean[] mCorrectId = new boolean[] {false, false, false, false};
        mCorrectId[0] = checkboxA.isCheck();
        mCorrectId[1] = checkboxB.isCheck();
        mCorrectId[2] = checkboxC.isCheck();
        mCorrectId[3] = checkboxD.isCheck();
        mListQuestion.get(position).setCorrectId(mCorrectId);
    }

    public void setCheckBoxsAnswer(int position) {
        boolean[] mCorrectId;
        mCorrectId = mListQuestion.get(position).getCorrectId();
        checkboxA.setChecked(mCorrectId[0]);
        checkboxB.setChecked(mCorrectId[1]);
        checkboxC.setChecked(mCorrectId[2]);
        checkboxD.setChecked(mCorrectId[3]);
    }

    public int collectAnsweredNum() {
        int AnsweredNum = 0;
        for (int i = 0; i < mListQuestion.size(); i++) {
            boolean[] bleans = mListQuestion.get(i).getCorrectId();
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
