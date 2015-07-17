package com.kcb.common.model.feedback;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @className: FeedBack
 * @description: 意见反馈
 * @author: wanghang
 * @date: 2015-7-17 下午5:25:23
 */
public class FeedBack {

    private String mId; // 后台生成的意见反馈的id
    private int mType; // 0-对app的反馈，1-学生对老师的反馈；
    // 学生提交反馈，需要包括当前课程对应老师的id和姓名；
    // 老师提交反馈，只需要包括自己的id和姓名
    private String mStuId; // 学生的学号
    private String mStuName; // 学生的姓名
    private String mTchId; // 老师的账号
    private String mTchName; // 老师的姓名

    private boolean mIsSecret; // 是否是匿名提交的反馈
    private String mText; // 反馈的内容
    private long mDate; // 提交反馈的时间戳，后台生成

    // 反馈的类型
    public interface TYPE {
        int TYPE_TO_APP = 0;
        int TYPE_TO_TEACHER = 0;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getStuId() {
        return mStuId;
    }

    public void setStuId(String stuId) {
        mStuId = stuId;
    }

    public String getStuName() {
        return mStuName;
    }

    public void setStuName(String stuName) {
        mStuName = stuName;
    }

    public String getTchId() {
        return mTchId;
    }

    public void setTchId(String tchId) {
        mTchId = tchId;
    }

    public String getTchName() {
        return mTchName;
    }

    public void setTchName(String tchName) {
        mTchName = tchName;
    }

    public boolean isSecret() {
        return mIsSecret;
    }

    public void setIsSecret(boolean isSecret) {
        mIsSecret = isSecret;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public long getDate() {
        return mDate;
    }

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(getDate()));
        return date;
    }

    public void setDate(long date) {
        mDate = date;
    }

    /**
     * feedback to jsonobject:
     */
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_STUID = "stuid";
    private static final String KEY_STUNAME = "stuname";
    private static final String KEY_TCHID = "tchid";
    private static final String KEY_TCHNAME = "tchname";
    private static final String KEY_ISSECRET = "issecret";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATE = "date";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_TYPE, mType);
            jsonObject.put(KEY_STUID, mStuId);
            jsonObject.put(KEY_STUNAME, mStuName);
            jsonObject.put(KEY_TCHID, mTchId);
            jsonObject.put(KEY_TCHNAME, mTchName);
            jsonObject.put(KEY_ISSECRET, mIsSecret);
            jsonObject.put(KEY_TEXT, mText);
            jsonObject.put(KEY_DATE, mDate);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static FeedBack fromJsonObject(JSONObject jsonObject) {
        FeedBack feedBack = new FeedBack();
        feedBack.mId = jsonObject.optString(KEY_ID);
        feedBack.mType = jsonObject.optInt(KEY_TYPE);
        feedBack.mStuId = jsonObject.optString(KEY_STUID);
        feedBack.mStuName = jsonObject.optString(KEY_STUNAME);
        feedBack.mTchId = jsonObject.optString(KEY_TCHID);
        feedBack.mTchName = jsonObject.optString(KEY_TCHNAME);
        feedBack.mIsSecret = jsonObject.optBoolean(KEY_ISSECRET);
        feedBack.mText = jsonObject.optString(KEY_TEXT);
        feedBack.mDate = jsonObject.optLong(KEY_DATE);
        return feedBack;
    }
}
