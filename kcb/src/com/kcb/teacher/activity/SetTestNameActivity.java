package com.kcb.teacher.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.AnimationUtil;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.model.test.Test;
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

    private FloatingEditText testNameEditText;

    private TextView setNumTextView;
    private Slider testNumSlider;

    private PaperButton finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_settestname);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testNameEditText = (FloatingEditText) findViewById(R.id.edittext_testname);

        setNumTextView = (TextView) findViewById(R.id.textview_setnum);
        setNumTextView.setText(String.format(getString(R.string.hellotip2), 3));

        testNumSlider = (Slider) findViewById(R.id.slider_testnum);
        testNumSlider.setValue(3);
        testNumSlider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                setNumTextView.setText(String.format(getString(R.string.hellotip2), value));
            }
        });

        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            // TODO detect if has same name test
            String name = testNameEditText.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                AnimationUtil.shake(testNameEditText);
            } else {
                EditTestActivity.startAddNewTest(SetTestNameActivity.this, new Test(name,
                        testNumSlider.getValue()));
                finish();
            }
        }
    };
}
