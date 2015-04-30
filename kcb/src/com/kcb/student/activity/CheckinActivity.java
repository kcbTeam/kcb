package com.kcb.student.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.student.adapter.CheckinRecycleAdapter;
import com.kcb.student.adapter.CheckinRecycleAdapter.ItemClickListener;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description: Check in,six Textview,one reclyclerView,one Button
 * @author: Tao Li
 * @date: 2015-4-24 下午9:16:22
 */
public class CheckinActivity extends BaseActivity implements ItemClickListener {

    private TextView num1TextView;
    private TextView num2TextView;
    private TextView num3TextView;
    private TextView num4TextView;
    private RecyclerView recyclerView;
    private Button finishButton;

    // TODO rename to mItems
    private String[] mItems;
    private CheckinRecycleAdapter mAdapter;
    private int currentInputIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_checkin);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        num1TextView = (TextView) findViewById(R.id.textview_shownum1);
        num2TextView = (TextView) findViewById(R.id.textview_shownum2);
        num3TextView = (TextView) findViewById(R.id.textview_shownum3);
        num4TextView = (TextView) findViewById(R.id.textview_shownum4);
        finishButton = (Button) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mItems = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "清除", "0", "×"};
        mAdapter = new CheckinRecycleAdapter(mItems);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String passwordString =
                new String(num1TextView.getText().toString() + num2TextView.getText().toString()
                        + num3TextView.getText().toString() + num4TextView.getText().toString());
        ToastUtil.toast(passwordString);
    }

    @Override
    protected void initData() {}

    @Override
    public void onItemClick(View view, int postion) {

        if (postion == 9) {
            num1TextView.setText("");
            num2TextView.setText("");
            num3TextView.setText("");
            num4TextView.setText("");
            currentInputIndex = 0;
        } else {
            if (postion == 11) {
                switch (currentInputIndex) {
                    case 1:
                        num1TextView.setText("");
                        break;
                    case 2:
                        num2TextView.setText("");
                        break;
                    case 3:
                        num3TextView.setText("");
                        break;
                    case 4:
                        num4TextView.setText("");
                        break;
                    default:
                        break;
                }
                currentInputIndex--;
            } else {
                if (postion == 10) {
                    postion = -1;
                }
                if (currentInputIndex != 4) {
                    currentInputIndex++;
                    switch (currentInputIndex) {
                        case 1:
                            num1TextView.setText(String.valueOf(postion + 1));
                            break;
                        case 2:
                            num2TextView.setText(String.valueOf(postion + 1));
                            break;
                        case 3:
                            num3TextView.setText(String.valueOf(postion + 1));
                            break;
                        case 4:
                            num4TextView.setText(String.valueOf(postion + 1));
                            break;
                        default:
                            break;
                    }
                }

            }
        }
    }
}
