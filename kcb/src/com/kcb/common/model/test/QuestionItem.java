package com.kcb.common.model.test;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.kcb.student.util.FileUtil;

/**
 * 
 * @className: TextContent
 * @description:
 * @author: ZQJ
 * @date: 2015年5月15日 下午8:07:01
 */
public class QuestionItem {

    private int mId; // item的id，由终端确定，0标识title，1-4表示四个选项

    private boolean mIsText; // 内容是否是文字
    private String mText; // 文字内容

    private Bitmap mBitmap; // 图片内容，临时显示；数据库存的是对应的文件路径，发到后台的是改路径对应的文件本身
    private String mBitmapPath; // 如果是老师出题，题目在本地的保存路径
    private String mBitmapUrl; // 如果从后台拿取图片，拿的是图片的url
    // 发送图片的时候，对应的key，格式为 question_0_item_0，第一个数字从0开始，表示题目的id，第二个数字为0-4，表示题目内容、4个选项
    private String mBitmapKey;

    private boolean mIsRight; // 如果是选项，表示选项是否是正确项
    private boolean mIsSelected; // 在学生模块，如果是选项，表示学生是否选择了此项

    public QuestionItem() {
        mIsText = true;
        mText = "";
    }

    public static void copy(QuestionItem oldItem, QuestionItem newItem) {
        newItem.mId = oldItem.mId;
        newItem.mIsText = oldItem.mIsText;
        if (oldItem.mIsText) {
            newItem.setText(oldItem.mText);
        } else {
            newItem.setBitmap(oldItem.mBitmap);
        }
        newItem.mBitmapPath = oldItem.mBitmapPath;
        newItem.mBitmapUrl = oldItem.mBitmapUrl;
        newItem.mBitmapKey = oldItem.mBitmapKey;
        newItem.mIsRight = oldItem.mIsRight;
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
        if (!TextUtils.isEmpty(mBitmapPath)) {
            new File(mBitmapPath).delete();
        }
        mBitmapPath = "";
    }

    public void setBitmap(Bitmap bitmap) {
        mIsText = false;
        mText = "";
        mBitmap = bitmap;
        mBitmapPath = "";
    }

    public Bitmap getBitmap() {
        if (null != mBitmap) {
            return mBitmap;
        } else if (null != mBitmapPath) {
            mBitmap = BitmapFactory.decodeFile(mBitmapPath);
            return mBitmap;
        } else {
            return null;
        }
    }

    public void setBitmapPath(String bitmapPath) {
        mBitmapPath = bitmapPath;
    }

    public String getBitmapPath() {
        return mBitmapPath;
    }

    public String getBitmapUrl() {
        return mBitmapUrl;
    }

    // 设置传输图片时候的key
    public void setBitmapKey(int questionId) {
        mBitmapKey = "";
        if (!isText()) {
            mBitmapKey = "question_" + questionId + "_item_" + mId;
        }
    }

    public void setIsRight(boolean isRight) {
        mIsRight = isRight;
    }

    public boolean isRight() {
        return mIsRight;
    }

    public void setIsSelected(boolean selected) {
        mIsSelected = selected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public boolean equals(QuestionItem item) {
        if (mIsRight == item.mIsRight) {
            if (mIsText == item.mIsText) {
                if (mIsText) {
                    return mText.equals(item.mText);
                } else {
                    if (null != item.getBitmap()) {
                        return getBitmap().equals(item.getBitmap());
                    }
                }
            }
        }
        return false;
    }

    /**
     * 编辑测试：题目内容/选项是否编辑完成了
     */
    public boolean isEidtFinish() {
        return !TextUtils.isEmpty(mText) || mBitmap != null;
    }

    public void deleteBitmap() {
        if (null != mBitmapPath) {
            new File(mBitmapPath).delete();
        }
    }

    public void renameBitmap(String testName, int questionIndex, int itemIndex) {
        if (!TextUtils.isEmpty(mBitmapPath)) {
            File file = new File(mBitmapPath);
            String newPath = FileUtil.getQuestionItemPath(testName, questionIndex + 1, itemIndex);
            file.renameTo(new File(newPath));
            mBitmapPath = newPath;
        }
    }

    public void release() {
        if (null != mBitmap) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    /**
     * question item to json, json to question item
     */
    public static final String KEY_ID = "id";
    public static final String KEY_ISTEXT = "istext";
    public static final String KEY_TEXT = "text";
    public static final String KEY_BITMAPPATH = "bitmappath";
    public static final String KEY_BITMAPURL = "bitmapurl";
    public static final String KEY_BITMAPKEY = "bitmapkey";
    public static final String KEY_ISRIGHT = "isright";
    public static final String KEY_ISSELECTED = "isselected";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId); // item的id
            jsonObject.put(KEY_ISTEXT, mIsText); // 是否是文字
            jsonObject.put(KEY_TEXT, mText); // 文字内容
            jsonObject.put(KEY_BITMAPPATH, mBitmapPath); // 图片路径
            jsonObject.put(KEY_BITMAPURL, mBitmapUrl); // 图片的url
            jsonObject.put(KEY_BITMAPKEY, mBitmapKey); // 发送到后台时用的key
            jsonObject.put(KEY_ISRIGHT, mIsRight); // 如果是选项，表示是否是正确选项
            jsonObject.put(KEY_ISSELECTED, mIsSelected); // 如果是选项，表示是否选择了
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static QuestionItem fromJsonObject(JSONObject jsonObject) {
        QuestionItem item = new QuestionItem();
        item.mId = jsonObject.optInt(KEY_ID); // item的id
        item.mIsText = jsonObject.optBoolean(KEY_ISTEXT); // 是否是文字
        item.mText = jsonObject.optString(KEY_TEXT); // 文字内容
        item.mBitmapPath = jsonObject.optString(KEY_BITMAPPATH); // 图片路径
        item.mBitmapUrl = jsonObject.optString(KEY_BITMAPURL); // 图片url
        item.mBitmapKey = jsonObject.optString(KEY_BITMAPKEY); // 发送到后台时用的key
        item.mIsRight = jsonObject.optBoolean(KEY_ISRIGHT); // 如果是选项，是否是正确选项
        item.mIsSelected = jsonObject.optBoolean(KEY_ISSELECTED); // 如果是选项，表示是否选择了
        return item;
    }
}
