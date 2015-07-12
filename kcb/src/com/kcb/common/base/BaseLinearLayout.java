package com.kcb.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public abstract class BaseLinearLayout extends LinearLayout
        implements
            OnClickListener,
            CustomViewListener {

    public BaseLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected Context mContext;
}
