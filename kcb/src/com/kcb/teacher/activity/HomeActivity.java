package com.kcb.teacher.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.fragment.CheckInFragment;
import com.kcb.teacher.fragment.StuCentreFragment;
import com.kcb.teacher.fragment.TestFragment;
import com.kcbTeam.R;

/**
 * 
 * @className: HomeActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:21:55
 */
public class HomeActivity extends BaseFragmentActivity {

    private final int INDEX_CHECKIN = 0;
    private final int INDEX_TEST = 1;
    private final int INDEX_STUCENTER = 2;

    private ButtonFlat exitButton;
    private ButtonFlat checkInButton;
    private ButtonFlat testButton;
    private ButtonFlat stuCenterButton;

    private CheckInFragment mCheckInFragment;
    private TestFragment mTestFragment;
    private StuCentreFragment mStuCentreFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_home);

        initView();
    }

    @Override
    protected void initView() {
        exitButton = (ButtonFlat) findViewById(R.id.button_exit);
        exitButton.setOnClickListener(this);
        exitButton.setRippleSpeed(6f);
        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        stuCenterButton = (ButtonFlat) findViewById(R.id.button_stucenter);
        stuCenterButton.setOnClickListener(this);

        mFragmentManager = getSupportFragmentManager();

        setDefaultFragment();
    }

    @Override
    protected void initData() {}

    private void setDefaultFragment() {
        onClick(checkInButton);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.button_exit:
                finish();
                break;
            case R.id.button_checkin:
                setButtonTextColor(INDEX_CHECKIN);
                if (null == mCheckInFragment) {
                    mCheckInFragment = new CheckInFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mCheckInFragment);
                break;
            case R.id.button_test:
                setButtonTextColor(INDEX_TEST);
                if (null == mTestFragment) {
                    mTestFragment = new TestFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mTestFragment);
                break;
            case R.id.button_stucenter:
                setButtonTextColor(INDEX_STUCENTER);
                if (null == mStuCentreFragment) {
                    mStuCentreFragment = new StuCentreFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mStuCentreFragment);
                break;
            default:
                break;
        }
        mFragmentTransaction.commit();
    }

    private void setButtonTextColor(int index) {
        Resources res = getResources();
        checkInButton.setTextColor(res.getColor(R.color.black));
        testButton.setTextColor(res.getColor(R.color.black));
        stuCenterButton.setTextColor(res.getColor(R.color.black));
        switch (index) {
            case INDEX_CHECKIN:
                checkInButton.setTextColor(res.getColor(R.color.blue));
                break;
            case INDEX_TEST:
                testButton.setTextColor(res.getColor(R.color.blue));
                break;
            case INDEX_STUCENTER:
                stuCenterButton.setTextColor(res.getColor(R.color.blue));
                break;
            default:
                break;
        }
    }
}
