package com.kcb.student.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcbTeam.R;

/**
 * @className: HomePageActivity
 * @description: Sign in and Test
 * @author: Ding
 * @date: 2015年4月23日 上午11:03:21
 */
public class HomeActivity extends BaseFragmentActivity {

    private final int INDEX_CHECKIN = 0;
    private final int INDEX_TEST = 1;

    private Fragment[] mFragments;
    private FragmentManager fragmentManager;

    private Button exitButton;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_home);

        initView();
    }

    @Override
    protected void initView() {
        exitButton = (Button) findViewById(R.id.button_exit);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        mFragments = new Fragment[2];
        fragmentManager = getSupportFragmentManager();

        mFragments[INDEX_CHECKIN] = fragmentManager.findFragmentById(R.id.fragment_sign);
        mFragments[INDEX_TEST] = fragmentManager.findFragmentById(R.id.fragment_test);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragments[INDEX_CHECKIN]).hide(mFragments[INDEX_TEST]);
        fragmentTransaction.show(mFragments[INDEX_CHECKIN]).commit();

        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog =
                        new AlertDialog.Builder(HomeActivity.this).setTitle("确定退出？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create();
                dialog.show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction =
                        fragmentManager.beginTransaction().hide(mFragments[INDEX_CHECKIN])
                                .hide(mFragments[INDEX_TEST]);
                switch (checkedId) {
                    case R.id.radiobutton_sign:
                        fragmentTransaction.show(mFragments[INDEX_CHECKIN]).commit();
                        break;
                    case R.id.radiobutton_test:
                        fragmentTransaction.show(mFragments[INDEX_TEST]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {}
}
