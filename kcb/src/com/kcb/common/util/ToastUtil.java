package com.kcb.common.util;

import android.app.Activity;
import android.widget.Toast;

import com.kcb.common.application.KApplication;

/**
 * @className: ToastUtil
 * @description: include easy use of toast in UI thread & child Thread (not UI thread);
 * @author: wanghang
 * @date: 2015-3-21 下午9:38:15
 */
public class ToastUtil {

    /**
     * 
     * @title: toast
     * @description: toast in UI thread;
     * @author:
     * @date: 2015-4-20 上午10:36:58
     * @param resId
     */
    public static void toast(int resId) {
        Toast.makeText(KApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void toast(CharSequence text) {
        Toast.makeText(KApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 
     * @title: toastInThread
     * @description: toast in child thread (not UI thread);
     * @author: hang.wang
     * @date: 2015-4-20 上午10:34:06
     * @param activity
     * @param resId
     */
    public static void toastInThread(Activity activity, final int resId) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(KApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void toastInThread(Activity activity, final CharSequence text) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(KApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
