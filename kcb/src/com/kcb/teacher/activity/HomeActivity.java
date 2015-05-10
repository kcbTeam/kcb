package com.kcb.teacher.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
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

    private Fragment mCurrentFragment;

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
        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        stuCenterButton = (ButtonFlat) findViewById(R.id.button_stucenter);
        stuCenterButton.setOnClickListener(this);

        setDefaultFragment();
    }

    @Override
    protected void initData() {}

    private void setDefaultFragment() {
        mCurrentFragment = new CheckInFragment();
        onClick(checkInButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_exit:
                DialogUtil.showNormalDialog(this, R.string.destroy, R.string.destroy_tip,
                        R.string.sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                KAccount.deleteAccount();
                                LoginActivity.start(HomeActivity.this);
                                finish();
                            }
                        }, R.string.cancel, null);
                break;
            case R.id.button_checkin:
                setButtonTextColor(INDEX_CHECKIN);
                if (null == mCheckInFragment) {
                    mCheckInFragment = new CheckInFragment();
                }
                switchContent(mCheckInFragment);
                break;
            case R.id.button_test:
                setButtonTextColor(INDEX_TEST);
                if (null == mTestFragment) {
                    mTestFragment = new TestFragment();
                }
                switchContent(mTestFragment);
                break;
            case R.id.button_stucenter:
                setButtonTextColor(INDEX_STUCENTER);
                if (null == mStuCentreFragment) {
                    mStuCentreFragment = new StuCentreFragment();
                }
                switchContent(mStuCentreFragment);
                break;
            default:
                break;
        }
    }

    private void switchContent(Fragment nextFragment) {
        if (mCurrentFragment != nextFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!nextFragment.isAdded()) {
                transaction.hide(mCurrentFragment).add(R.id.fragment_content, nextFragment)
                        .commit();
            } else {
                transaction.hide(mCurrentFragment).show(nextFragment).commit();
            }
            mCurrentFragment = nextFragment;
        }
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

    private boolean hasClickBack = false;

    @Override
    public void onBackPressed() {
        if (!hasClickBack) {
            hasClickBack = true;
            ToastUtil.toast(R.string.click_again_exit_app);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    hasClickBack = false;
                }
            }, 2000);
        } else {
            System.exit(0);
        }
    }

    /**
     * 
     * @title: start
     * @description: start HomeActivity from StartActivity or LoginActivity
     * @author: wanghang
     * @date: 2015-5-10 上午11:27:53
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
