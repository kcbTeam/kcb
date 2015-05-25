package com.kcb.teacher.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.AnimationUtil;
import com.kcb.common.util.ToastUtil;
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
public class EditTestFirstActivity extends BaseActivity {

    private ButtonFlat backButton;
    private TextView numtip;
    private FloatingEditText nameEditText;
    private Slider slider;
    private PaperButton finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edittestfirst);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        nameEditText = (FloatingEditText) findViewById(R.id.edittext_testname);

        slider = (Slider) findViewById(R.id.sliderNumber);
        slider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                numtip.setText("共" + String.valueOf(value) + "题");
            }
        });

        numtip = (TextView) findViewById(R.id.edittext_num);
        numtip.setText("共" + String.valueOf(slider.getValue()) + "题");

        finishButton = (PaperButton) findViewById(R.id.button_editfinish);
        finishButton.setOnClickListener(mClickListener);
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            String name = nameEditText.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                AnimationUtil.shake(nameEditText);
            } else {
                EditTestActivity.startAddNewTest(EditTestFirstActivity.this,
                        new Test(name, slider.getValue()));
            }
        }
    };

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
}
