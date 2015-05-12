package com.kcb.teacher.util;

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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestDialog
 * @description: 
 * @author: ZQJ
 * @date: 2015年5月12日 下午3:07:37
 */
public class EditTestDialog extends android.app.Dialog {

    private View backgroundView;
    private View contentView;

    private TextView titleTextView;

    private EditText contentEditText;

    private ButtonFlat sureButton;
    private ButtonFlat cancelButton;

    private Context mContext;
    private String mEditString;


    public EditTestDialog(Context context, String editString) {
        super(context, android.R.style.Theme_Translucent);

        mContext = context;
        mEditString = editString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_fragment_dialog_edittest);

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
        contentEditText = (EditText) findViewById(R.id.edittext_dialog);
        contentEditText.setText(mEditString);
        contentEditText.setSelection(mEditString.length());
        sureButton = (ButtonFlat) findViewById(R.id.button_accept);
        cancelButton = (ButtonFlat) findViewById(R.id.button_cancel);

    }

    @Override
    public void show() {
        super.show();
        contentView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_main_show));
        backgroundView.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.dialog_root_show));
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_main_hide);
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
                        EditTestDialog.super.dismiss();
                    }
                });
            }
        });
        contentView.startAnimation(anim);
        Animation backAnim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_root_hide);
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
    public void setSureButton(@NonNull int resid, final DialogBackListener listener) {
        setSureButton(mContext.getResources().getString(resid), listener);
    }

    public void setSureButton(@NonNull CharSequence text, final DialogBackListener listener) {
        sureButton.setText(text);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.refreshActivity(contentEditText.getText().toString());
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

    public interface DialogBackListener {
        public void refreshActivity(String text);

    }
}
