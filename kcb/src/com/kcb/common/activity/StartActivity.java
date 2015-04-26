package com.kcb.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.view.ColorAnimationView;
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

    private class StartViewPagerAdapter extends PagerAdapter implements OnClickListener {

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
                Button stuButton = (Button) view.findViewById(R.id.button_stu);
                Button tchButton = (Button) view.findViewById(R.id.button_tch);
                stuButton.setVisibility(View.VISIBLE);
                stuButton.setOnClickListener(this);
                tchButton.setVisibility(View.VISIBLE);
                tchButton.setOnClickListener(this);
            }
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_stu:
                    gotoStuModule();
                    break;
                case R.id.button_tch:
                    gotoTchModule();
                    break;
                default:
                    break;
            }
        }

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
