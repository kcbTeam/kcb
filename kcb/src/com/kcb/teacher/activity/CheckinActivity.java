package com.kcb.teacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午9:05:06
 */
public class CheckinActivity extends Activity {

    // TODO
    // 1, init view
    // 2, add onclick listener
    // 3, replace Activity with BaseActivity
    // 4,
    private Button getNumButton;
    private TextView numTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_checkin);

        numTextview = (TextView) findViewById(R.id.num);
        getNumButton = (Button) findViewById(R.id.getnumber);


        getNumButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int intnum = (int) (Math.random() * 9000 + 1000);
                numTextview.setText(String.valueOf(intnum));
            }
        });
    }
}
