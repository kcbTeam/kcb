package com.kcb.teacher.database.students;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @className: StudentInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:09
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId;
    private String mName;
    private double mCheckInRate;
    private double mCorrectRate;

    public Student() {}

    public Student(String name, String studentID) {
        mId = studentID;
        mName = name;
    }

    // add by ljx
    public Student(String name, String studentID, Double checkInRate, Double correctRate) {
        mId = studentID;
        mName = name;
        mCheckInRate = checkInRate;
        mCorrectRate = correctRate;
    }

    /**
     * 
     * Constructor: StudentInfo checkinTimes is success checkinTimes of the student correctTimes is
     * correctTimes of the student
     * 
     */
    public void setID(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getCheckInRate() {
        return mCheckInRate;
    }

    public void setCheckInRate(double checkinrate) {
        mCheckInRate = checkinrate;
    }

    public double getCorrectRate() {
        return mCorrectRate;
    }

    public void setCorrectRate(double correctrate) {
        mCorrectRate = correctrate;
    }

    public String toString() {
        return toJsonObject().toString();
    }

    /*
     * student to json,json to student
     */
    private final static String KEY_ID = "id";
    private final static String KEY_NAME = "name";
    private final static String KEY_CHECKINRATE = "checkinrate";
    private final static String KEY_CORRECTRATE = "correctrate";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_NAME, mName);
            jsonObject.put(KEY_CHECKINRATE, mCheckInRate);
            jsonObject.put(KEY_CORRECTRATE, mCorrectRate);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static Student fromjsonObject(JSONObject jsonObject) {
        Student student = new Student();
        student.mId = jsonObject.optString(KEY_ID);
        student.mName = jsonObject.optString(KEY_NAME);
        student.mCheckInRate = jsonObject.optDouble(KEY_CHECKINRATE);
        student.mCorrectRate = jsonObject.optDouble(KEY_CORRECTRATE);
        return student;
    }
}
