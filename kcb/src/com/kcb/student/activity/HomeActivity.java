package com.kcb.student.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
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

	private ButtonFlat exitButton;
	private ButtonFlat checkInButton;
	private ButtonFlat testButton;

	private Fragment[] mFragments;
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_activity_home);

		initView();
	}

	@Override
	protected void initView() {
		exitButton = (ButtonFlat) findViewById(R.id.button_exit);
		exitButton.setOnClickListener(this);
		exitButton.setRippleSpeed(6f);
		checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
		checkInButton.setOnClickListener(this);
		checkInButton.setRippleSpeed(18f);
		testButton = (ButtonFlat) findViewById(R.id.button_test);
		testButton.setOnClickListener(this);
		testButton.setRippleSpeed(18f);

		mFragmentManager = getSupportFragmentManager();

		mFragments = new Fragment[2];
		mFragments[INDEX_CHECKIN] = mFragmentManager
				.findFragmentById(R.id.fragment_sign);
		mFragments[INDEX_TEST] = mFragmentManager
				.findFragmentById(R.id.fragment_test);

		showDefaultFragment();
	}

	@Override
	protected void initData() {
	}

	private void showDefaultFragment() {
		onClick(checkInButton);
	}

	@Override
	public void onClick(View v) {
		if (v == exitButton) {
			DialogUtil.showDialog(this, "退出", "退出程序还是注销账号？注销账号后，下次进入程序需要重新登录。",
					"注销", new OnClickListener() {

						@Override
						public void onClick(View v) {
							ToastUtil.toast("click sure");
						}
					}, "退出", null);
		} else {
			FragmentTransaction fragmentTransaction = mFragmentManager
					.beginTransaction();
			fragmentTransaction.hide(mFragments[INDEX_CHECKIN]).hide(
					mFragments[INDEX_TEST]);
			if (v == checkInButton) {
				setButtonTextColor(INDEX_CHECKIN);
				fragmentTransaction.show(mFragments[INDEX_CHECKIN]).commit();
			} else if (v == testButton) {
				setButtonTextColor(INDEX_TEST);
				fragmentTransaction.show(mFragments[INDEX_TEST]).commit();
			}
		}
	}

	private void setButtonTextColor(int index) {
		Resources resources = getResources();
		checkInButton.setTextColor(resources.getColor(R.color.gray));
		testButton.setTextColor(resources.getColor(R.color.gray));
		if (index == INDEX_CHECKIN) {
			checkInButton.setTextColor(resources.getColor(R.color.blue));
		} else {
			testButton.setTextColor(resources.getColor(R.color.blue));
		}
	}
}
