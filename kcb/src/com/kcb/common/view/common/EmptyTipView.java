package com.kcb.common.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseLinearLayout;
import com.kcbTeam.R;

public class EmptyTipView extends BaseLinearLayout {

    public EmptyTipView(Context context) {
        super(context);
    }

    public EmptyTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyTipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private Context mContext;
    private ImageView emptyTipImageView;
    private TextView emptyTipTextView;

    @Override
    public void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_empty_tip, this);
        initView();
    }

    @Override
    public void initView() {
        emptyTipImageView = (ImageView) findViewById(R.id.imageview_empty_icon);
        emptyTipTextView = (TextView) findViewById(R.id.textview_empty_tip);
    }

    @Override
    public void release() {}

    @Override
    public void onClick(View v) {}

    public void setEmptyIcon(int resId) {
        emptyTipImageView.setImageResource(resId);
    }

    public void setEmptyText(int resId) {
        emptyTipTextView.setText(mContext.getResources().getString(resId));
    }
}
