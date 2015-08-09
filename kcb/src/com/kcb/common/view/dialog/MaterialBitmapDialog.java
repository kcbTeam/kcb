package com.kcb.common.view.dialog;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kcb.common.util.DialogUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

/**
 * 
 * @className: MaterialBitmapDialog
 * @description: 显示图片的对话框，在编辑题目页面，可以删除、裁剪图片
 * @author: wanghang
 * @date: 2015-5-14 下午1:59:21
 */
public class MaterialBitmapDialog extends android.app.Dialog implements OnClickListener {

    public interface OnActionListener {
        void onDelete(int flag);

        void onReTakePhoto(int flag);
    }

    // a dialog includes black background and content
    private View backgroundView;
    private View contentView;

    private TextView titleTextView;

    private PhotoView photoView;
    private PhotoViewAttacher mAttacher;

    private ButtonFlat sureButton;
    private ButtonFlat retakephotoButton;
    private ButtonFlat deleteButton;

    private Context mContext;
    private int mFlag;
    private OnActionListener mListener;
    private boolean mCancelable = true;

    public MaterialBitmapDialog(Context context) {
        super(context, android.R.style.Theme_Translucent);

        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_material_bitmap_dialog);

        backgroundView = (RelativeLayout) findViewById(R.id.dialog_rootView);
        backgroundView.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mCancelable) {
                    if (event.getX() < contentView.getLeft()
                            || event.getX() > contentView.getRight()
                            || event.getY() > contentView.getBottom()
                            || event.getY() < contentView.getTop()) {
                        dismiss();
                    }
                }
                return false;
            }
        });
        contentView = (LinearLayout) findViewById(R.id.contentDialog);

        titleTextView = (TextView) findViewById(R.id.title);

        photoView = (PhotoView) findViewById(R.id.photoview);
        mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.setScaleType(ScaleType.FIT_CENTER);

        sureButton = (ButtonFlat) findViewById(R.id.button_accept);
        sureButton.setTextSize(16);
        sureButton.setRippleColor(mContext.getResources().getColor(R.color.black_300));
        sureButton.setOnClickListener(this);

        retakephotoButton = (ButtonFlat) findViewById(R.id.button_retakephoto);
        retakephotoButton.setTextSize(16);
        retakephotoButton.setRippleColor(mContext.getResources().getColor(R.color.black_300));
        retakephotoButton.setOnClickListener(this);

        deleteButton = (ButtonFlat) findViewById(R.id.button_delete);
        deleteButton.setTextSize(16);
        deleteButton.setRippleColor(mContext.getResources().getColor(R.color.black_300));
        deleteButton.setOnClickListener(this);
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
                        photoView = null;
                        mAttacher.cleanup();
                        MaterialBitmapDialog.super.dismiss();
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
    public void setImageView(Bitmap bitmap) {
        photoView.setImageBitmap(bitmap);
    }

    /**
     * 3
     */
    public void setOnActionListener(int flag, OnActionListener listener) {
        if (null == listener) {
            retakephotoButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        } else {
            mFlag = flag;
            mListener = listener;
        }
    }
    
    /**
     * step 6
     */
    public void setSureTextColor(int color) {
        sureButton.setTextColor(color);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_accept:
                dismiss();
                break;
            case R.id.button_retakephoto:
                dismiss();
                mListener.onReTakePhoto(mFlag);
                break;
            case R.id.button_delete:
                DialogUtil.showNormalDialog(mContext, R.string.comm_delete,
                        R.string.comm_delete_bitmap, R.string.comm_sure,
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                dismiss();
                                mListener.onDelete(mFlag);
                            }
                        }, R.string.comm_cancel, null);
                break;
            default:
                break;
        }
    }
}
