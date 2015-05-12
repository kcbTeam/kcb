package com.kcb.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
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

	private ColorAnimationView colorAnimationView;
	private ViewPager viewPager;
	private StartViewPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!KAccount.hasAccount()) {
			setContentView(R.layout.comm_activity_start);
			initView();
		} else {
			if (KAccount.getAccountType() == KAccount.TYPE_STU) {
				HomeActivity.start(this);
			} else {
				com.kcb.teacher.activity.HomeActivity.start(this);
			}
			finish();
		}
	}

	@Override
	protected void initView() {
		colorAnimationView = (ColorAnimationView) findViewById(R.id.color_animation_view);
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		mAdapter = new StartViewPagerAdapter();
		viewPager.setAdapter(mAdapter);
		viewPager.setOffscreenPageLimit(mAdapter.getCount());
		colorAnimationView.setmViewPager(viewPager, mAdapter.getCount(),
				new int[] { 0xffffffff, 0xffffffff, 0xffffffff });

		Intent intent = getIntent();
		if (null != intent && intent.getAction() == INTENT_ACTION_RESTART) {
			viewPager.setCurrentItem(mAdapter.getCount() - 1);
		}
	}

	@Override
	protected void initData() {
	}

	private class StartViewPagerAdapter extends PagerAdapter {
		private int[] mBackgroundBitmapIds = new int[] {
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher };

		@Override
		public int getCount() {
			return mBackgroundBitmapIds.length;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(StartActivity.this,
					R.layout.comm_view_start_pager, null);
			ImageView backgroundImageView = (ImageView) view
					.findViewById(R.id.imageview_background);
			backgroundImageView
					.setImageResource(mBackgroundBitmapIds[position]);
			if (position == getCount() - 1) {
				PaperButton stuPaperButton = (PaperButton) view
						.findViewById(R.id.pagerbutton_stu);
				PaperButton tchPaperButton = (PaperButton) view
						.findViewById(R.id.pagerbutton_tch);
				stuPaperButton.setVisibility(View.VISIBLE);
				stuPaperButton.setOnClickListener(mClickListener);
				tchPaperButton.setVisibility(View.VISIBLE);
				tchPaperButton.setOnClickListener(mClickListener);
			}
			container.addView(view);
			return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		private DelayClickListener mClickListener = new DelayClickListener(
				DelayClickListener.DELAY_PAPER_BUTTON) {

			@Override
			public void doClick(final View v) {
				Intent intent = null;
				switch (v.getId()) {
				case R.id.pagerbutton_stu:
					intent = new Intent(StartActivity.this, LoginActivity.class);
					break;
				case R.id.pagerbutton_tch:
					intent = new Intent(StartActivity.this,
							com.kcb.teacher.activity.LoginActivity.class);
					break;
				default:
					break;
				}
				startActivity(intent);
				finish();
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

	/**
	 * 
	 * @title: restart
	 * @description: finish this activity when goto LoginActivity, but if user
	 *               click back, we need restart this activity and show third
	 *               page;
	 * @author: wanghang
	 * @date: 2015-5-4 下午8:39:21
	 * @param context
	 */
	public static final String INTENT_ACTION_RESTART = "restartFromLogin";

	public static void restart(Context context) {
		Intent intent = new Intent(context, StartActivity.class);
		intent.setAction(StartActivity.INTENT_ACTION_RESTART);
		context.startActivity(intent);
	}
}
