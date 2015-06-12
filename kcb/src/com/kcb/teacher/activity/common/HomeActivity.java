package com.kcb.teacher.activity.common;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
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
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
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

    private SmoothProgressBar progressBar;

    private ButtonFlat accountButton;
    private TextView userNameTextView;
    private TextView tabtipTextView;
    private ButtonFlat refreshButton;

    private CheckInFragment mCheckInFragment;
    private TestFragment mTestFragment;
    private StuCentreFragment mStuCentreFragment;

    private FragmentManager mFragmentManager;

    private ButtonFlat checkInButton;
    private ButtonFlat testButton;
    private ButtonFlat stuCenterButton;

    private PopupWindow popupWindow;

    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_home);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar_refresh);

        accountButton = (ButtonFlat) findViewById(R.id.button_account);
        accountButton.setOnClickListener(this);

        userNameTextView = (TextView) findViewById(R.id.textview_username);
        userNameTextView.setText(KAccount.getAccountName());

        tabtipTextView = (TextView) findViewById(R.id.textview_tabtip);

        refreshButton = (ButtonFlat) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);

        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        checkInButton.setTextSize(16);

        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        testButton.setTextSize(16);

        stuCenterButton = (ButtonFlat) findViewById(R.id.button_stucenter);
        stuCenterButton.setOnClickListener(this);
        stuCenterButton.setTextSize(16);
    }

    @Override
    protected void initData() {
        mCurrentIndex = -1;
        mFragmentManager = getSupportFragmentManager();
        onClick(checkInButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_account:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindow();
                    popupWindow.showAsDropDown(v, 0, 0);
                }
                break;
            case R.id.button_refresh:
                mStuCentreFragment.refresh();
                break;
            case R.id.button_checkin:
                if (mCurrentIndex != INDEX_CHECKIN) {
                    showFragment(INDEX_CHECKIN);
                }
                break;
            case R.id.button_test:
                if (mCurrentIndex != INDEX_TEST) {
                    showFragment(INDEX_TEST);
                }
                break;
            case R.id.button_stucenter:
                if (mCurrentIndex != INDEX_STUCENTER) {
                    showFragment(INDEX_STUCENTER);
                }
                break;
            default:
                break;
        }
    }

    private void showFragment(int index) {
        mCurrentIndex = index;
        setTabTip(index);
//        setButtonTextColor(index);

        if (mCurrentIndex == INDEX_STUCENTER) {
            refreshButton.setVisibility(View.VISIBLE);
        } else {
            refreshButton.setVisibility(View.INVISIBLE);
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (null != mCheckInFragment) {
            transaction.hide(mCheckInFragment);
        }
        if (null != mTestFragment) {
            transaction.hide(mTestFragment);
        }
        if (null != mStuCentreFragment) {
            transaction.hide(mStuCentreFragment);
        }
        switch (index) {
            case INDEX_CHECKIN:
                if (null == mCheckInFragment) {
                    mCheckInFragment = new CheckInFragment();
                    transaction.add(R.id.fragment_content, mCheckInFragment);
                }
                transaction.show(mCheckInFragment);
                break;
            case INDEX_TEST:
                if (null == mTestFragment) {
                    mTestFragment = new TestFragment();
                    transaction.add(R.id.fragment_content, mTestFragment);
                }
                transaction.show(mTestFragment);
                break;
            case INDEX_STUCENTER:
                if (null == mStuCentreFragment) {
                    mStuCentreFragment = new StuCentreFragment();
                    transaction.add(R.id.fragment_content, mStuCentreFragment);
                    mStuCentreFragment.setProgressBar(progressBar);
                }
                transaction.show(mStuCentreFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void setTabTip(int index) {
        switch (index) {
            case INDEX_CHECKIN:
                tabtipTextView.setText(R.string.tch_class_checkin);
                break;
            case INDEX_TEST:
                tabtipTextView.setText(R.string.tch_class_test);
                break;
            case INDEX_STUCENTER:
                tabtipTextView.setText(R.string.tch_student_center);
                break;
            default:
                break;
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
    public void initPopupWindow() {
        View customView = View.inflate(HomeActivity.this, R.layout.tch_popupwindow_setting, null);
        popupWindow =
                new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_modifypassword:
                        popupWindow.dismiss();
                        Intent intent = new Intent(HomeActivity.this, ModifyPasswordActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.button_exit:
                        popupWindow.dismiss();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCheckInFragment = null;
        mTestFragment = null;
        mStuCentreFragment = null;
        mFragmentManager = null;
        popupWindow = null;
    }

    /**
     * start
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
