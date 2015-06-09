package com.kcb.teacher.activity.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.QuestionEditView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.database.test.TestDao;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 下午6:03:59
 */
public class EditTestActivity extends BaseActivity {

    private ButtonFlat backButton;
    private TextView testNameNumTextView;

    private ButtonFlat cancelButton;
    private ImageView cancelImageView;

    private QuestionEditView questionEditView;

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;
    private ButtonFlat addButton;
    private ButtonFlat deleteButton;

    // test and current question index;
    public static Test sTest;
    private int mCurrentQuestionIndex;

    // when click last/next, show a mTempQuestion, used for compare it is changed or not;
    private Question mTempQuestion;

    private String mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edit_test);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        testNameNumTextView = (TextView) findViewById(R.id.textview_title);

        cancelButton = (ButtonFlat) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);
        cancelImageView = (ImageView) findViewById(R.id.imageview_cancel);

        questionEditView = (QuestionEditView) findViewById(R.id.questioneditview);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        addButton = (ButtonFlat) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        deleteButton = (ButtonFlat) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mAction = intent.getAction();
        if (mAction.equals(ACTION_EDIT_TEST)) {
            backButton.setVisibility(View.VISIBLE);
            testNameNumTextView.setPadding(0, 0, 0, 0);
            cancelImageView.setImageResource(R.drawable.ic_delete_black_24dp);
        } else if (mAction.equals(ACTION_ADD_TEST)) {
            backButton.setVisibility(View.GONE);
        }
        showTestNameNum();
        showQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                release();
                finish();
                break;
            case R.id.button_cancel:
                if (mAction.equals(ACTION_ADD_TEST)) {
                    cancelAddTest();
                } else {
                    deleteTest();
                }
                break;
            case R.id.button_last:
                lastQuestion();
                break;
            case R.id.button_next:
                nextQuesion();
                break;
            case R.id.button_add:
                addQuestion();
                break;
            case R.id.button_delete:
                deleteQuestion();
                break;
            default:
                break;
        }
    }

    private void deleteTest() {
        OnClickListener sureListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.toast(R.string.tch_deleted);
                TestDao testDao = new TestDao(EditTestActivity.this);
                testDao.delete(sTest);
                testDao.close();

                release();
                finish();
            }
        };
        DialogUtil.showNormalDialog(
                EditTestActivity.this,
                R.string.tch_comm_delete,
                String.format(getResources().getString(R.string.tch_delete_test_tip),
                        sTest.getName()), R.string.tch_comm_sure, sureListener,
                R.string.tch_comm_cancel, null);
    }

    private void lastQuestion() {
        if (mCurrentQuestionIndex > 0) {
            questionEditView.saveQuestion();
            if (!getCurrentQuestion().equal(mTempQuestion)) {
                ToastUtil.toast(String.format(
                        getResources().getString(R.string.tch_question_saved),
                        1 + mCurrentQuestionIndex));
            }
            mCurrentQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuesion() {
        questionEditView.saveQuestion();
        if (mCurrentQuestionIndex == sTest.getQuestionNum() - 1) {
            if (!sTest.isCompleted()) {
                int unCompletedIndex = sTest.getUnCompleteIndex() + 1;
                ToastUtil.toast(String.format(
                        getResources().getString(R.string.tch_question_unfinished),
                        unCompletedIndex));
            } else {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (ACTION_ADD_TEST.equals(mAction)) {
                            SetTestTimeActivity.startFromAddNewTest(EditTestActivity.this, sTest);
                        } else if (ACTION_EDIT_TEST.equals(mAction)) {
                            SetTestTimeActivity.startFromEditTest(EditTestActivity.this, sTest);
                        }
                        finish();
                    }
                }, 200);
            }
        } else {
            if (!getCurrentQuestion().equal(mTempQuestion)) {
                ToastUtil.toast(String.format(
                        getResources().getString(R.string.tch_question_saved),
                        1 + mCurrentQuestionIndex));
            }
            mCurrentQuestionIndex++;
            showQuestion();
        }
    }

    // four click functions: add ,delete ,next ,last.
    private void addQuestion() {
        if (sTest.getQuestionNum() == 9) {
            ToastUtil.toast(R.string.tch_question_uplimited);
            return;
        }
        final int questionNum = sTest.getQuestionNum() + 1;
        DialogUtil.showNormalDialog(this, R.string.tch_add,
                String.format(getString(R.string.tch_add_question_tip), questionNum),
                R.string.tch_comm_sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        sTest.addQuestion();
                        showTestNameNum();
                        switchNextButton();
                        ToastUtil.toast(String.format(
                                getResources().getString(R.string.tch_question_added), questionNum));
                    }
                }, R.string.tch_comm_cancel, null);
    }

    private void deleteQuestion() {
        DialogUtil.showNormalDialog(this, R.string.tch_comm_delete, String.format(
                getString(R.string.tch_delete_question_tip), mCurrentQuestionIndex + 1),
                R.string.tch_comm_sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (sTest.getQuestionNum() > 1) {
                            int originQuestionNum = sTest.getQuestionNum();
                            sTest.deleteQuestion(mCurrentQuestionIndex);
                            mCurrentQuestionIndex =
                                    mCurrentQuestionIndex == originQuestionNum - 1 ? sTest
                                            .getQuestionNum() - 1 : mCurrentQuestionIndex;
                            showTestNameNum();
                            showQuestion();
                            ToastUtil.toast(R.string.tch_delete_success);
                        } else {
                            finish();
                        }
                    }
                }, R.string.tch_comm_cancel, null);
    }

    private void showTestNameNum() {
        testNameNumTextView.setText(String.format(
                getResources().getString(R.string.tch_test_name_num), sTest.getName(),
                sTest.getQuestionNum()));
    }

    private void showQuestion() {
        questionEditView.showQuestion(mCurrentQuestionIndex, getCurrentQuestion());
        mTempQuestion = Question.clone(getCurrentQuestion());
        switchNextButton();
    }

    private Question getCurrentQuestion() {
        return sTest.getQuestion(mCurrentQuestionIndex);
    }

    private void switchNextButton() {
        if (mCurrentQuestionIndex == sTest.getQuestionNum() - 1) {
            nextButton.setText(getResources().getString(R.string.tch_comm_complete));
            nextButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            nextButton.setText(getResources().getString(R.string.tch_next_question));
            nextButton.setTextColor(getResources().getColor(R.color.black_700));
        }
    }

    @Override
    public void onBackPressed() {
        if (ACTION_ADD_TEST.equals(mAction)) {
            cancelAddTest();
        } else if (ACTION_EDIT_TEST.equals(mAction)) {
            release();
            finish();
        }
    }

    private void cancelAddTest() {
        OnClickListener sureListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                release();
                finish();
            }
        };
        DialogUtil.showNormalDialog(this, R.string.tch_comm_cancel,
                R.string.tch_not_save_if_cancel, R.string.tch_comm_sure, sureListener,
                R.string.tch_comm_cancel, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        questionEditView.onActivityResult(requestCode, resultCode, data);
    }

    private void release() {
        sTest.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionEditView = null;
        sTest = null;
        mTempQuestion = null;

    }

    /**
     * for add a new Test or Edit Test;
     */
    private static final String ACTION_ADD_TEST = "action_addtest";
    private static final String ACTION_EDIT_TEST = "action_edittest";

    /**
     * for better effect, use static test, don't need parse bitmap
     */
    public static void startAddNewTest(Context context, Test test) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_ADD_TEST);
        context.startActivity(intent);
        sTest = test;
    }

    public static void startEditTest(Context context, Test test) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_EDIT_TEST);
        context.startActivity(intent);
        sTest = test;
        sTest.changeStringToBitmap();
    }
}
