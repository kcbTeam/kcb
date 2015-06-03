package com.kcb.student.model;

import java.util.Date;

import org.json.JSONObject;

public class CheckInResult {

    private long mDate;
    private boolean mHasChecked;

    public Date getDate() {
        return new Date(mDate);
    }

    public boolean hasChecked() {
        return mHasChecked;
    }

    /**
     * json to checkin result
     */
    public static final String KEY_DATE = "date";
    public static final String KEY_HASCHECKED = "haschecked";

    public static CheckInResult fromJsonObject(JSONObject jsonObject) {
        CheckInResult result = new CheckInResult();
        result.mDate = jsonObject.optLong(KEY_DATE);
        result.mHasChecked = jsonObject.optBoolean(KEY_HASCHECKED);
        return result;
    }
}
