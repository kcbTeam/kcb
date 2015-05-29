package com.kcb.teacher.activity;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.SetTestTimeAdapter;
import com.kcb.teacher.adapter.SetTestTimeAdapter.EditQuestionListener;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class SetTestTimeActivity extends BaseActivity {

    private TextView testNameTextView;
    private TextView testTimeTextView;
    private Slider testTimeSlider;

    private ButtonFlat finishButton;

    private ListView listView;

    private Test mTest;
    private SetTestTimeAdapter mAdapter;

    public final static String MODIFY_QUESTION_KEY = "modify_question";
    private final int MODIFY_QUESTION = 100;

    private EditQuestionListener mEditListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_settesttime);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        testNameTextView = (TextView) findViewById(R.id.textview_testname);
        testTimeTextView = (TextView) findViewById(R.id.textview_testtime);

        finishButton = (ButtonFlat) findViewById(R.id.button_submit);
        finishButton.setOnClickListener(this);

        testTimeSlider = (Slider) findViewById(R.id.slider_testtime);
        testTimeSlider.setValue(5);
        testTimeSlider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                testTimeTextView.setText(String.format(getString(R.string.settime_hint),
                        mTest.getQuestionNum(), value));
            }
        });

        listView = (ListView) findViewById(R.id.listview_questions);
    }

    @Override
    protected void initData() {
        mTest = (Test) getIntent().getSerializableExtra(DATA_TEST);
        testNameTextView.setText(mTest.getName());
        testTimeTextView.setText(String.format(getString(R.string.settime_hint),
                mTest.getQuestionNum(), 5));

        mEditListener = new EditQuestionListener() {

            @Override
            public void onEdit(int index, Question question) {
                EditQuestionActivty.startForResult(SetTestTimeActivity.this, index, question);
            }
        };
        mAdapter = new SetTestTimeAdapter(this, mTest, mEditListener);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditQuestionActivty.REQUEST_CODE_EDIT) {
            if (resultCode == RESULT_OK) {
                Question question = (Question) data.getSerializableExtra("MODIFIED");
                // TODO
                // mList.set(mPositonIndex, question);
                mAdapter.notifyDataSetChanged();
                ToastUtil.toast(R.string.modify_saved);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == finishButton) {
            mTest.setDate(new Date());
            mTest.setTime(testTimeSlider.getValue());
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
        Intent intent = new Intent(context, SetTestTimeActivity.class);
        test.changeTestToSerializable();
        intent.putExtra(DATA_TEST, test);
        context.startActivity(intent);
    }
}
