package com.kcb.common.util;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.kcb.library.view.statusbar.SystemBarTintManager;
import com.kcbTeam.R;

public class StatusBarUtil {

    public static void setStuStatusBarColor(Activity activity) {
        setSystemBar(activity, R.color.stu_primary_dark);
    }

    public static void setTchStatusBarColor(Activity activity) {
        setSystemBar(activity, R.color.tch_primary_dark);
    }

    private static void setSystemBar(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            LayoutParams params = window.getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(params);
            SystemBarTintManager manager = new SystemBarTintManager(activity);
            manager.setStatusBarTintEnabled(true);
            manager.setTintColor(activity.getResources().getColor(colorId));
        }
    }
}
