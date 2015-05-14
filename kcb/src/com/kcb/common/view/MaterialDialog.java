package com.kcb.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: MaterialDialog
 * @description: use new -> show -> step1,2,3,4
 * @author: wanghang
 * @date: 2015-5-14 下午1:59:21
 */
public class MaterialDialog extends android.app.Dialog {

    // a dialog includes black background and content
    private View backgroundView;
    private View contentView;

    private TextView titleTextView;
    private TextView messageTextView;
    private ButtonFlat sureButton;
    private ButtonFlat cancelButton;

    private Context mContext;

    public MaterialDialog(Context context) {
        super(context, android.R.style.Theme_Translucent);

        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_material_dialog);

        backgroundView = (RelativeLayout) findViewById(R.id.dialog_rootView);
        backgroundView.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() < contentView.getLeft() || event.getX() > contentView.getRight()
                        || event.getY() > contentView.getBottom()
                        || event.getY() < contentView.getTop()) {
                    dismiss();
                }
                return false;
            }
        });
        contentView = (RelativeLayout) findViewById(R.id.contentDialog);

        titleTextView = (TextView) findViewById(R.id.title);
        messageTextView = (TextView) findViewById(R.id.message);
        sureButton = (ButtonFlat) findViewById(R.id.button_accept);
        cancelButton = (ButtonFlat) findViewById(R.id.button_cancel);
    }

    @Override
    public void show() {
        super.show();
        contentView.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.material_dialog_main_show));
        backgroundView.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.material_dialog_root_show));
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.material_dialog_main_hide);
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                contentView.post(new Runnable() {
                    @Override
                    public void run() {
                        MaterialDialog.super.dismiss();
                    }
                });
            }
        });
        contentView.startAnimation(anim);
        Animation backAnim =
                AnimationUtils.loadAnimation(mContext, R.anim.material_dialog_root_hide);
        backgroundView.startAnimation(backAnim);
    }

    /**
     * Set title, message, button text and listener;
     */
    /**
     * step 1
     */
    public void setTitle(int resid) {
        titleTextView.setText(resid);
    }

    public void setTitle(CharSequence text) {
        titleTextView.setText(text);
    }

    /**
     * step 2
     */
    public void setMessage(int resid) {
        messageTextView.setText(resid);
    }

    public void setMessage(CharSequence text) {
        messageTextView.setText(text);
    }

    /**
     * step 3
     */
    public void setSureButton(@NonNull int resid, final View.OnClickListener listener) {
        if (resid < 0) {
            return;
        }
        setSureButton(mContext.getResources().getString(resid), listener);
    }

    public void setSureButton(@NonNull CharSequence text, final View.OnClickListener listener) {
        sureButton.setText(text);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.onClick(v);
                }
            }
        });
    }

    /**
     * step 4
     */
    public void setCancelButton(int resid, View.OnClickListener listener) {
        if (resid < 0) {
            return;
        }
        setCancelButton(mContext.getResources().getString(resid), listener);
    }

    public void setCancelButton(CharSequence text, final View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        cancelButton.setVisibility(View.VISIBLE);
        cancelButton.setText(text);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.onClick(v);
                }
            }
        });
    }
}
