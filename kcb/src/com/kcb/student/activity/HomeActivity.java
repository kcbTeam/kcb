package com.kcb.student.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.activity.LoginActivity;
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
	private ButtonFlat settingButton;
	private ButtonFlat checkInButton;
	private ButtonFlat testButton;

	private Fragment[] mFragments;
	private FragmentManager mFragmentManager;

	private PopupWindow mPopupWindow;

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

		settingButton = (ButtonFlat) findViewById(R.id.button_setting);
		settingButton.setOnClickListener(this);

		checkInButton = (ButtonFlat) findViewById(R.id.button_checkin);
		checkInButton.setOnClickListener(this);
		testButton = (ButtonFlat) findViewById(R.id.button_test);
		testButton.setOnClickListener(this);

		mFragmentManager = getSupportFragmentManager();

		mFragments = new Fragment[2];
		mFragments[INDEX_CHECKIN] = mFragmentManager
				.findFragmentById(R.id.fragment_checkin);
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
		switch (v.getId()) {
		case R.id.button_setting:
			if (mPopupWindow != null && mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
				return;
			} else {
				initPopupWindow();
				mPopupWindow.showAsDropDown(v, 0, 0);
			}
			break;
		case R.id.button_checkin:
			switchFragment(INDEX_CHECKIN);
			break;
		case R.id.button_test:
			switchFragment(INDEX_TEST);
			break;
		case R.id.modify_button:
			ModifyPassword();
			break;
		case R.id.exit_button:
			onClick(settingButton);
			DialogUtil.showNormalDialog(this, R.string.quitload,
					R.string.destroy_tip, R.string.sure,
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							KAccount.deleteAccount();
							LoginActivity
									.start(HomeActivity.this);
							finish();
						}
					}, R.string.cancel, null);
			break;
		default:
			break;
		}
	}

	private void switchFragment(int index) {
		FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();
		fragmentTransaction.hide(mFragments[INDEX_CHECKIN]).hide(
				mFragments[INDEX_TEST]);
		fragmentTransaction.show(mFragments[index]).commit();
		setButtonTextColor(index);
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

	private boolean hasClickBack = false;

	@Override
	public void onBackPressed() {
		if (!hasClickBack) {
			hasClickBack = true;
			ToastUtil.toast(R.string.click_again_exit_app);
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

	public void initPopupWindow() {
		View customView = getLayoutInflater().inflate(
				R.layout.stu_menu_setting, null, false);
		mPopupWindow = new PopupWindow(customView, 350,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		customView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
					mPopupWindow = null;
				}
				return false;
			}
		});

		Button modifyButton = (Button) customView
				.findViewById(R.id.modify_button);
		Button exitButton = (Button) customView.findViewById(R.id.exit_button);
		modifyButton.setOnClickListener(this);
		exitButton.setOnClickListener(this);
	}

	private void ModifyPassword() {
		onClick(settingButton);
		final EditText mEditText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("为保障你的数据安全，修改密码前请填写原密码")
				.setView(mEditText)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String password = mEditText.getText().toString();
						if (TextUtils.isEmpty(password)) {
							ToastUtil.toast("密码为空请重新输入！");
						} else {
							Intent intent = new Intent(HomeActivity.this,
									ModifyPasswordActivity.class);
							startActivity(intent);
						}
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}

	/**
	 * 
	 * @title: start
	 * @description: start HomeActivity from StartActivity or LoginActivity
	 * @author: wanghang
	 * @date: 2015-5-10 上午11:27:53
	 * @param context
	 */
	public static void start(Context context) {
		Intent intent = new Intent(context, HomeActivity.class);
		context.startActivity(intent);
	}
}
