package com.kcb.common.listener;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class CustomOnClickListener implements OnClickListener {

    public static final long DELAY_PAPER_BUTTON = 400;

    private long mLastClickTime = 0;
    private long mDelayTime = 0; // delay for show button click animation;

    public CustomOnClickListener() {}

    public CustomOnClickListener(long delayTime) {
        mDelayTime = delayTime;
    }

    @Override
    public void onClick(final View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime < 800) {
            return;
        }
        mLastClickTime = currentTime;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doClick(v);
            }
        }, mDelayTime);
    }

    public abstract void doClick(View v);
}
