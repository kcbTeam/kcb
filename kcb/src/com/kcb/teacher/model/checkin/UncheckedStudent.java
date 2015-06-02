package com.kcb.teacher.model.checkin;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @className: UncheckedStudent
 * @description:
 * @author: wanghang
 * @date: 2015-5-31 下午3:42:39
 */
public class UncheckedStudent implements Serializable {

    /**
     * 
     */
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
        return mUnCheckedRate;
    }


    /**
     * uncheckstudent to json
     * 
     */
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_UNCHECKRATE = "uncheckrate";

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
        UncheckedStudent uncheckedStudent = new UncheckedStudent();
        uncheckedStudent.mId = jsonObject.optString(KEY_ID);
        uncheckedStudent.mName = jsonObject.optString(KEY_NAME);
        uncheckedStudent.mUnCheckedRate = jsonObject.optDouble(KEY_UNCHECKRATE);
        return uncheckedStudent;
    }


    // TODO : for test
    public UncheckedStudent(String id, String name, double rate) {
        mId = id;
        mName = name;
        mUnCheckedRate = rate;
    }
}
