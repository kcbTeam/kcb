package com.kcb.teacher.model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    private byte[] mBytesOfBitmap;

    public TextContent() {
        mContentString = "";
        isString = true;
    }

    public TextContent(String stringContent) {
        mContentString = stringContent;
        isString = true;
    }

    public TextContent(Bitmap bitmapContent) {
        mContentBitmap = bitmapContent;
        isString = false;
    }

    public TextContent(TextContent textContent) {
        isString = textContent.isString;
        mContentString = textContent.mContentString;
        mContentBitmap = textContent.mContentBitmap;
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

    public boolean equals(TextContent o) {
        if (this == o) {
            return true;
        }
        if (this.isString == o.isString) {
            if (this.isString) {
                return this.mContentString.equals(o.mContentString);
            } else {
                return this.mContentBitmap.equals(o.mContentBitmap);
            }
        }
        return false;
    }

    public void copy(TextContent textContent) {
        mContentBitmap = textContent.mContentBitmap;
        mContentString = textContent.mContentString;
        isString = textContent.isString;
    }

    public void changeBitmapToBytes() {
        if (null != mContentBitmap) {
            mBytesOfBitmap = getBytes(mContentBitmap);
            mContentBitmap = null;
        }
    }

    public void recoverBitmapFromBytes() {
        if (null != mBytesOfBitmap) {
            mContentBitmap = getBitmap(mBytesOfBitmap);
            mBytesOfBitmap = null;
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
