package com.kcb.common.application;

import android.app.Application;

import com.kcb.student.database.KCBProfileDao;

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
    private KCBProfileDao DB1;

    @Override
    public void onCreate() {
        super.onCreate();
        DB1 = new KCBProfileDao(this, 1);
        instance = this;
    }


    public KCBProfileDao getDB() {
        return DB1;
    }

    public static KApplication getInstance() {
        return instance;
    }
}
