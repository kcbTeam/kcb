package com.kcb.teacher.model;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 
 * @className: TextContent
 * @description:
 * @author: ZQJ
 * @date: 2015年5月15日 下午8:07:01
 */
public class TextContent implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4919254309171318451L;

    @SuppressWarnings("unused")
    private static final String TAG = "TextContent";

    private boolean isString = false;
    private String mContentString;
    private Bitmap mContentBitmap;

    public TextContent(String stringContent) {
        mContentString = stringContent;
        isString = true;
    }

    public TextContent(Bitmap bitmapContent) {
        mContentBitmap = bitmapContent;
        isString = false;
    }

    public boolean isString() {
        return isString;
    }

    public String getContentString() {
        return this.mContentString;
    }

    public void setContentString(String mContentString) {
        if (isString) {
            this.mContentString = mContentString;
        }
    }

    public Bitmap getContentBitmap() {
        return this.mContentBitmap;
    }

    public void setContentBitmap(Bitmap mContentBitmap) {
        if (!isString) {
            this.mContentBitmap = mContentBitmap;
        }
    }


}
