package com.kcb.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * 
 * @className: BaseRelativeLayout
 * @description: 所有自定义的view，如果根布局是RelativeLayout，需要继承自此类；
 * @author: wanghang
 * @date: 2015-7-11 下午2:53:34
 */
public abstract class BaseRelativeLayout extends RelativeLayout
        implements
            OnClickListener,
            CustomViewListener {

    public BaseRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected Context mContext;
}
