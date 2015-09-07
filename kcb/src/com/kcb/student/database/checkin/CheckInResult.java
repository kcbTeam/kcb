package com.kcb.student.database.checkin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.kcb.common.util.LogUtil;

@SuppressLint("SimpleDateFormat")
public class CheckInResult {

    private static final String TAG = CheckInResult.class.getName();

    private long mDate;
    private boolean mHasChecked;

    public long getDate() {
        return mDate;
    }

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(mDate));
    }

    public boolean getHasChecked() {
        return mHasChecked;
    }

    /**
     * json to checkin result
     */
    public static final String KEY_DATE = "createDate";
    public static final String KEY_HASCHECKED = "haschecked";
    public static final String KEY_STATE = "state";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_DATE, mDate);
            jsonObject.put(KEY_HASCHECKED, mHasChecked);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static CheckInResult fromJsonObject(JSONObject jsonObject) {
        CheckInResult result = new CheckInResult();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(jsonObject.optString(KEY_DATE));
            result.mDate = date.getTime();
            result.mHasChecked = jsonObject.optString(KEY_STATE).equals("0");
        } catch (ParseException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }
}
