package com.kcb.teacher.activity.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.database.checkin.CheckInDao;
import com.kcb.teacher.database.students.StudentDao;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.fragment.CheckInFragment;
import com.kcb.teacher.fragment.StuCentreFragment;
import com.kcb.teacher.fragment.TestFragment;
import com.kcb.teacher.model.account.KAccount;
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

    private TextView userNameTextView;
    private ButtonFlat settingButton;
    private ButtonFlat checkInButton;
    private ButtonFlat testButton;
    private ButtonFlat stuCenterButton;

    // add some popupwindow by ljx
    private PopupWindow mPopupWindow;

    private CheckInFragment mCheckInFragment;
    private TestFragment mTestFragment;
    private StuCentreFragment mStuCentreFragment;

    // TODO why need? can replace by index?
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_home);
        initView();
    }

    @Override
    protected void initView() {
        userNameTextView = (TextView) findViewById(R.id.textview_username);
        userNameTextView.setText(KAccount.getAccountName());

        settingButton = (ButtonFlat) findViewById(R.id.button_setting);
        settingButton.setOnClickListener(this);

        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        checkInButton.setTextSize(16);
        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        testButton.setTextSize(16);
        stuCenterButton = (ButtonFlat) findViewById(R.id.button_stucenter);
        stuCenterButton.setOnClickListener(this);
        stuCenterButton.setTextSize(16);

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
            case R.id.button_setting:
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    initPopupWindow();
                    mPopupWindow.showAsDropDown(v, 0, 0);
                }
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
        checkInButton.setTextColor(res.getColor(R.color.black_500));
        testButton.setTextColor(res.getColor(R.color.black_500));
        stuCenterButton.setTextColor(res.getColor(R.color.black_500));
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

    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    public void initPopupWindow() {
        View customView =
                getLayoutInflater().inflate(R.layout.tch_popupwindow_setting, null, false);
        mPopupWindow =
                new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);

        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_modifypassword:
                        mPopupWindow.dismiss();
                        Intent intent = new Intent(HomeActivity.this, ModifyPasswordActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.button_exit:
                        mPopupWindow.dismiss();
                        exitAccount();
                        break;
                    default:
                        break;
                }
            }
        };

        ButtonFlat modifyButton = (ButtonFlat) customView.findViewById(R.id.button_modifypassword);
        modifyButton.setOnClickListener(clickListener);
        modifyButton.setTextColor(getResources().getColor(R.color.black_700));
        modifyButton.setTextSize(14);
        ButtonFlat exitButton = (ButtonFlat) customView.findViewById(R.id.button_exit);
        exitButton.setOnClickListener(clickListener);
        exitButton.setTextColor(getResources().getColor(R.color.black_700));
        exitButton.setTextSize(14);
    }

    private void exitAccount() {
        DialogUtil.showNormalDialog(HomeActivity.this, R.string.tch_exit_account,
                R.string.tch_exit_account_tip, R.string.tch_comm_sure, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // delete account
                        KAccount.deleteAccount();

                        // delete checkin result
                        CheckInDao checkInDao = new CheckInDao(HomeActivity.this);
                        checkInDao.deleteAll();
                        checkInDao.close();

                        // delete test result
                        TestDao testDao = new TestDao(HomeActivity.this);
                        testDao.deleteAll();
                        testDao.close();

                        // delete student
                        StudentDao studentDao = new StudentDao(HomeActivity.this);
                        studentDao.deleteAll();
                        studentDao.close();

                        // goto login activity
                        LoginActivity.start(HomeActivity.this);
                        finish();
                    }
                }, R.string.tch_comm_cancel, null);
    }

    private boolean hasClickBack = false;

    @Override
    public void onBackPressed() {
        if (!hasClickBack) {
            hasClickBack = true;
            ToastUtil.toast(R.string.tch_click_again_exit_app);
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
