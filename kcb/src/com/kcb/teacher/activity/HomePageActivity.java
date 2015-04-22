package com.kcb.teacher.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.teacher.fragment.SignInFragment;
import com.kcb.teacher.fragment.StudentCentreFragment;
import com.kcb.teacher.fragment.TestFragment;
import com.kcbTeam.R;

/**
 * 
 * @className: HomePageActivity
 * @description: home page
 * @author: ZQJ
 * @date: 2015年4月22日 上午9:39:56
 */
public class HomePageActivity extends BaseFragmentActivity {

    private Button exitButton;
    private Button courseSignInButton;
    private Button courseTestButton;
    private Button studentCenterButton;

    private SignInFragment mSignInFragment;
    private TestFragment mTestFragment;
    private StudentCentreFragment mStudentCentreFragment;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_homepage);
        initView();
    }

    @Override
    protected void initView() {

        exitButton = (Button) findViewById(R.id.button_exit);
        courseSignInButton = (Button) findViewById(R.id.button_course_signin);
        courseTestButton = (Button) findViewById(R.id.button_course_test);
        studentCenterButton = (Button) findViewById(R.id.button_student_center);

        exitButton.setOnClickListener(this);
        courseSignInButton.setOnClickListener(this);
        courseTestButton.setOnClickListener(this);
        studentCenterButton.setOnClickListener(this);

        setDefaultFragment();
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.button_exit:
                finish();
                break;
            case R.id.button_course_signin:
                setButtonTextColor(1);
                if (null == mSignInFragment) {
                    mSignInFragment = new SignInFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mSignInFragment);
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
                if (null == mStudentCentreFragment) {
                    mStudentCentreFragment = new StudentCentreFragment();
                }
                mFragmentTransaction.replace(R.id.fragment_content, mStudentCentreFragment);
                break;
            default:
                break;
        }
        mFragmentTransaction.commit();
    }

    private void setDefaultFragment() {
        setButtonTextColor(1);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mSignInFragment = new SignInFragment();
        mFragmentTransaction.replace(R.id.fragment_content, mSignInFragment);
        mFragmentTransaction.commit();
    }

    private void setButtonTextColor(int index) {
        switch (index) {
            case 1:
                courseSignInButton.setTextColor(getResources().getColor(R.color.blue));
                courseTestButton.setTextColor(getResources().getColor(R.color.black));
                studentCenterButton.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                courseSignInButton.setTextColor(getResources().getColor(R.color.black));
                courseTestButton.setTextColor(getResources().getColor(R.color.blue));
                studentCenterButton.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                courseSignInButton.setTextColor(getResources().getColor(R.color.black));
                courseTestButton.setTextColor(getResources().getColor(R.color.black));
                studentCenterButton.setTextColor(getResources().getColor(R.color.blue));
                break;
            default:
                break;
        }
    }
}
