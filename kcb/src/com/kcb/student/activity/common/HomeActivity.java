package com.kcb.student.activity.common;

import static android.view.Gravity.START;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.DrawerArrowDrawable;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * @className: HomePageActivity
 * @description: Sign in and Test
 * @author: Ding
 * @date: 2015年4月23日 上午11:03:21
 */
public class HomeActivity extends BaseFragmentActivity {

    private static final String TAG = HomeActivity.class.getName();
    
    private final int INDEX_CHECKIN = 0;
    private final int INDEX_TEST = 1;

    private DrawerLayout drawerLayout; // 包裹了侧边栏和主体内容

    private ImageView menuImageView; // 左上角的菜单图片
    private DrawerArrowDrawable drawerArrowDrawable; // 用于绘制菜单图片
    private float offset;
    private boolean flipped;

    // 标题
    private TextView titleTextView;

    private ButtonFlat checkInButton;
    private ImageView checkInImageView;
    private TextView checkInTextView;

    private ButtonFlat testButton;
    private ImageView testImageView;
    private TextView testTextView;

    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.stu_activity_home);
        StatusBarUtil.setStuStatusBarColor(this);

        initView();
    }

    @Override
    protected void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        drawerArrowDrawable = new DrawerArrowDrawable(getResources());
        drawerArrowDrawable.setStrokeColor(Color.WHITE);

        menuImageView = (ImageView) findViewById(R.id.imageview_menu);
        menuImageView.setOnClickListener(this);
        menuImageView.setImageDrawable(drawerArrowDrawable);

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
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

        // 主体内容
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
            case R.id.imageview_menu:
                if (drawerLayout.isDrawerVisible(START)) {
                    drawerLayout.closeDrawer(START);
                } else {
                    drawerLayout.openDrawer(START);
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

    private boolean mHasClickBack = false;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                exitApp();
                break;
            case KeyEvent.KEYCODE_MENU:
                onClick(menuImageView);
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
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
    }

    /**
     * start
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
