package com.kcb.teacher.activity;

import java.util.Date;

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
import com.kcb.teacher.adapter.SetTestTimeAdapter;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class SetTestTimeActivity extends BaseActivity implements OnItemClickListener {

    private TextView testNameTextView;
    private Slider testTimeSlider;
    private TextView testTimeTextView;

    private ButtonFlat finishButton;

    private ListView listView;

    private Test mTest;
    private SetTestTimeAdapter mAdapter;

    private int mPositonIndex;

    public final static String MODIFY_QUESTION_KEY = "modify_question";
    private final int MODIFY_QUESTION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_settesttime);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        testNameTextView = (TextView) findViewById(R.id.textview_test_name);

        finishButton = (ButtonFlat) findViewById(R.id.button_submit);
        finishButton.setOnClickListener(this);

        testTimeTextView = (TextView) findViewById(R.id.testtimeHint);
        testTimeTextView.setText(String
                .format(getResources().getString(R.string.testtimeFormat), 5));

        testTimeSlider = (Slider) findViewById(R.id.slider_testtime);
        testTimeSlider.setValue(5);
        testTimeSlider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                testTimeTextView.setText(String.format(
                        getResources().getString(R.string.testtimeFormat),
                        testTimeSlider.getValue()));
            }
        });

        listView = (ListView) findViewById(R.id.listview_questions);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        mTest = (Test) getIntent().getSerializableExtra(DATA_TEST);
        testNameTextView.setText(mTest.getName());
        mAdapter = new SetTestTimeAdapter(this, mTest);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPositonIndex = position;
        Intent intent = new Intent(this, ModifyQuestionActivty.class);
        intent.putExtra(MODIFY_QUESTION_KEY, mTest.getQuestion(position));
        intent.putExtra("TEST_NAME", mTest.getName());
        intent.putExtra("QUETION_ID", position);
        startActivityForResult(intent, MODIFY_QUESTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_QUESTION) {
            if (resultCode == ModifyQuestionActivty.MODIFY_SAVED) {
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
