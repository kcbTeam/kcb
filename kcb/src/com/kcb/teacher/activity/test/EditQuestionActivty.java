package com.kcb.teacher.activity.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Question;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.test.EditQuestionView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: ModifyQuestionActivty
 * @description:
 * @author: ZQJ
 * @date: 2015年5月27日 下午8:24:10
 */
public class EditQuestionActivty extends BaseActivity {

    private TextView questionIndexTextView;
    private ButtonFlat deleteButton;
    private ButtonFlat finishButton;

    private EditQuestionView questionEditView;

    // question and current question index;
    private String mTestName;
    public static Question sQuestion;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edit_question);
        StatusBarUtil.setTchStatusBarColor(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_index);

        deleteButton = (ButtonFlat) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);

        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);

        questionEditView = (EditQuestionView) findViewById(R.id.questioneditview);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mIndex = intent.getIntExtra(DATA_INDEX, 0);
        mTestName = intent.getStringExtra(DATA_TESTNAME);
        questionIndexTextView.setText(String.format(
                getResources().getString(R.string.tch_question_index), mIndex + 1));
        questionEditView.showQuestion(mTestName, mIndex, sQuestion);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete:
                DialogUtil.showNormalDialog(this, R.string.tch_comm_delete,
                        R.string.tch_delete_tip, R.string.tch_comm_sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra(DATA_INDEX, mIndex);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            }
                        }, R.string.tch_comm_cancel, null);
                break;
            case R.id.button_finish:
                questionEditView.saveQuestion();
                if (!sQuestion.isEditFinish()) {
                    ToastUtil.toast(R.string.tch_question_not_finish);
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onClick(finishButton);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        questionEditView.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionEditView.release();
        questionEditView = null;
        sQuestion = null;
    }

    /**
     * start
     */
    private static final String ACTION_EDIT_QUESTION = "action_editQuestion";
    public static final String DATA_INDEX = "data_index";
    public static final String DATA_TESTNAME = "data_testname";

    public static final int REQUEST_EDIT = 0;
    public static final int RESULT_DELETE = 1;

    public static void startForResult(Context context, String testName, int index, Question question) {
        Intent intent = new Intent(context, EditQuestionActivty.class);
        intent.setAction(ACTION_EDIT_QUESTION);
        intent.putExtra(DATA_INDEX, index);
        intent.putExtra(DATA_TESTNAME, testName);
        ((Activity) context).startActivityForResult(intent, REQUEST_EDIT);
        sQuestion = question;
    }
}
