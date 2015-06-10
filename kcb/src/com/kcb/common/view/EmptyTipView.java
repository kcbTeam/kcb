package com.kcb.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcbTeam.R;

public class EmptyTipView extends LinearLayout {

    public EmptyTipView(Context context) {
        super(context);
        init(context);
    }

    public EmptyTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyTipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private Context mContext;
    private ImageView emptyTipImageView;
    private TextView emptyTipTextView;

    private void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_empty_tip, this);
        initView();
    }

    private void initView() {
        emptyTipImageView = (ImageView) findViewById(R.id.imageview_empty_icon);
        emptyTipTextView = (TextView) findViewById(R.id.textview_empty_tip);
    }

    public void setEmptyIcon(int resId) {
        emptyTipImageView.setImageResource(resId);
    }

    public void setEmptyText(int resId) {
        emptyTipTextView.setText(mContext.getResources().getString(resId));
    }
}
