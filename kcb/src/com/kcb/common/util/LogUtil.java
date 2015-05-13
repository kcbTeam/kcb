package com.kcb.common.util;

import android.util.Log;

import com.kcb.common.application.GlobalConstant;

/**
 * 
 * @className: LogUtil
 * @description: log util, only log when enable log;
 * @author: hang.wang
 * @date: 2015-4-20 上午11:10:07
 */
public class LogUtil {

    public static void i(String tag, String msg) {
        if (GlobalConstant.ENABLE_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (GlobalConstant.ENABLE_LOG) {
            Log.w(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (GlobalConstant.ENABLE_LOG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (GlobalConstant.ENABLE_LOG) {
            Log.e(tag, msg);
        }
    }
}
