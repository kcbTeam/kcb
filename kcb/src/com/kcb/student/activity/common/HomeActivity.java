package com.kcb.student.activity.common;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.kcb.student.database.checkin.CheckInDao;
import com.kcb.student.database.test.TestDao;
import com.kcb.student.model.account.KAccount;
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

    private TextView userNameTextView;
    private ButtonFlat moreButton;

    private ButtonFlat checkInButton;
    private ButtonFlat testButton;

    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_home);

        initView();
    }

    @Override
    protected void initView() {
        userNameTextView = (TextView) findViewById(R.id.textview_username);
        userNameTextView.setText(KAccount.getAccountName());

        moreButton = (ButtonFlat) findViewById(R.id.button_more);
        moreButton.setOnClickListener(this);

        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        checkInButton.setTextSize(16);
        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        testButton.setTextSize(16);

        mFragmentManager = getSupportFragmentManager();

        mFragments = new Fragment[2];
        mFragments[INDEX_CHECKIN] = mFragmentManager.findFragmentById(R.id.fragment_checkin);
        mFragments[INDEX_TEST] = mFragmentManager.findFragmentById(R.id.fragment_test);

        switchFragment(INDEX_CHECKIN);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_more:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindow();
                    popupWindow.showAsDropDown(v, 0, 0);
                }
                break;
            case R.id.button_checkin:
                switchFragment(INDEX_CHECKIN);
                break;
            case R.id.button_test:
                switchFragment(INDEX_TEST);
                break;
            default:
                break;
        }
    }

    private void switchFragment(int index) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragments[INDEX_CHECKIN]).hide(mFragments[INDEX_TEST]);
        fragmentTransaction.show(mFragments[index]).commit();
        setButtonTextColor(index);
    }

    private void setButtonTextColor(int index) {
        Resources resources = getResources();
        checkInButton.setTextColor(resources.getColor(R.color.black_500));
        testButton.setTextColor(resources.getColor(R.color.black_500));
        if (index == INDEX_CHECKIN) {
            checkInButton.setTextColor(resources.getColor(R.color.blue));
        } else {
            testButton.setTextColor(resources.getColor(R.color.blue));
        }
    }

    @SuppressWarnings("deprecation")
    public void initPopupWindow() {
        View customView = View.inflate(HomeActivity.this, R.layout.stu_popupwindow_setting, null);
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
        DialogUtil.showNormalDialog(HomeActivity.this, R.string.stu_exit_account,
                R.string.stu_exit_account_tip, R.string.stu_comm_sure, new View.OnClickListener() {

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

                        // goto login activity
                        LoginActivity.start(HomeActivity.this);
                        finish();
                    }
                }, R.string.stu_comm_cancel, null);
    }

    private boolean mHasClickBack = false;

    @Override
    public void onBackPressed() {
        if (!mHasClickBack) {
            mHasClickBack = true;
            ToastUtil.toast(R.string.stu_click_again_exit_app);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mHasClickBack = false;
                }
            }, 2000);
        } else {
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments = null;
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
