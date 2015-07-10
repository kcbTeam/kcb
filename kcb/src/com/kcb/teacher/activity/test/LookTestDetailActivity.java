package com.kcb.teacher.activity.test;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.view.test.ShowQuestionView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.database.test.QuestionResult;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.model.KAccount;
import com.kcbTeam.R;

public class LookTestDetailActivity extends BaseActivity {

    private static final String TAG = LookTestDetailActivity.class.getName();

    private ButtonFlat backButton;
    private TextView testNameNumTextView;
    private ButtonFlat refreshButton;
    private SmoothProgressBar progressBar;

    private ShowQuestionView showQuestionView;

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;

    public static Test sTest;
    private int mQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_look_test_detail);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testNameNumTextView = (TextView) findViewById(R.id.textview_test_name_num);
        testNameNumTextView.setText(String.format(getString(R.string.tch_test_name_num),
                sTest.getName(), sTest.getQuestionNum()));

        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);

        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        showQuestionView = (ShowQuestionView) findViewById(R.id.questionview);
        showQuestionView.setType(ShowQuestionView.TYPE_TEACHER);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        lastButton.setRippleColor(getResources().getColor(R.color.black_400));

        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        nextButton.setRippleColor(getResources().getColor(R.color.black_400));
    }

    @Override
    protected void initData() {
        showQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_refresh:
                refresh();
                break;
            case R.id.button_last:
                lastQuestion();
                break;
            case R.id.button_next:
                nextQuestin();
                break;
            default:
                break;
        }
    }

    /**
     * 获取此测试的结果，即每道题的正确率；
     */
    private void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        // 根据tchId和testId获取结果
        JsonArrayRequest request =
                new JsonArrayRequest(Method.GET, UrlUtil.getTchTestLookresultdetailUrl(
                        KAccount.getAccountId(), sTest.getId()), new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        LogUtil.i(TAG, response.toString());
                        // response为多个题目的id和正确率
                        TestDao testDao = new TestDao(LookTestDetailActivity.this);
                        for (int i = 0; i < response.length(); i++) {
                            QuestionResult result =
                                    QuestionResult.fromJsonObject(response.optJSONObject(i));
                            sTest.setQuestionResult(result);
                        }
                        // 更新数据库中的测试
                        testDao.update(sTest);
                        testDao.close();
                        // 更新UI
                        showQuestion();
                        progressBar.hide(LookTestDetailActivity.this);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(LookTestDetailActivity.this);
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    private void lastQuestion() {
        if (mQuestionIndex > 0) {
            mQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuestin() {
        if (mQuestionIndex != sTest.getQuestionNum() - 1) {
            mQuestionIndex++;
            showQuestion();
        }
    }

    private void showQuestion() {
        showQuestionView.showQuestion(mQuestionIndex, sTest.getQuestion(mQuestionIndex));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showQuestionView.release();
        showQuestionView = null;
        sTest = null;
    }

    /**
     * start
     */
    public static void start(Context context, Test test) {
        Intent intent = new Intent(context, LookTestDetailActivity.class);
        context.startActivity(intent);
        sTest = test;
    }
}
