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

    private String mId; // from client

    private boolean isText = true;
    private String mText = "";
    private Bitmap mBitmap;
    private byte[] mBytesOfBitmap;
    private boolean isRight = false;
    private float mRate; // if the item is a choice, mRate represent a choice rate;if the item is a
                         // question title, mRate represent the correct rate

    public QuestionItem() {}

    public QuestionItem(String text) {
        mText = text;
    }

    public static void copy(QuestionItem oldItem, QuestionItem newItem) {
        if (oldItem.isText) {
            newItem.setText(oldItem.getText());
        } else {
            newItem.setBitmap(oldItem.getBitmap());
        }
        newItem.setIsRight(oldItem.isRight);
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
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
        mBitmap = null;
        mBytesOfBitmap = null;
    }

    public void setBitmap(Bitmap bitmap) {
        isText = false;
        mText = "";
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        if (null != mBytesOfBitmap) {
            return BitmapFactory.decodeByteArray(mBytesOfBitmap, 0, mBytesOfBitmap.length);
        }
        return mBitmap;
    }

    public void setIsRight(boolean _isRight) {
        isRight = _isRight;
    }

    public boolean isRight() {
        return isRight;
    }

    public float getRate() {
        return mRate;
    }

    public void setRate(float rate) {
        mRate = rate;
    }

    public boolean equals(QuestionItem item) {
        if (isRight == item.isRight) {
            if (isText == item.isText) {
                if (isText) {
                    return mText.equals(item.mText);
                } else {
                    if (null != item.mBitmap) {
                        return mBitmap.equals(item.mBitmap);
                    }
                }
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

    public boolean isCompleted() {
        return !TextUtils.isEmpty(mText) || mBitmap != null;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }
}
