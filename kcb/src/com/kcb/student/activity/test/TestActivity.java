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
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.MaterialDialog;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.student.activity.checkin.StartCheckInActivity;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
import com.kcb.teacher.model.test.Test;
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
        testNameTextView.setText(sTest.getName() + "（共" + sTest.getQuestionNum() + "题）");
        showQuestionIndex();
        showQuestion();

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

    private void showQuestionIndex() {
        questionIndexTextView.setText(String.format(getString(R.string.stu_question_index),
                mCurrentQuestionIndex + 1));
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
                break;
            default:
                break;
        }
    }

    private void lastQuestion() {
        if (mCurrentQuestionIndex > 0) {
            saveQuestion();
            if (!getCurrentQuestion().equal(mTempQuestion)) {
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mCurrentQuestionIndex));
            }
            mCurrentQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuesion() {
        saveQuestion();
        if (!getCurrentQuestion().equal(mTempQuestion)) {
            ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                    1 + mCurrentQuestionIndex));
        }
        mCurrentQuestionIndex++;
        showQuestion();
    }

    private void showQuestion() {
        mTempQuestion = Question.clone(getCurrentQuestion());

        showQuestionNum();

        Question question = getCurrentQuestion();

        showQuestionItem(FLAG_TITLE, question.getTitle());
        showQuestionItem(FLAG_A, question.getChoiceA());
        showQuestionItem(FALG_B, question.getChoiceB());
        showQuestionItem(FLAG_C, question.getChoiceC());
        showQuestionItem(FLAG_D, question.getChoiceD());

        checkBoxA.setChecked(question.getChoiceA().isRight());
        checkBoxB.setChecked(question.getChoiceB().isRight());
        checkBoxC.setChecked(question.getChoiceC().isRight());
        checkBoxD.setChecked(question.getChoiceD().isRight());
    }

    private void showQuestionNum() {
        testNameTextView.setText(String.format(getString(R.string.test_name_num), sTest.getName(),
                sTest.getQuestionNum()));
        questionIndexTextView.setText(String.format(getString(R.string.problem_hint),
                mCurrentQuestionIndex + 1));
    }

    private void saveQuestion() {
        QuestionItem titleItem = getCurrentQuestion().getTitle();
        QuestionItem aItem = getCurrentQuestion().getChoiceA();
        QuestionItem bItem = getCurrentQuestion().getChoiceB();
        QuestionItem cItem = getCurrentQuestion().getChoiceC();
        QuestionItem dItem = getCurrentQuestion().getChoiceD();

        aItem.setIsRight(checkBoxA.isCheck());
        bItem.setIsRight(checkBoxB.isCheck());
        cItem.setIsRight(checkBoxC.isCheck());
        dItem.setIsRight(checkBoxD.isCheck());
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(int flag, QuestionItem item) {
        TextView textView = getTextViewByTag(flag);
        if (item.isText()) {
            setEditMode(flag, EDIT_MODE_TEXT);
            textView.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                setEditMode(flag, EDIT_MODE_BITMAP);
                textView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        }
    }

    private final int EDIT_MODE_TEXT = 1;
    private final int EDIT_MODE_BITMAP = 2;

    private void setEditMode(int flag, int mode) {
        TextView editText = getTextViewByTag(flag);
        switch (mode) {
            case EDIT_MODE_TEXT:
                editText.setText("");
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.setBackgroundResource(R.drawable.stu_checkin_textview);
                break;
            case EDIT_MODE_BITMAP:
                editText.setText(" ");
                editText.setFocusable(false);
                break;
            default:
                break;
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
