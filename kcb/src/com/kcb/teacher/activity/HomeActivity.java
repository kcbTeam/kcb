package com.kcb.teacher.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.kcb.common.base.BaseFragmentActivity;
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

    private Button exitButton;
    private Button checkInButton;
    private Button testButton;
    private Button stuCenterButton;

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

        exitButton = (Button) findViewById(R.id.button_exit);
        checkInButton = (Button) findViewById(R.id.button_course_signin);
        testButton = (Button) findViewById(R.id.button_course_test);
        stuCenterButton = (Button) findViewById(R.id.button_student_center);

        exitButton.setOnClickListener(this);
        checkInButton.setOnClickListener(this);
        testButton.setOnClickListener(this);
        stuCenterButton.setOnClickListener(this);

        setDefaultFragment();
    }

    @Override
    protected void initData() {}

    private void setDefaultFragment() {
        onClick(checkInButton);
    }

    @Override
    public void onClick(View v) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.button_exit:
                finish();
                break;
            case R.id.button_course_signin:
                setButtonTextColor(1);
                if (null == mCheckInFragment) {
                    mCheckInFragment = new CheckInFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mCheckInFragment);
                break;
            case R.id.button_course_test:
                setButtonTextColor(2);
                if (null == mTestFragment) {
                    mTestFragment = new TestFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mTestFragment);
                break;
            case R.id.button_student_center:
                setButtonTextColor(3);
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
        checkInButton.setTextColor(getResources().getColor(R.color.black));
        testButton.setTextColor(getResources().getColor(R.color.black));
        stuCenterButton.setTextColor(getResources().getColor(R.color.black));
        switch (index) {
            case INDEX_CHECKIN:
                checkInButton.setTextColor(getResources().getColor(R.color.blue));
                break;
            case INDEX_TEST:
                testButton.setTextColor(getResources().getColor(R.color.blue));
                break;
            case INDEX_STUCENTER:
                stuCenterButton.setTextColor(getResources().getColor(R.color.blue));
                break;
            default:
                break;
        }
    }

}
