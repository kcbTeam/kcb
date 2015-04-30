package com.kcb.student.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
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

    private Button exitButton;
    private ButtonFlat checkInButton;
    private ButtonFlat testButton;

    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_home);

        initView();
    }

    @Override
    protected void initView() {
        exitButton = (Button) findViewById(R.id.button_exit);
        exitButton.setOnClickListener(this);
        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        checkInButton.setRippleSpeed(18f);
        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        testButton.setRippleSpeed(18f);

        mFragmentManager = getSupportFragmentManager();

        mFragments = new Fragment[2];
        mFragments[INDEX_CHECKIN] = mFragmentManager.findFragmentById(R.id.fragment_sign);
        mFragments[INDEX_TEST] = mFragmentManager.findFragmentById(R.id.fragment_test);

        showDefaultFragment();
    }

    @Override
    protected void initData() {}

    private void showDefaultFragment() {
        onClick(checkInButton);
    }

    @Override
    public void onClick(View v) {
        if (v == exitButton) {
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
        } else {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.hide(mFragments[INDEX_CHECKIN]).hide(mFragments[INDEX_TEST]);
            if (v == checkInButton) {
                setButtonTextColor(INDEX_CHECKIN);
                fragmentTransaction.show(mFragments[INDEX_CHECKIN]).commit();
            } else if (v == testButton) {
                setButtonTextColor(INDEX_TEST);
                fragmentTransaction.show(mFragments[INDEX_TEST]).commit();
            }
        }
    }

    private void setButtonTextColor(int index) {
        Resources resources = getResources();
        checkInButton.setTextColor(resources.getColor(R.color.gray));
        testButton.setTextColor(resources.getColor(R.color.gray));
        if (index == INDEX_CHECKIN) {
            checkInButton.setTextColor(resources.getColor(R.color.blue));
        } else {
            testButton.setTextColor(resources.getColor(R.color.blue));
        }
    }
}
