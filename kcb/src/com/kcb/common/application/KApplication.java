package com.kcb.common.application;

import android.app.Application;

import com.kcb.common.util.ImageLoaderUtil;

/**
 * 
 * @className: KApplication
 * @description: this app's application;
 * @author: wanghang
 * @date: 2015-4-20 上午9:54:21
 */
public class KApplication extends Application {

    // don't use this instance casually, we use for toast context, but can't use for dialog context
    private static KApplication instance;

    public static KApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ImageLoaderUtil.init();
    }
}
