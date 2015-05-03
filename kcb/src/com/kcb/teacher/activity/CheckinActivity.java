package com.kcb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午9:05:06
 */
public class CheckInActivity extends BaseActivity {

    private ButtonFlat giveupButton;
    private PaperButton getNumButton;
    private PaperButton startStopButton;
    private TextView numTextview;
    private PaperButton rateButton;
    private PaperButton finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checkin);

        initView();
    }

    @Override
    protected void initView() {
        giveupButton = (ButtonFlat) findViewById(R.id.button_giveup);
        giveupButton.setOnClickListener(this);
        giveupButton.setRippleSpeed(6f);
        
        getNumButton = (PaperButton) findViewById(R.id.button_getnum);
        getNumButton.setOnClickListener(mClickListener);

        numTextview = (TextView) findViewById(R.id.num);

        startStopButton = (PaperButton) findViewById(R.id.button_startstop);
        startStopButton.setOnClickListener(mClickListener);
        rateButton = (PaperButton) findViewById(R.id.button_rate);
        rateButton.setOnClickListener(mClickListener);

        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        if (v == giveupButton) {

        }
    }

    private CustomOnClickListener mClickListener = new CustomOnClickListener(
            CustomOnClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            if (v == getNumButton) {
                int intnum = (int) (Math.random() * 9000 + 1000);
                numTextview.setText(String.valueOf(intnum));
            } else if (v == startStopButton) {

            } else if (v == rateButton) {
                Intent intent = new Intent(CheckInActivity.this, CheckInDetailsActivity.class);
                startActivity(intent);
            } else if (v == finishButton) {
                finish();
            }
        }
    };
}
