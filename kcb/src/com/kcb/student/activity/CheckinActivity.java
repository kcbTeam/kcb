package com.kcb.student.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.student.adapter.MyRecycleAdapter;
import com.kcb.student.util.ItemBeam;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description: Check in,six Textview,one reclyclerView��one Button
 * @author: Tao Li
 * @date: 2015-4-22 ����8:05:53
 */
// TODO
// 1, use utf-8 encode
// 2, rename myAdapter to mAdapter
// 3, replace List<ItemBeam>, use String[]
// 4, use implement MyItemClickListener
public class CheckinActivity extends BaseActivity {


    private MyRecycleAdapter myAdapter;
    private RecyclerView mRecyclerView;
    private List<ItemBeam> itemBeams;
    private TextView num1TextView;
    private TextView num2TextView;
    private TextView num3TextView;
    private TextView num4TextView;
    private Button finishButton;
    private int flagCount4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_checkin);
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
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        itemBeams = new ArrayList<ItemBeam>();
        itemBeams.add(new ItemBeam("1"));
        itemBeams.add(new ItemBeam("2"));
        itemBeams.add(new ItemBeam("3"));
        itemBeams.add(new ItemBeam("4"));
        itemBeams.add(new ItemBeam("5"));
        itemBeams.add(new ItemBeam("6"));
        itemBeams.add(new ItemBeam("7"));
        itemBeams.add(new ItemBeam("8"));
        itemBeams.add(new ItemBeam("9"));
        itemBeams.add(new ItemBeam("���"));
        itemBeams.add(new ItemBeam("0"));
        itemBeams.add(new ItemBeam("��"));
        myAdapter = new MyRecycleAdapter(itemBeams);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyRecycleAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 9) {
                    num1TextView.setText("");
                    num2TextView.setText("");
                    num3TextView.setText("");
                    num4TextView.setText("");
                    flagCount4 = 0;
                } else {
                    if (position == 11) {
                        switch (flagCount4) {
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
                        flagCount4--;
                    } else {
                        if (position == 10) {
                            position = -1;
                        }
                        if (flagCount4 != 4) {
                            flagCount4++;
                            switch (flagCount4) {
                                case 1:
                                    num1TextView.setText(String.valueOf(position + 1));
                                    break;
                                case 2:
                                    num2TextView.setText(String.valueOf(position + 1));
                                    break;
                                case 3:
                                    num3TextView.setText(String.valueOf(position + 1));
                                    break;
                                case 4:
                                    num4TextView.setText(String.valueOf(position + 1));
                                    break;
                                default:
                                    break;
                            }
                        }

                    }
                }
            }
        });
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

}
