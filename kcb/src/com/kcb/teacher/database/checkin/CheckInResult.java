package com.kcb.teacher.database.checkin;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Telephony.Mms.Rate;
import android.text.format.DateUtils;

/**
 * 
 * @className: CheckInResult
 * @description: 老师的单次签到结果，包括签到日期、签到率、未到课学生情况
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:01
 */
public class CheckInResult implements Serializable {

    private static final long serialVersionUID = 3L;

    private String mDate;
    private double mRate;
    private List<UncheckedStudent> mUnCheckedStudents;

    private CheckInResult() {
        mUnCheckedStudents = new ArrayList<UncheckedStudent>();
    }

    public long getDateLong() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(mDate);
        } catch (ParseException e) {}
        long dateLong = date.getTime();
        return dateLong;
    }

    public String getDateTimeString(){
        return mDate;
    }
    
    public String getDateString() {
        return mDate.substring(0, mDate.lastIndexOf(" "));
        // return mDate.replace(" ", "\n");

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        // Date date = new Date();
        // try {
        // date = sdf.parse(mDate);
        // } catch (ParseException e) {}
        // long dateLong = date.getTime();
        //
        // SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd\nhh:mm:ss");
        // return sdf2.format(new Date(dateLong));
        // return new Date(mDate).toString();
    }

    public String getTimeString() {
        return mDate.substring(mDate.lastIndexOf(" ") + 1);
    }

    public double getRate() {
        double rate = (double) Math.round(mRate * 100) / 100;
        return rate;
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
        // 服务器端返回的日期格式为 2015-08-01 20:45:00.0
        String dateString = jsonObject.optString(KEY_DATE);
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        // Date date = new Date();
        // try {
        // date = sdf.parse(dateString);
        // } catch (ParseException e) {}
        // long dateLong = date.getTime();

        checkInResult.mDate = dateString;
        checkInResult.mRate = jsonObject.optDouble(KEY_RATE);

        // 如果是从数据库中读取数据，需要获取学生的信息；
        JSONArray jsonArray = jsonObject.optJSONArray(KEY_UNCHECKSTU);
        if (null != jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                checkInResult.mUnCheckedStudents.add(UncheckedStudent.fromJsonObject(jsonArray
                        .optJSONObject(i)));
            }
        }
        return checkInResult;
    }
}
