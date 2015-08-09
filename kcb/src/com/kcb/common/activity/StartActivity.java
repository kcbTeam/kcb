package com.kcb.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.util.AccountUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.view.common.PasswordEditText;
import com.kcb.library.view.ColorAnimationView;
import com.kcb.library.view.PaperButton;
import com.kcb.student.activity.common.HomeActivity;
import com.kcb.student.activity.common.LoginActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: StartActivity
 * @description: first activity of app, show three description page
 * @author: wanghang
 * @date: 2015-4-29 下午9:01:59
 */
public class StartActivity extends BaseActivity {

    private static final String TAG = StartActivity.class.getName();

    private ColorAnimationView colorAnimationView;
    private ViewPager viewPager;

    private StartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.i(TAG, "on create");
        // 如果已经登录过了，直接进入这界面，否则显示此界面
        if (!AccountUtil.hasAccount()) {
            LogUtil.i(TAG, "start has no account");
            setContentView(R.layout.comm_activity_start);
            initView();
        } else {
            LogUtil.i(TAG, "start has account");
            if (AccountUtil.getAccountType() == AccountUtil.TYPE_STU) {
                LogUtil.i(TAG, "start student");
                HomeActivity.start(this);
            } else {
                LogUtil.i(TAG, "start teacher");
                com.kcb.teacher.activity.common.HomeActivity.start(this);
            }
            finish();
        }
    }

    @Override
    protected void initView() {
        colorAnimationView = (ColorAnimationView) findViewById(R.id.color_animation_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        mAdapter = new StartAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mAdapter.getCount());
        colorAnimationView.setmViewPager(viewPager, mAdapter.getCount(), new int[] {0xffffffff,
                0xffffffff, 0xffffffff});

        // user click back in LoginActivity, restart this activity and show third page
        Intent intent = getIntent();
        if (null != intent && intent.getAction() == ACTION_RESTART) {
            viewPager.setCurrentItem(mAdapter.getCount() - 1);
        }
    }

    @Override
    protected void initData() {}

    private class StartAdapter extends PagerAdapter {
        private int[] mBackgroundBitmapIds = new int[] {R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};

        @Override
        public int getCount() {
            return mBackgroundBitmapIds.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(StartActivity.this, R.layout.comm_pageritem_start, null);
            ImageView backgroundImageView =
                    (ImageView) view.findViewById(R.id.imageview_background);
            backgroundImageView.setImageResource(mBackgroundBitmapIds[position]);
            // two button only show in third page
            if (position == getCount() - 1) {
                PaperButton stuButton = (PaperButton) view.findViewById(R.id.button_stu);
                PaperButton tchButton = (PaperButton) view.findViewById(R.id.button_tch);
                stuButton.setVisibility(View.VISIBLE);
                stuButton.setOnClickListener(mClickListener);
                tchButton.setVisibility(View.VISIBLE);
                tchButton.setOnClickListener(mClickListener);
            }
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // goto stu/tch LoginActivity according user's selection
        private DelayClickListener mClickListener = new DelayClickListener(
                DelayClickListener.DELAY_PAPER_BUTTON) {

            @Override
            public void doClick(final View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.button_stu:
                        PasswordEditText.setFloatingColor(getResources().getColor(
                                R.color.stu_primary));
                        intent = new Intent(StartActivity.this, LoginActivity.class);
                        break;
                    case R.id.button_tch:
                        PasswordEditText.setFloatingColor(getResources().getColor(R.color.blue));
                        intent =
                                new Intent(StartActivity.this,
                                        com.kcb.teacher.activity.common.LoginActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                finish(); // finish this activity, if user click back in LoginActivity, restart this
                          // activity
            }
        };

        public void release() {
            mBackgroundBitmapIds = null;
            mClickListener = null;
        }
    }

    /**
     * exit app;
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        colorAnimationView = null;
        viewPager = null;
        if (null != mAdapter) { // 如果已经登录过，直接进入这界面，此时mAdapter根本没有初始化，所以需要判空
            mAdapter.release();
            mAdapter = null;
        }
    }

    /**
     * 
     * @title: restart
     * @description: finish this activity when goto LoginActivity, but if user click back in
     *               LoginActivity, we need restart this activity and show third page;
     * @author: wanghang
     * @date: 2015-5-4 下午8:39:21
     * @param context
     */
    public static final String ACTION_RESTART = "action_restart";

    public static void restart(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        intent.setAction(StartActivity.ACTION_RESTART);
        context.startActivity(intent);
    }
}
