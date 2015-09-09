package com.kcb.teacher.database.checkin;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.kcb.teacher.activity.checkin.LookCheckInActivity;

/**
 * 
 * @className: UncheckedStudent
 * @description:
 * @author: wanghang
 * @date: 2015-5-31 下午3:42:39
 */
public class UncheckedStudent implements Serializable {

    private static final long serialVersionUID = 6L;

    private String mId;
    private String mName;
    private double mUnCheckedRate;

    public UncheckedStudent() {}

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getUnCheckedRate() {
        double rate = (double) Math.round(mUnCheckedRate * 100) / 100;
        return rate;
    }

    /**
     * uncheckedstudent to json, json to uncheckedstudent
     * 
     */
    public static final String KEY_ID = "stuCode";
    public static final String KEY_NAME = "realname";
    public static final String KEY_UNCHECKRATE = "uncheckedrate";
    public static final String KEY_UNCHECKNUM = "uncheck";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_NAME, mName);
            jsonObject.put(KEY_UNCHECKRATE, mUnCheckedRate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static UncheckedStudent fromJsonObject(JSONObject jsonObject) {
        UncheckedStudent student = new UncheckedStudent();
        student.mId = jsonObject.optString(KEY_ID);
        student.mName = jsonObject.optString(KEY_NAME);
        student.mUnCheckedRate = jsonObject.optDouble(KEY_UNCHECKRATE);
        return student;
    }

    public static UncheckedStudent fromJsonObject(JSONObject jsonObject, boolean fromInternet) {
        UncheckedStudent student = new UncheckedStudent();
        student.mId = jsonObject.optString(KEY_ID);
        student.mName = jsonObject.optString(KEY_NAME);
        student.mUnCheckedRate =
                Double.valueOf(jsonObject.optString(KEY_UNCHECKNUM))
                        / LookCheckInActivity.mCheckInNum;
        return student;
    }
}
