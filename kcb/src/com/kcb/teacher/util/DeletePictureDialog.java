package com.kcb.teacher.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
 * @className: DeletePictureDialog
 * @description:
 * @author: ZQJ
 * @date: 2015年5月20日 下午4:31:28
 */
public class DeletePictureDialog extends android.app.Dialog {

    private View backgroundView;
    private View contentView;

    private TextView titleTextView;

    private ButtonFlat reBackground;
    private ButtonFlat deleteBackground;
    private ButtonFlat cancelButton;

    private Context mContext;

    public DeletePictureDialog(Context context) {
        super(context, android.R.style.Theme_Translucent);

        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_dialog_deletepicture);

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
        reBackground = (ButtonFlat) findViewById(R.id.button_rebackground);
        deleteBackground = (ButtonFlat) findViewById(R.id.button_deletebackground);
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
                        DeletePictureDialog.super.dismiss();
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

    /**
     * step 3
     */
    public void setReBackgroundButton(final android.view.View.OnClickListener listener) {
        reBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.onClick(v);;
                }
            }
        });
    }

    public void setDeleteBackgroundButton(final android.view.View.OnClickListener listener) {
        deleteBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.onClick(v);;
                }
            }
        });
    }

    public void setCancelButton(final View.OnClickListener listener) {
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
