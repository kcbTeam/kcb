package com.kcb.common.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
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

    /**
     * 
     * @title: topToCenter
     * @description:
     * @author: ZQJ
     * @date: 2015年5月23日 下午8:01:02
     * @param view
     */
    public static void topToCenter(View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -400, 0);
        animation.setDuration(350);
        view.startAnimation(animation);
    }

    /**
     * 
     * @title: rightToCenter
     * @description:
     * @author: ZQJ
     * @date: 2015年5月23日 下午8:14:51
     * @param view
     */
    public static void rightToCenter(View view) {
        TranslateAnimation animation = new TranslateAnimation(1000, 0, 0, 0);
        animation.setDuration(1000);
        view.startAnimation(animation);
    }

    public static void centerToLeft(final View view) {
        TranslateAnimation animation = new TranslateAnimation(0, -1000, 0, 0);
        animation.setDuration(1000);
        // animation.setFillAfter(true); not work very well , use listener instead
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        view.startAnimation(animation);
    }

    public static void fadeIn(View view) {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setFillAfter(true);
        animation.setStartOffset(500);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        view.startAnimation(animation);
    }
}
