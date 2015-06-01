package com.kcb.teacher.model.checkin;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 
 * @className: UncheckedStudent
 * @description:
 * @author: wanghang
 * @date: 2015-5-31 下午3:42:39
 */
public class UncheckedStudent implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6L;
    private String mId;
    private String mName;
    private double mUnCheckedRate;

    public static UncheckedStudent from(JSONObject jsonObject) {
        UncheckedStudent uncheckedStudent = new UncheckedStudent();
        uncheckedStudent.mId = jsonObject.optString("mId");
        uncheckedStudent.mName = jsonObject.optString("mName");
        uncheckedStudent.mUnCheckedRate = jsonObject.optDouble("mUnCheckedRate");
        return uncheckedStudent;
    }

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

    // TODO : for test
    public UncheckedStudent(String id, String name, double rate) {
        mId = id;
        mName = name;
        mUnCheckedRate = rate;
    }
}
