package com.kcb.teacher.activity.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.test.SetTestTimeAdapter;
import com.kcb.teacher.adapter.test.SetTestTimeAdapter.EditQuestionListener;
import com.kcb.teacher.database.test.TestDao;
import com.kcbTeam.R;

public class SetTestTimeActivity extends BaseActivity {

    private TextView titleTextView;
    private ButtonFlat deleteButton;
    private ButtonFlat cancelButton;
    private ButtonFlat finishButton;

    private TextView testTimeTextView;
    private Slider slider;

    private ListView listView;

    public static Test sTest;
    private EditQuestionListener mEditListener;
    private SetTestTimeAdapter mAdapter;

    private String mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_set_test_time);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.textview_title);
        deleteButton = (ButtonFlat) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);
        cancelButton = (ButtonFlat) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);
        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);

        testTimeTextView = (TextView) findViewById(R.id.textview_testtime);

        slider = (Slider) findViewById(R.id.slider_testtime);
        slider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                testTimeTextView.setText(String.format(getString(R.string.tch_set_test_time_tip),
                        sTest.getQuestionNum(), value));
            }
        });

        listView = (ListView) findViewById(R.id.listview_questions);
    }

    @Override
    protected void initData() {
        mAction = getIntent().getAction();
        if (ACTION_ADD_TEST.equals(mAction)) {
            deleteButton.setVisibility(View.GONE);
        } else if (ACTION_EDIT_TEST.equals(mAction)) {
            cancelButton.setVisibility(View.GONE);
            titleTextView.setText(sTest.getName());
        }

        testTimeTextView.setText(String.format(getString(R.string.tch_set_test_time_tip),
                sTest.getQuestionNum(), sTest.getTime()));
        slider.setValue(sTest.getTime());

        mEditListener = new EditQuestionListener() {

            @Override
            public void onEdit(int index, Question question) {
                EditQuestionActivty.startForResult(SetTestTimeActivity.this, sTest.getName(),
                        index, question);
            }
        };
        mAdapter = new SetTestTimeAdapter(this, sTest, mEditListener);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete:
                deleteTest();
                break;
            case R.id.button_cancel:
                OnClickListener sureListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                };
                DialogUtil.showNormalDialog(this, R.string.tch_comm_cancel,
                        R.string.tch_not_save_if_cancel, R.string.tch_comm_sure, sureListener,
                        R.string.tch_comm_cancel, null);
                break;
            case R.id.button_finish:
                sTest.setTime(slider.getValue());
                sTest.setDate(System.currentTimeMillis());
                TestDao testDao = new TestDao(this);
                if (ACTION_ADD_TEST.equals(mAction)) {
                    testDao.add(sTest);
                } else if (ACTION_EDIT_TEST.equals(mAction)) {
                    testDao.update(sTest);
                }
                testDao.close();
                ToastUtil.toast(R.string.tch_saved);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditQuestionActivty.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                mAdapter.notifyDataSetChanged();
                ToastUtil.toast(R.string.tch_saved);
            } else if (resultCode == EditQuestionActivty.RESULT_DELETE) {
                int index = data.getIntExtra(EditQuestionActivty.DATA_INDEX, 0);
                mAdapter.deleteItem(index);
                if (mAdapter.getCount() == 0) { // delete all question
                    deleteTestFromDatabase();
                    sTest.renameBitmap();
                    finish();
                } else {
                    sTest.renameBitmap();
                    mAdapter.notifyDataSetChanged();
                    testTimeTextView.setText(String.format(
                            getString(R.string.tch_set_test_time_tip), mAdapter.getCount(),
                            slider.getValue()));
                }
            }
        }
    }

    private void deleteTest() {
        OnClickListener sureListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                sTest.deleteBitmap();
                ToastUtil.toast(R.string.tch_deleted);
                deleteTestFromDatabase();
                finish();
            }
        };
        DialogUtil.showNormalDialog(
                SetTestTimeActivity.this,
                R.string.tch_comm_delete,
                String.format(getResources().getString(R.string.tch_delete_test_tip),
                        sTest.getName()), R.string.tch_comm_sure, sureListener,
                R.string.tch_comm_cancel, null);
    }

    private void deleteTestFromDatabase() {
        TestDao testDao = new TestDao(SetTestTimeActivity.this);
        testDao.delete(sTest);
        testDao.close();
    }

    @Override
    public void onBackPressed() {
        if (ACTION_ADD_TEST.equals(mAction)) {
            onClick(cancelButton);
        } else if (ACTION_EDIT_TEST.equals(mAction)) {
            onClick(finishButton);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sTest.release();
        sTest = null;
        mEditListener = null;
        mAdapter.release();
        mAdapter = null;
    }

    /**
     * start
     */
    private static final String ACTION_ADD_TEST = "action_addtest";
    private static final String ACTION_EDIT_TEST = "action_edittest";

    public static void startFromAddNewTest(Context context, Test test) {
        Intent intent = new Intent(context, SetTestTimeActivity.class);
        intent.setAction(ACTION_ADD_TEST);
        context.startActivity(intent);
        sTest = test;
    }

    public static void startFromEditTest(Context context, Test test) {
        Intent intent = new Intent(context, SetTestTimeActivity.class);
        intent.setAction(ACTION_EDIT_TEST);
        context.startActivity(intent);
        sTest = test;
    }
}
