package com.kcb.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.library.view.ColorAnimationView;
import com.kcb.library.view.PaperButton;
import com.kcb.student.activity.HomeActivity;
import com.kcb.student.activity.LoginActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: StartActivity
 * @description: first activity of app;
 * @author: wanghang
 * @date: 2015-4-29 下午9:01:59
 */
public class StartActivity extends BaseActivity {

    public static final String INTENT_ACTION = "backFromLogin";

    private ColorAnimationView colorAnimationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!KAccount.hasAccount()) {
            setContentView(R.layout.comm_activity_start);
            initView();
        } else {
            Intent intent;
            if (KAccount.getAccountType() == KAccount.TYPE_STU) {
                intent = new Intent(this, HomeActivity.class);
            } else {
                intent = new Intent(this, com.kcb.teacher.activity.HomeActivity.class);
            }
            startActivity(intent);
        }
    }

    private int[] backgroundColors = new int[] {0xffff0000, 0xff00ff00, 0xff0000ff};

    @Override
    protected void initView() {
        colorAnimationView = (ColorAnimationView) findViewById(R.id.color_animation_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        StartViewPagerAdapter adapter = new StartViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        colorAnimationView.setmViewPager(viewPager, adapter.getCount(), backgroundColors);

        Intent intent = getIntent();
        if (null != intent && intent.getAction() == INTENT_ACTION) {
            viewPager.setCurrentItem(adapter.getCount() - 1);
        }
    }

    @Override
    protected void initData() {}

    private class StartViewPagerAdapter extends PagerAdapter {

        private int[] mBackgroundBitmapIds = new int[] {R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};

        @Override
        public int getCount() {
            return mBackgroundBitmapIds.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(StartActivity.this, R.layout.comm_view_start_pager, null);
            ImageView backgroundImageView =
                    (ImageView) view.findViewById(R.id.imageview_background);
            backgroundImageView.setImageResource(mBackgroundBitmapIds[position]);
            if (position == getCount() - 1) {
                PaperButton stuPaperButton = (PaperButton) view.findViewById(R.id.pagerbutton_stu);
                PaperButton tchPaperButton = (PaperButton) view.findViewById(R.id.pagerbutton_tch);
                stuPaperButton.setVisibility(View.VISIBLE);
                stuPaperButton.setOnClickListener(mOnClickListener);
                tchPaperButton.setVisibility(View.VISIBLE);
                tchPaperButton.setOnClickListener(mOnClickListener);
            }
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        private CustomOnClickListener mOnClickListener = new CustomOnClickListener(
                CustomOnClickListener.DELAY_PAPER_BUTTON) {

            @Override
            public void doClick(final View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.pagerbutton_stu:
                        intent = new Intent(StartActivity.this, LoginActivity.class);
                        finish();
                        break;
                    case R.id.pagerbutton_tch:
                        intent =
                                new Intent(StartActivity.this,
                                        com.kcb.teacher.activity.LoginActivity.class);
                        finish();
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        };
    }

    /**
     * exit app;
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
