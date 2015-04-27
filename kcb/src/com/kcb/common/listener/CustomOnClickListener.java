package com.kcb.common.listener;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class CustomOnClickListener implements OnClickListener {

    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 800) {
            return;
        }
        lastClickTime = currentTime;
        doClick(v);
    }

    public abstract void doClick(View v);
}
