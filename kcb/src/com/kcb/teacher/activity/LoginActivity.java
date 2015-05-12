package com.kcb.teacher.activity;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.activity.StartActivity;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description:
 * @author: ljx
 * @date: 2015年4月24日 下午8:36:47
 */
public class LoginActivity extends BaseActivity {

	private static final String TAG = LoginActivity.class.getName();

	private FloatingEditText idEditText;
	private FloatingEditText passwordEditText;
	private PaperButton loginButton;
	private SmoothProgressBar loginProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tch_activity_login);

		initView();
	}

	@Override
	protected void initView() {
		idEditText = (FloatingEditText) findViewById(R.id.edittext_id);
		passwordEditText = (FloatingEditText) findViewById(R.id.edittext_password);
		loginButton = (PaperButton) findViewById(R.id.button_login);
		loginButton.setOnClickListener(mClickListener);
		loginProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar_login);
	}

	@Override
	protected void initData() {
	}

	private DelayClickListener mClickListener = new DelayClickListener(
			DelayClickListener.DELAY_PAPER_BUTTON) {

		@Override
		public void doClick(View v) {
			final String id = idEditText.getText().toString().trim();
			final String password = passwordEditText.getText().toString();
			if (TextUtils.isEmpty(id)) {
				idEditText.requestFocus();
				AnimationUtil.shake(idEditText);
			} else if (TextUtils.isEmpty(password)) {
				passwordEditText.requestFocus();
				AnimationUtil.shake(passwordEditText);
			} else {
				loginProgressBar.setVisibility(View.VISIBLE);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO save teacher's name after login success
						KAccount account = new KAccount(KAccount.TYPE_TCH, id,
								"name");
						KAccount.saveAccount(account);
						Intent intent = new Intent(LoginActivity.this,
								HomeActivity.class);
						startActivity(intent);
						finish();
					}
				}, 1000);

				// TODO request server
				JsonObjectRequest request = new JsonObjectRequest(Method.POST,
						UrlUtil.getStuLoginUrl(id, password), "",
						new Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// loginProgressBar.hide(LoginActivity.this);
							}
						});
				RequestUtil.getInstance().addToRequestQueue(request, TAG);
			}
		}
	};

	@Override
	public void onBackPressed() {
		finish();
		StartActivity.restart(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RequestUtil.getInstance().cancelPendingRequests(TAG);
	}

	/**
	 * 
	 * @title: start
	 * @description: if delete account in HomeActivity, need show this Activity;
	 * @author: wanghang
	 * @date: 2015-5-10 上午11:13:50
	 * @param context
	 */
	public static void start(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
}
