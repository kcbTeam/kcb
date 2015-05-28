package com.kcb.teacher.activity;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterQuestions;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class SubmitTestActivity extends BaseActivity implements OnItemClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "SubmitTestPaper";

    private ListView questionListView;
    private ListAdapterQuestions mAdapter;
    private List<Question> mList;
    private Test mCurrentTest;

    private TextView testName;
    private Slider testTime;
    private TextView texttimeHint;

    private ButtonFlat submitButton;

    public final static String MODIFY_QUESTION_KEY = "modify_question";
    private final int MODIFY_QUESTION = 100;

    private int mPositonIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_submittest);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        questionListView = (ListView) findViewById(R.id.listview_questions);
        mAdapter = new ListAdapterQuestions(this, mList);
        questionListView.setAdapter(mAdapter);
        questionListView.setOnItemClickListener(this);

        testName = (TextView) findViewById(R.id.textview_test_name);
        testName.setText("测试：" + mCurrentTest.getName());

        submitButton = (ButtonFlat) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(this);

        testTime = (Slider) findViewById(R.id.slider_testtime);
        texttimeHint = (TextView) findViewById(R.id.testtimeHint);
        texttimeHint.setText(String.format(getResources().getString(R.string.testtimeFormat),
                testTime.getValue()));
        testTime.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                texttimeHint.setText(String.format(getResources()
                        .getString(R.string.testtimeFormat), testTime.getValue()));
            }
        });
    }

    @Override
    protected void initData() {
        mCurrentTest = (Test) getIntent().getSerializableExtra(DATA_TEST);
        mList = mCurrentTest.getQuestions();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPositonIndex = position;
        Intent intent = new Intent(this, ModifyQuestionActivty.class);
        intent.putExtra(MODIFY_QUESTION_KEY, mCurrentTest.getQuestion(position));
        intent.putExtra("TEST_NAME", mCurrentTest.getName());
        intent.putExtra("QUETION_ID", position);
        startActivityForResult(intent, MODIFY_QUESTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_QUESTION) {
            if (resultCode == ModifyQuestionActivty.MODIFY_SAVED) {
                Question question = (Question) data.getSerializableExtra("MODIFIED");
                mList.set(mPositonIndex, question);
                mAdapter.notifyDataSetChanged();
                ToastUtil.toast(R.string.modify_saved);
            }
        }

    }

    @Override
    public void onClick(View v) {

        if (v == submitButton) {
            mCurrentTest.setDate(new Date());
            mCurrentTest.setTime(testTime.getValue());
            // TODO:change mcurrenttes to json object
        }

    }

    @Override
    public void onBackPressed() {
        OnClickListener sureListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        DialogUtil.showNormalDialog(this, R.string.leave, R.string.sureLeave, R.string.sure,
                sureListener, R.string.cancel, null);
    }

    private static final String DATA_TEST = "current_course_key";

    public static void start(Context context, Test test) {
        Intent intent = new Intent(context, SubmitTestActivity.class);
        intent.putExtra(DATA_TEST, test);
        context.startActivity(intent);
    }
}
