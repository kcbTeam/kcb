package com.kcb.student.activity.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.answer.TestAnswer;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.MaterialDialog;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.database.test.TestDao;
import com.kcbTeam.R;

/**
 * 
 * @className: TestActivity
 * @description:
 * @author: Tao Li
 * @date: 2015-5-17 上午10:53:44
 */
public class TestActivity extends BaseActivity {

    private TextView testNameTextView;
    private TextView timeTextView;

    private TextView questionIndexTextView;

    private TextView titleTextView;
    private TextView choiceATextView;
    private TextView choiceBTextView;
    private TextView choiceCTextView;
    private TextView choiceDTextView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;
    private ButtonFlat submitButton;

    // tag title/A/B/C/D;
    private final int FLAG_TITLE = 1;
    private final int FLAG_A = 2;
    private final int FALG_B = 3;
    private final int FLAG_C = 4;
    private final int FLAG_D = 5;

    // test and current question index;
    public static Test sTest;
    private int mCurrentQuestionIndex;

    // when click last/next, show a mTempQuestion, used for compare it is changed or not;
    private Question mTempQuestion;

    private TestAnswer mTestAnswer;

    private TestDao mTestDao;

    private int mRemainTime; // seconds
    private Handler mHandler;
    private final int MESSAGE_TIME_REDUCE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_test);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        testNameTextView = (TextView) findViewById(R.id.textview_testname);
        timeTextView = (TextView) findViewById(R.id.textview_time);

        questionIndexTextView = (TextView) findViewById(R.id.textview_question_num);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        choiceATextView = (TextView) findViewById(R.id.textview_A);
        choiceBTextView = (TextView) findViewById(R.id.textview_B);
        choiceCTextView = (TextView) findViewById(R.id.textview_C);
        choiceDTextView = (TextView) findViewById(R.id.textview_D);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        submitButton = (ButtonFlat) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);
    }

    @Override
    protected void initData() {
        testNameTextView.setText(String.format(getString(R.string.test_name), sTest.getName(),
                sTest.getQuestionNum()));
        showQuestion();

        mTestAnswer = new TestAnswer(sTest);

        mRemainTime = getIntent().getIntExtra(DATA_REMAIN_TIME, 0);
        mHandler = new Handler(getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                showRemainTime();
                if (mRemainTime == 0) {
                    showTimeEndDialog();
                } else {
                    sendEmptyMessageDelayed(MESSAGE_TIME_REDUCE, 1000);
                    mRemainTime--;
                }
            };
        };
        mHandler.sendEmptyMessage(MESSAGE_TIME_REDUCE);
    }

    private void showRemainTime() {
        timeTextView.setText(String.format(getString(R.string.stu_remain_time), mRemainTime / 60,
                mRemainTime % 60));
    }

    private void showTimeEndDialog() {
        MaterialDialog dialog =
                DialogUtil.showNormalDialog(TestActivity.this, R.string.tip,
                        R.string.stu_test_time_end, R.string.submit, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }, -1, null);
        dialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_last:
                lastQuestion();
                break;
            case R.id.button_next:
                nextQuesion();
                break;
            case R.id.button_submit:
                submitAnswer();
                break;
            default:
                break;
        }
    }

    private void lastQuestion() {
        if (mCurrentQuestionIndex > 0) {
            saveAnswer();
            mCurrentQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuesion() {
        if (mCurrentQuestionIndex < sTest.getQuestionNum() - 1) {
            saveAnswer();
            mCurrentQuestionIndex++;
            showQuestion();
        }
    }

    private void submitAnswer() {
        saveAnswer();
        // TODO get unfinished question index;
        MaterialDialog dialog =
                DialogUtil.showNormalDialog(TestActivity.this, R.string.submit,
                        R.string.stu_test_time_end, R.string.submit, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }, R.string.cancel, null);
    }

    private void showQuestion() {
        questionIndexTextView.setText(String.format(getString(R.string.stu_question_index),
                mCurrentQuestionIndex + 1));

        Question question = getCurrentQuestion();
        mTempQuestion = Question.clone(question);

        showQuestionItem(FLAG_TITLE, question.getTitle());
        showQuestionItem(FLAG_A, question.getChoiceA());
        showQuestionItem(FALG_B, question.getChoiceB());
        showQuestionItem(FLAG_C, question.getChoiceC());
        showQuestionItem(FLAG_D, question.getChoiceD());

        checkBoxA.setChecked(question.getChoiceA().isSelected());
        checkBoxB.setChecked(question.getChoiceB().isSelected());
        checkBoxC.setChecked(question.getChoiceC().isSelected());
        checkBoxD.setChecked(question.getChoiceD().isSelected());
    }

    private void saveAnswer() {
        QuestionItem aItem = getCurrentQuestion().getChoiceA();
        QuestionItem bItem = getCurrentQuestion().getChoiceB();
        QuestionItem cItem = getCurrentQuestion().getChoiceC();
        QuestionItem dItem = getCurrentQuestion().getChoiceD();
        aItem.setIsSelected(checkBoxA.isCheck());
        bItem.setIsSelected(checkBoxB.isCheck());
        cItem.setIsSelected(checkBoxC.isCheck());
        dItem.setIsSelected(checkBoxD.isCheck());
        if (!getCurrentQuestion().equal(mTempQuestion)) {
            ToastUtil.toast(String.format(getResources().getString(R.string.stu_question_save),
                    mCurrentQuestionIndex + 1));
        }
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(int flag, QuestionItem item) {
        TextView textView = getTextViewByTag(flag);
        if (item.isText()) {
            textView.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                textView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        }
    }

    private TextView getTextViewByTag(int flag) {
        TextView textView = null;
        switch (flag) {
            case FLAG_TITLE:
                textView = titleTextView;
                break;
            case FLAG_A:
                textView = choiceATextView;
                break;
            case FALG_B:
                textView = choiceBTextView;
                break;
            case FLAG_C:
                textView = choiceCTextView;
                break;
            case FLAG_D:
                textView = choiceDTextView;
                break;
            default:
                break;
        }
        return textView;
    }

    private Question getCurrentQuestion() {
        return sTest.getQuestion(mCurrentQuestionIndex);
    }

    @Override
    public void onBackPressed() {}

    /**
     * start
     */
    private static final String DATA_REMAIN_TIME = "remaintime";

    public static void start(Context context, Test test, int remainTime) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(DATA_REMAIN_TIME, remainTime);
        context.startActivity(intent);
        sTest = test;
    }
}
