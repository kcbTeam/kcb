package com.kcb.student.model.checkin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckInResult {

    private int mSuccessTime;
    private int mAllTime;
    private List<CheckInResultDetail> results;

    private static final String KEY_SUCCESS_TIME = "successtime";
    private static final String KEY_ALL_TIME = "alltime";
    private static final String KEY_RESULT = "result";

    private CheckInResult() {
        results = new ArrayList<CheckInResultDetail>();
    }

    /**
     * checkin resutl to json, json to checkin result
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_SUCCESS_TIME, mSuccessTime);
            jsonObject.put(KEY_ALL_TIME, mAllTime);

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < results.size(); i++) {
                jsonArray.put(results.get(i).toJsonObject());
            }

            jsonObject.put(KEY_RESULT, jsonArray);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static CheckInResult fromJsonObejct(JSONObject jsonObject) {
        CheckInResult checkInResult = new CheckInResult();
        checkInResult.mSuccessTime = jsonObject.optInt(KEY_SUCCESS_TIME);
        checkInResult.mAllTime = jsonObject.optInt(KEY_ALL_TIME);

        try {
            JSONArray jsonArray = jsonObject.optJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                checkInResult.results.add(CheckInResultDetail.fromJsonObject(jsonArray
                        .getJSONObject(i)));
            }
        } catch (JSONException e) {}

        return checkInResult;
    }
}
