package com.kcb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午9:05:06
 */
//TODO change button to PaperButton or ButtonFlat
public class CheckInActivity extends BaseActivity {

    private Button getNumButton;
    private TextView numTextview;
    private Button rateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checkin);

        initView();
    }

    @Override
    protected void initView() {
        numTextview = (TextView) findViewById(R.id.num);
        getNumButton = (Button) findViewById(R.id.getnumber);
        getNumButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int intnum = (int) (Math.random() * 9000 + 1000);
                numTextview.setText(String.valueOf(intnum));
            }
        });
        rateButton = (Button) findViewById(R.id.button_rate);
        rateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckInActivity.this, CheckInDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {}
}
