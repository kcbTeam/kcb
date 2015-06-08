package com.kcb.teacher.model.checkin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @className: CheckInRecordInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:01
 */
public class CheckInResult implements Serializable {

    private static final long serialVersionUID = 3L;

    private long mDate;
    private double mRate;
    private List<UncheckedStudent> mUnCheckedStudents;

    private CheckInResult() {
        mUnCheckedStudents = new ArrayList<UncheckedStudent>();
    }

    public long getDate() {
        return mDate;
    }

    public String getDateString() {
        return new Date(mDate).toString();
    }

    public double getRate() {
        return mRate;
    }

    public List<UncheckedStudent> getUnCheckedStudents() {
        return mUnCheckedStudents;
    }

    public String toString() {
        return toJsonObject().toString();
    }

    /**
     * checkinresult to json ,json to checkinresult
     */
    public static final String KEY_DATE = "date";
    public static final String KEY_RATE = "rate";
    public static final String KEY_UNCHECKSTU = "uncheckstudents";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_DATE, mDate);
            jsonObject.put(KEY_RATE, mRate);

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mUnCheckedStudents.size(); i++) {
                jsonArray.put(mUnCheckedStudents.get(i).toJsonObject());
            }

            jsonObject.put(KEY_UNCHECKSTU, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static CheckInResult fromJsonObject(JSONObject jsonObject) {
        CheckInResult checkInResult = new CheckInResult();
        try {
            checkInResult.mDate = jsonObject.optLong(KEY_DATE);
            checkInResult.mRate = jsonObject.optDouble(KEY_RATE);

            JSONArray jsonArray = jsonObject.optJSONArray(KEY_UNCHECKSTU);
            for (int i = 0; i < jsonArray.length(); i++) {
                checkInResult.mUnCheckedStudents.add(UncheckedStudent.fromJsonObject(jsonArray
                        .getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return checkInResult;
    }
}
