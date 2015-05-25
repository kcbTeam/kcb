package com.kcb.teacher.model.test;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

/**
 * 
 * @className: TextContent
 * @description:
 * @author: ZQJ
 * @date: 2015年5月15日 下午8:07:01
 */
public class QuestionItem implements Serializable {

    private static final long serialVersionUID = 4919254309171318451L;

    private boolean isText;
    private String mText;
    private Bitmap mBitmap;
    private byte[] mBytesOfBitmap;

    public QuestionItem() {}

    public QuestionItem(QuestionItem item) {
        isText = item.isText;
        mText = item.mText;
        mBitmap = item.mBitmap;
    }

    public boolean isText() {
        return isText;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        isText = true;
        mText = text;
    }

    public void setBitmap(Bitmap bitmap) {
        isText = false;
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        if (null != mBytesOfBitmap) {
            mBitmap = BitmapFactory.decodeByteArray(mBytesOfBitmap, 0, mBytesOfBitmap.length);
            mBytesOfBitmap = null;
        }
        return mBitmap;
    }

    public boolean equals(QuestionItem item) {
        if (this == item) {
            return true;
        }
        if (this.isText == item.isText) {
            if (this.isText) {
                return this.mText.equals(item.mText);
            } else {
                return this.mBitmap.equals(item.mBitmap);
            }
        }
        return false;
    }

    public void changeBitmapToBytes() {
        if (null != mBitmap) {
            mBytesOfBitmap = getBytes(mBitmap);
            mBitmap = null;
        }
    }

    public boolean isEmpty() {
        if (null == mBitmap && TextUtils.isEmpty(mText) && null == mBytesOfBitmap) {
            return true;
        }
        return false;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }
}
