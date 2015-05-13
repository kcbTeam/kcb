package com.kcb.common.util;

import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {

    public static void shake(View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(3));
        animation.setDuration(350);
        view.startAnimation(animation);
    }
}
