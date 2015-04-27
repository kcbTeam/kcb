package com.kcb.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.library.view.ColorAnimationView;
import com.kcb.library.view.PaperButton;
import com.kcb.student.activity.LoginActivity;
import com.kcbTeam.R;

public class StartActivity extends BaseActivity {

    private ColorAnimationView colorAnimationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_activity_start);

        initView();
    }

    @Override
    protected void initView() {
        colorAnimationView = (ColorAnimationView) findViewById(R.id.color_animation_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        StartViewPagerAdapter adapter = new StartViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        colorAnimationView.setmViewPager(viewPager, adapter.getCount(), new int[] {0xff8080FF,
                0xff80ff80, 0xffffffff});
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

        private CustomOnClickListener mOnClickListener = new CustomOnClickListener() {

            @Override
            public void doClick(final View v) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        switch (v.getId()) {
                            case R.id.pagerbutton_stu:
                                gotoStuModule();
                                break;
                            case R.id.pagerbutton_tch:
                                gotoTchModule();
                                break;
                            default:
                                break;
                        }
                    }
                }, 400);
            }
        };

        private void gotoStuModule() {
            // TODO detect stu login, sharedPreference;
            // if not login, goto Login
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            // else if login, goto Home
            // Intent intent = new Intent(StartActivity.this, HomeActivity.class);
            // startActivity(intent);
        }

        private void gotoTchModule() {
            // TODO detect tch login, sharedPreference;
            Intent intent =
                    new Intent(StartActivity.this, com.kcb.teacher.activity.LoginActivity.class);
            startActivity(intent);
            // else if login, goto Home
            // Intent intent = new Intent(StartActivity.this,
            // com.kcb.teacher.activity.HomeActivity.class);
            // startActivity(intent);
        }
    }
}
