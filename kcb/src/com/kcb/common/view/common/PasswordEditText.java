package com.kcb.common.view.common;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kcb.library.view.FloatingEditText;
import com.kcbTeam.R;

/**
 * 
 * @className: SearchEditText
 * @description: input key words and search, has a clear icon; init it, set listener and hint;
 * @author: wanghang
 * @date: 2015-6-7 下午9:08:04
 */
public class PasswordEditText extends RelativeLayout implements OnClickListener {

    public PasswordEditText(Context context) {
        super(context);
        init(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private FloatingEditText passwordEditText;
    private ImageView changeInputTypeImageView;

    /**
     * 1 step
     */
    private void init(Context context) {
        inflate(context, R.layout.comm_view_edittext_password, this);
        initView();
    }

    private void initView() {
        passwordEditText = (FloatingEditText) findViewById(R.id.edittext_password);

        changeInputTypeImageView = (ImageView) findViewById(R.id.imageview_changeinputtype);
        changeInputTypeImageView.setOnClickListener(this);
    }

    /**
     * 3 step
     */
    public void setHint(int resId) {
        passwordEditText.setHint(resId);
    }

    /**
     * 4 step
     */
    public String getText() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_changeinputtype:
                switch (passwordEditText.getInputType()) {
                    case 129:
                        passwordEditText
                                .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        changeInputTypeImageView
                                .setImageResource(R.drawable.ic_visibility_grey600_24dp);
                        break;
                    case InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
                        passwordEditText.setInputType(129);
                        changeInputTypeImageView
                                .setImageResource(R.drawable.ic_visibility_off_grey600_24dp);
                    default:
                        break;
                }
                passwordEditText.setSelection(passwordEditText.getText().toString().length());
                break;
            default:
                break;
        }
    }
}
