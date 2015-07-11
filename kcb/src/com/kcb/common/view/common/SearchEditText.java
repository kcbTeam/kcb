package com.kcb.common.view.common;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.kcb.common.base.BaseRelativeLayout;
import com.kcb.library.view.FloatingEditText;
import com.kcbTeam.R;

/**
 * 
 * @className: SearchEditText
 * @description: input key words and search, has a clear icon; init it, set listener and hint;
 * @author: wanghang
 * @date: 2015-6-7 下午9:08:04
 */
public class SearchEditText extends BaseRelativeLayout implements TextWatcher {

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * set this listener, then you can listen user's input or clear action
     */
    public interface OnSearchListener {
        void onSearch(String text);

        void onClear();
    }

    private FloatingEditText searchEditText;
    private ImageView clearImageView;

    private OnSearchListener mListener;

    /**
     * 1 step
     */
    @Override
    public void init(Context context) {
        inflate(context, R.layout.comm_view_edittext_search, this);
        initView();
    }

    @Override
    public void initView() {
        searchEditText = (FloatingEditText) findViewById(R.id.edittext_search);
        searchEditText.addTextChangedListener(this);

        clearImageView = (ImageView) findViewById(R.id.imageview_clear);
        clearImageView.setOnClickListener(this);
    }

    @Override
    public void release() {
        mListener = null;
    }

    /**
     * 1 step
     */
    public void setOnSearchListener(OnSearchListener listener) {
        mListener = listener;
    }

    /**
     * 2 step
     */
    public void setHint(int resId) {
        searchEditText.setHint(resId);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String text = searchEditText.getText().toString().trim().replace(" ", "");
        if (!text.equals("")) {
            clearImageView.setVisibility(View.VISIBLE);
            mListener.onSearch(text);
        } else {
            clearImageView.setVisibility(View.INVISIBLE);
            mListener.onClear();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_clear:
                searchEditText.setText("");
                clearImageView.setVisibility(View.INVISIBLE);
                mListener.onClear();
                break;
            default:
                break;
        }
    }

    public void setText(String text) {
        searchEditText.setText(text);
    }
}
