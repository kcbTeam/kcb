package com.kcb.teacher.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: TestActivity
 * @description:
 * @author: ljx
 * @date: 2015��4��28�� ����10:24:15
 */

public class TestActivity extends BaseActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_test);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        startButton = (Button) findViewById(R.id.button_starttest);
        startButton.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_starttest) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
            builder.setTitle(R.string.warmtip).setIcon(R.drawable.ic_launcher)
                    .setMessage(R.string.tipmessage);

            builder.setPositiveButton(R.string.tipyes, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startButton.setVisibility(View.GONE);
                    TextView tipTextView = (TextView) findViewById(R.id.textview_tip);
                    tipTextView.setVisibility(View.VISIBLE);
                }

            });

            builder.setNegativeButton(R.string.tipno, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            });

            builder.create().show();

        }
    }

}
