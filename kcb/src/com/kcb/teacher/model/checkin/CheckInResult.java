package com.kcb.teacher.model.checkin;

import java.io.Serializable;
import java.sql.Date;
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

    private Date mDate;
    private double mRate;
    private List<UncheckedStudent> mUnCheckedStudents;

    private CheckInResult() {}

    public CheckInResult(Date signDate, Double signRate, List<UncheckedStudent> uncheckedStudents) {
        mDate = signDate;
        mRate = signRate;
        mUnCheckedStudents = uncheckedStudents;
    }

    public CheckInResult from(JSONObject jsonObject) {
        // TODO jsonObject from server
        CheckInResult checkInResult = new CheckInResult();
        checkInResult.mDate = new Date(jsonObject.optLong(""));
        checkInResult.mRate = jsonObject.optDouble("");
        JSONArray jsonArray = jsonObject.optJSONArray("");
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                UncheckedStudent student = UncheckedStudent.from(jsonArray.getJSONObject(i));
                checkInResult.mUnCheckedStudents.add(student);
            } catch (JSONException e) {}
        }
        return checkInResult;
    }

    public Date getDate() {
        return mDate;
    }

    public double getRate() {
        return mRate;
    }

    public List<UncheckedStudent> getUnCheckedStudents() {
        return mUnCheckedStudents;
    }
}
