package com.kcb.common.application;

import android.app.Application;

/**
 * 
 * @className: KApplication
 * @description: this app's application;
 * @author: hang.wang
 * @date: 2015-4-20 上午9:54:21
 */
public class KApplication extends Application {

	private static KApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
	}

	public static KApplication getInstance() {
		return instance;
	}
}
