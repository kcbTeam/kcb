package com.kcb.student.model.checkin;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckInResultDetail {

    private long mDate;
    private boolean mHasChecked;

    public long getDate() {
        return mDate;
    }

    public boolean hasChecked() {
        return mHasChecked;
    }

    /**
     * json to checkin result
     */
    public static final String KEY_DATE = "date";
    public static final String KEY_HASCHECKED = "haschecked";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_DATE, mDate);
            jsonObject.put(KEY_HASCHECKED, mHasChecked);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static CheckInResultDetail fromJsonObject(JSONObject jsonObject) {
        CheckInResultDetail result = new CheckInResultDetail();
        result.mDate = jsonObject.optLong(KEY_DATE);
        result.mHasChecked = jsonObject.optBoolean(KEY_HASCHECKED);
        return result;
    }
}
