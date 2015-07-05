package com.kcb.student.activity.common;

import static android.view.Gravity.START;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.DrawerArrowDrawable;
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

    // 侧边栏
    private DrawerLayout drawer;
    private ImageView indicatorImageView;
    private DrawerArrowDrawable drawerArrowDrawable;
    private float offset;
    private boolean flipped;

    private ButtonFlat accountButton;
    private TextView userNameTextView;
    private TextView titleTextView;

    private ButtonFlat checkInButton;
    private ImageView checkInImageView;
    private TextView checkInTextView;

    private ButtonFlat testButton;
    private ImageView testImageView;
    private TextView testTextView;

    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_home);
        StatusBarUtil.setStuStatusBarColor(this);

        initView();
    }

    @Override
    protected void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        indicatorImageView = (ImageView) findViewById(R.id.imageview_indicator);
        indicatorImageView.setOnClickListener(this);
        Resources resources = getResources();

        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(Color.WHITE);
        indicatorImageView.setImageDrawable(drawerArrowDrawable);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;
                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });

        accountButton = (ButtonFlat) findViewById(R.id.button_account);
        accountButton.setOnClickListener(this);
        accountButton.setRippleColor(getResources().getColor(R.color.stu_primary_dark));

        userNameTextView = (TextView) findViewById(R.id.textview_username);
        userNameTextView.setText(KAccount.getAccountName());

        titleTextView = (TextView) findViewById(R.id.textview_title);

        checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
        checkInButton.setOnClickListener(this);
        checkInButton.setRippleColor(getResources().getColor(R.color.black_400));
        checkInImageView = (ImageView) findViewById(R.id.imageview_checkin);
        checkInTextView = (TextView) findViewById(R.id.textview_tab_checkin);

        testButton = (ButtonFlat) findViewById(R.id.button_test);
        testButton.setOnClickListener(this);
        testButton.setRippleColor(getResources().getColor(R.color.black_400));
        testImageView = (ImageView) findViewById(R.id.imageview_test);
        testTextView = (TextView) findViewById(R.id.textview_tab_test);

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
            case R.id.imageview_indicator:
                if (drawer.isDrawerVisible(START)) {
                    drawer.closeDrawer(START);
                } else {
                    drawer.openDrawer(START);
                }
                break;
            case R.id.button_account:
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    if (null == popupWindow) {
                        initPopupWindow();
                    }
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
        setTabTip(index);
        setTab(index);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mFragments[INDEX_CHECKIN]).hide(mFragments[INDEX_TEST]);
        fragmentTransaction.show(mFragments[index]).commit();
    }

    private void setTabTip(int index) {
        switch (index) {
            case INDEX_CHECKIN:
                titleTextView.setText(R.string.stu_class_checkin);
                break;
            case INDEX_TEST:
                titleTextView.setText(R.string.stu_class_test);
                break;
            default:
                break;
        }
    }

    private void setTab(int index) {
        Resources res = getResources();
        checkInImageView.setImageResource(R.drawable.ic_assignment_turned_in_white_36dp);
        checkInTextView.setTextColor(res.getColor(R.color.white));
        testImageView.setImageResource(R.drawable.ic_event_note_white_36dp);
        testTextView.setTextColor(res.getColor(R.color.white));
        switch (index) {
            case INDEX_CHECKIN:
                checkInImageView.setImageResource(R.drawable.ic_assignment_turned_in_grey600_36dp);
                checkInTextView.setTextColor(res.getColor(R.color.black_700));
                break;
            case INDEX_TEST:
                testImageView.setImageResource(R.drawable.ic_event_note_grey600_36dp);
                testTextView.setTextColor(res.getColor(R.color.black_700));
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void initPopupWindow() {
        View customView = View.inflate(HomeActivity.this, R.layout.stu_popupwindow_account, null);
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
        modifyButton.setRippleColor(getResources().getColor(R.color.black_400));

        ButtonFlat exitButton = (ButtonFlat) customView.findViewById(R.id.button_exit);
        exitButton.setOnClickListener(clickListener);
        exitButton.setRippleColor(getResources().getColor(R.color.black_400));
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
