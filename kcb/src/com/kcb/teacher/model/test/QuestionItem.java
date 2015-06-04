package com.kcb.teacher.model.test;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.kcb.common.util.BitmapUtil;

/**
 * 
 * @className: TextContent
 * @description:
 * @author: ZQJ
 * @date: 2015年5月15日 下午8:07:01
 */
public class QuestionItem implements Serializable {

    private static final long serialVersionUID = 4919254309171318451L;

    private int mId; // from client, useful when item is choice

    private boolean mIsText = true;
    private String mText = "";
    private Bitmap mBitmap;
    private String mBitmapString;

    private boolean mIsRight; // useful when item is choice
    private double mRate; // useful when item is choice, mRate represent a choice rate

    private boolean mIsSelected; // used in stu module, useful when item is choice, true if stu
                                 // selected this choice

    public QuestionItem() {}

    public QuestionItem(String text) {
        mText = text;
    }

    public static void copy(QuestionItem oldItem, QuestionItem newItem) {
        newItem.mId = oldItem.mId;
        if (oldItem.mIsText) {
            newItem.setText(oldItem.mText);
        } else {
            newItem.setBitmap(oldItem.mBitmap);
        }
        newItem.mIsRight = oldItem.mIsRight;
        newItem.mRate = oldItem.mRate;
        newItem.mIsSelected = oldItem.mIsSelected;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public boolean isText() {
        return mIsText;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mIsText = true;
        mText = text;
        mBitmap = null;
        mBitmapString = null;
    }

    public void setBitmap(Bitmap bitmap) {
        mIsText = false;
        mText = "";
        mBitmap = bitmap;
        mBitmapString = null;
    }

    public Bitmap getBitmap() {
        if (null != mBitmap) {
            return mBitmap;
        } else if (null != mBitmapString) {
            return BitmapUtil.stringToBitmap(mBitmapString);
        } else {
            return null;
        }
    }

    public String getBitmapString() {
        if (null != mBitmap) {
            mBitmapString = BitmapUtil.bitmapToString(mBitmap);
        }
        return mBitmapString;
    }

    public void setIsRight(boolean _isRight) {
        mIsRight = _isRight;
    }

    public boolean isRight() {
        return mIsRight;
    }

    public double getRate() {
        return mRate;
    }

    public void setRate(double rate) {
        mRate = rate;
    }

    public boolean equals(QuestionItem item) {
        if (mIsRight == item.mIsRight) {
            if (mIsText == item.mIsText) {
                if (mIsText) {
                    return mText.equals(item.mText);
                } else {
                    if (null != item.getBitmap()) {
                        return getBitmap().equals(item.getBitmap()); // when use intent transport
                                                                     // data , mBitmap must set as
                                                                     // null,so use gtBitmap() is
                                                                     // safer. mBitmap maybe =null
                    }
                }
            }
        }
        return false;
    }

    public boolean isCompleted() {
        return !TextUtils.isEmpty(mText) || mBitmap != null || mBitmapString != null;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }

    /**
     * question item to json, json to question item
     */
    public static final String KEY_ID = "id";
    public static final String KEY_ISTEXT = "istext";
    public static final String KEY_TEXT = "text";
    public static final String KEY_BITMAPSTRING = "bitmapstring";
    public static final String KEY_ISRIGHT = "isright";
    public static final String KEY_RATE = "rate";
    public static final String KEY_ISSELECTED = "isselected";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_ISTEXT, mIsText);
            jsonObject.put(KEY_TEXT, mText);
            jsonObject.put(KEY_BITMAPSTRING, getBitmapString());
            jsonObject.put(KEY_ISRIGHT, mIsRight);
            jsonObject.put(KEY_RATE, mRate);
            jsonObject.put(KEY_ISSELECTED, mIsSelected);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static QuestionItem fromJsonObject(JSONObject jsonObject) {
        QuestionItem item = new QuestionItem();
        item.mId = jsonObject.optInt(KEY_ID);
        item.mIsText = jsonObject.optBoolean(KEY_ISTEXT);
        item.mText = jsonObject.optString(KEY_TEXT);
        item.mBitmapString = jsonObject.optString(KEY_BITMAPSTRING);
        item.mIsRight = jsonObject.optBoolean(KEY_ISRIGHT);
        item.mRate = jsonObject.optDouble(KEY_RATE);
        item.mIsSelected = jsonObject.optBoolean(KEY_ISSELECTED);
        return item;
    }
}
