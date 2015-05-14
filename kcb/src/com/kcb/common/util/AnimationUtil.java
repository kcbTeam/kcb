package com.kcb.common.util;

import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 
 * @className: AnimationUtil
 * @description: define reused animation
 * @author: wanghang
 * @date: 2015-5-14 下午1:39:07
 */
public class AnimationUtil {

    /**
     * 
     * @title: shake
     * @description: used in LoginActivity, shake edittext when it is empty
     * @author: wanghang
     * @date: 2015-5-6 下午1:39:31
     * @param view
     */
    public static void shake(View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(3));
        animation.setDuration(350);
        view.startAnimation(animation);
    }
}
