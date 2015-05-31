package com.kcb.teacher.model.checkin;

import org.json.JSONObject;

/**
 * 
 * @className: UncheckedStudent
 * @description:
 * @author: wanghang
 * @date: 2015-5-31 下午3:42:39
 */
public class UncheckedStudent {

    private String mId;
    private String mName;
    private double mUnCheckedRate;

    public static UncheckedStudent from(JSONObject jsonObject) {
        UncheckedStudent uncheckedStudent = new UncheckedStudent();
        uncheckedStudent.mId = jsonObject.optString("");
        uncheckedStudent.mName = jsonObject.optString("");
        uncheckedStudent.mUnCheckedRate = jsonObject.optDouble("");
        return uncheckedStudent;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getUnCheckedRate() {
        return mUnCheckedRate;
    }
}
