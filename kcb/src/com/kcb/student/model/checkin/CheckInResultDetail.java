package com.kcb.student.model.checkin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.kcb.common.util.LogUtil;

public class CheckInResultDetail {

    private static final String TAG = CheckInResultDetail.class.getName();

    private long mDate;
    private boolean mHasChecked;

    public long getDate() {
        return mDate;
    }

    public String getDateString() {
        return new Date(mDate).toString();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(jsonObject.optString(KEY_DATE));
            result.mDate = date.getTime();
        } catch (ParseException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        result.mHasChecked = jsonObject.optBoolean(KEY_HASCHECKED);
        return result;
    }
}
