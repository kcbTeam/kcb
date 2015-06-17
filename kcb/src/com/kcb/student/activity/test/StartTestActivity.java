package com.kcb.student.activity.test;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.answer.TestAnswer;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.dialog.MaterialDialog;
import com.kcb.common.view.test.AnswerQuestionView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.database.test.TestDao;
import com.kcb.student.model.account.KAccount;
import com.kcbTeam.R;

/**
 * 
 * @className: TestActivity
 * @description:
 * @author: Tao Li
 * @date: 2015-5-17 上午10:53:44
 */
public class StartTestActivity extends BaseActivity {

    private static final String TAG = StartTestActivity.class.getName();

    private TextView testNameNumTextView;
    private TextView timeTextView;
    private SmoothProgressBar progressBar;

    private AnswerQuestionView answerQuestionView;

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;
    private ButtonFlat submitButton;

    // test and current question index;
    public static Test sTest;
    private int mQuestionIndex;

    // when click last/next, show a mTempQuestion, used for compare it is changed or not;
    private Question mTempQuestion;

    private TestAnswer mTestAnswer;

    private int mRemainTime; // seconds
    private Handler mHandler;
    private final int MESSAGE_TIME_REDUCE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_start_test);
        StatusBarUtil.setStuStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        testNameNumTextView = (TextView) findViewById(R.id.textview_test_name_num);
        timeTextView = (TextView) findViewById(R.id.textview_time);
        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        answerQuestionView = (AnswerQuestionView) findViewById(R.id.answerquestionview);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        submitButton = (ButtonFlat) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(this);
        submitButton.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void initData() {
        testNameNumTextView.setText(String.format(getString(R.string.stu_test_name_num),
                sTest.getName(), sTest.getQuestionNum()));

        mTestAnswer = new TestAnswer(sTest);

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
        if (mQuestionIndex > 0) {
            saveAnswer();
            mQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuesion() {
        if (mQuestionIndex < sTest.getQuestionNum() - 1) {
            saveAnswer();
            mQuestionIndex++;
            showQuestion();
        }
    }

    private void submitAnswer() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        answerQuestionView.saveAnswer();

        // get unfinished question index
        List<Integer> unFinishedIndexs = mTestAnswer.getUnFinishedIndex();
        int unFinishedSize = unFinishedIndexs.size();

        // get dialog message
        String dialogMessage;
        if (unFinishedSize > 0) {
            String unFinishedIndexString = "";
            for (int i = 0; i < unFinishedSize; i++) {
                if (i == 0) {
                    unFinishedIndexString = String.valueOf(i + 1);
                } else {
                    unFinishedIndexString += "，";
                    unFinishedIndexString += String.valueOf(i + 1);
                }
            }
            dialogMessage =
                    String.format(getString(R.string.stu_submit_test_miss), unFinishedIndexString);
        } else {
            dialogMessage = getString(R.string.stu_submit_test_finish);
        }

        DialogUtil.showNormalDialog(StartTestActivity.this, R.string.stu_comm_submit,
                dialogMessage, R.string.stu_comm_submit, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        saveAnswerToDataBase();
                        submitAnswerToServer();
                    }
                }, R.string.stu_comm_cancel, null);
    }

    private void saveAnswerToDataBase() {
        TestDao testDao = new TestDao(StartTestActivity.this);
        testDao.add(sTest);
        testDao.close();
    }

    private static final String KEY_STUID = "sutid";
    private static final String KEY_TESTANSWER = "testanswer";

    private void submitAnswerToServer() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_STUID, KAccount.getAccountId());
            jsonObject.put(KEY_TESTANSWER, mTestAnswer.toJsonObject());
        } catch (JSONException e) {}
        JsonObjectRequest request =
                new JsonObjectRequest(Method.POST, UrlUtil.getStuTestFinishUrl(), jsonObject,
                        new Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                ToastUtil.toast(R.string.stu_answer_submitted);
                                progressBar.hide(StartTestActivity.this);
                                finish();
                            }
                        }, new ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.hide(StartTestActivity.this);
                                ResponseUtil.toastError(error);
                            }
                        });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    private void showQuestion() {
        mTempQuestion = Question.clone(getCurrentQuestion());
        answerQuestionView.showQuestion(getCurrentQuestion(), mQuestionIndex);
    }

    private void showRemainTime() {
        timeTextView.setText(String.format(getString(R.string.stu_remain_time), mRemainTime / 60,
                mRemainTime % 60));
    }

    private void showTimeEndDialog() {
        MaterialDialog dialog =
                DialogUtil.showNormalDialog(StartTestActivity.this, R.string.stu_comm_tip,
                        R.string.stu_test_time_end, R.string.stu_comm_submit,
                        new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }, -1, null);
        dialog.setCancelable(false);
    }

    private void saveAnswer() {
        answerQuestionView.saveAnswer();
        if (!getCurrentQuestion().equal(mTempQuestion)) {
            mTestAnswer.saveQuestionAnswer(getCurrentQuestion());
            ToastUtil.toast(String.format(getResources().getString(R.string.stu_question_save),
                    mQuestionIndex + 1));
        }
    }

    private Question getCurrentQuestion() {
        return sTest.getQuestion(mQuestionIndex);
    }

    @Override
    public void onBackPressed() {
        if (mRemainTime > 0) {
            ToastUtil.toast(R.string.stu_test_unfinish);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        sTest.release();
        sTest = null;
        mTempQuestion.release();
        mTempQuestion = null;
        mTestAnswer = null;
        answerQuestionView.release();
        answerQuestionView = null;
    }

    /**
     * start
     */
    private static final String DATA_REMAIN_TIME = "remaintime";

    public static void start(Context context, Test test, int remainTime) {
        Intent intent = new Intent(context, StartTestActivity.class);
        intent.putExtra(DATA_REMAIN_TIME, remainTime);
        context.startActivity(intent);
        sTest = test;
    }
}
