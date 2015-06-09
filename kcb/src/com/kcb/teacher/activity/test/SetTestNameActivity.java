package com.kcb.teacher.activity.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.model.test.Test;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.database.test.TestDao;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestFirstActivity
 * @description:
 * @author: ljx
 * @date: 2015-5-15 下午11:32:33
 */
public class SetTestNameActivity extends BaseActivity {

    private ButtonFlat backButton;
    private ButtonFlat finishButton;

    private FloatingEditText testNameEditText;

    private TextView setNumTextView;
    private Slider testNumSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_set_test_name);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);

        testNameEditText = (FloatingEditText) findViewById(R.id.edittext_testname);

        setNumTextView = (TextView) findViewById(R.id.textview_setnum);
        setNumTextView.setText(String.format(getString(R.string.tch_set_question_num), 3));

        testNumSlider = (Slider) findViewById(R.id.slider_testnum);
        testNumSlider.setValue(3);
        testNumSlider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                setNumTextView.setText(String.format(getString(R.string.tch_set_question_num),
                        value));
            }
        });
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_finish:
                String name = testNameEditText.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    AnimationUtil.shake(testNameEditText);
                } else {
                    TestDao testDao = new TestDao(SetTestNameActivity.this);
                    if (testDao.hasTest(name)) {
                        ToastUtil.toast(R.string.tch_has_same_test_name);
                    } else {
                        EditTestActivity.startAddNewTest(SetTestNameActivity.this, new Test(name,
                                testNumSlider.getValue()));
                        finish();
                    }
                    testDao.close();
                }
                break;
            default:
                break;
        }
    }
}
