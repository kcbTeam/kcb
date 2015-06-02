package com.kcb.teacher.database.students;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.CommonSQLiteOpenHelper;
import com.kcb.teacher.database.checkin.CheckInDB;
import com.kcb.teacher.model.checkin.CheckInResult;
import com.kcb.teacher.model.checkin.UncheckedStudent;

public class StudentDao {
	private CommonSQLiteOpenHelper mCommonSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public StudentDao(Context context) {
        mCommonSQLiteOpenHelper = new CommonSQLiteOpenHelper(context);
        mSqLiteDatabase = mCommonSQLiteOpenHelper.getWritableDatabase();
    }

    /**
     * 
     * @title: addCheckInReslt
     * @description: add a CheckInResult to db
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:15:14
     * @param checkInResult
     * @throws SQLException
     * @throws JSONException
     */
    public void addCheckInReslt(CheckInResult checkInResult) throws SQLException, JSONException {
        String date = formatter.format(checkInResult.getDate());
        mSqLiteDatabase.execSQL("INSERT INTO " + CheckInDB.TABLE_NAME + " VALUES(null,?,?,?)",
                new String[] {date, String.valueOf(checkInResult.getRate()),
                        changeUnCheckStudentsToString(checkInResult.getUnCheckedStudents())});
    }


    /**
     * 
     * @title: getCheckInResultByDate
     * @description: get CheckInResults from db by Date info
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:49:27
     * @param date
     * @return
     * @throws ParseException
     * @throws JSONException
     */
    public List<CheckInResult> getCheckInResultByDate(Date date) throws ParseException,
            JSONException {
        String dateString = formatter.format(date);
        Cursor cursor =
                mSqLiteDatabase.rawQuery("SELECT * FROM " + CheckInDB.TABLE_NAME + " WHERE "
                        + CheckInDB.KEY_DATE + "=?", new String[] {dateString});
        List<CheckInResult> checkInResultsList = new ArrayList<CheckInResult>();
        if (cursor.moveToFirst()) {
            do {
                dateString = cursor.getString(cursor.getColumnIndex(CheckInDB.KEY_DATE));
                String rate = cursor.getString(cursor.getColumnIndex(CheckInDB.KEY_RATE));
                String uncheckStudents =
                        cursor.getString(cursor.getColumnIndex(CheckInDB.KEY_UNCHECK_STUDENTS));
                Date mDate = formatter.parse(dateString);
                double mRate = Double.valueOf(rate);
                List<UncheckedStudent> uncheckedStudentList =
                        jsonStringToUncheckedStudent(uncheckStudents);
                checkInResultsList.add(new CheckInResult(mDate, mRate, uncheckedStudentList));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checkInResultsList;
    }


    /**
     * 
     * @title: getAllCheckInResults
     * @description: get all checkinresult records from db
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:37:50
     * @return
     * @throws ParseException
     * @throws JSONException
     */
    public List<CheckInResult> getAllCheckInResults() throws ParseException, JSONException {
        Cursor cursor =
                mSqLiteDatabase.query(CheckInDB.TABLE_NAME, null, null, null, null, null, null);
        List<CheckInResult> checkInResultsList = new ArrayList<CheckInResult>();
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex(CheckInDB.KEY_DATE));
                String rate = cursor.getString(cursor.getColumnIndex(CheckInDB.KEY_RATE));
                String uncheckStudents =
                        cursor.getString(cursor.getColumnIndex(CheckInDB.KEY_UNCHECK_STUDENTS));
                Date mDate = formatter.parse(date);
                double mRate = Double.valueOf(rate);
                List<UncheckedStudent> uncheckedStudentList =
                        jsonStringToUncheckedStudent(uncheckStudents);
                checkInResultsList.add(new CheckInResult(mDate, mRate, uncheckedStudentList));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checkInResultsList;
    }


    public void deleteCheckInResultsByDate(Date date) {
        String dateString = formatter.format(date);
        mSqLiteDatabase.delete(CheckInDB.TABLE_NAME, CheckInDB.KEY_DATE + "=?",
                new String[] {dateString});
    }

    /**
     * 
     * @title: deleteAllCheckInResults
     * @description: delete All records
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:52:58
     */
    public void deleteAllCheckInResults() {
        mSqLiteDatabase.execSQL("DELETE FROM " + CheckInDB.TABLE_NAME);
    }

    public void close() {
        mSqLiteDatabase.close();
    }

    /**
     * 
     * @title: changeUnCheckStudentsToString
     * @description: List<UnCheckedStudent> to String
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:14:07
     * @param list
     * @return
     * @throws JSONException
     */
    private static String changeUnCheckStudentsToString(List<UncheckedStudent> list)
            throws JSONException {
        int length = list.size();
        if (length < 1) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < length; i++) {
            jsonArray.put(changeUnCheckStudentsToJsonObject(list.get(i)));
        }
        return jsonArray.toString();
    }


    /**
     * 
     * @title: changeUnCheckStudentsToJsonObject
     * @description: UnCheckedStudent to JSONObject
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:14:33
     * @param uncheckedStudent
     * @return
     * @throws JSONException
     */
    private static JSONObject changeUnCheckStudentsToJsonObject(UncheckedStudent uncheckedStudent)
            throws JSONException {

        if (null == uncheckedStudent) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mId", uncheckedStudent.getId());
        jsonObject.put("mName", uncheckedStudent.getName());
        jsonObject.put("mUnCheckedRate", uncheckedStudent.getUnCheckedRate());

        return jsonObject;

    }

    /**
     * 
     * @title: jsonStringToUncheckedStudent
     * @description: jsonString to List<UnCheckedStudent>
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:34:09
     * @param jsonString
     * @return
     * @throws JSONException
     */
    private static List<UncheckedStudent> jsonStringToUncheckedStudent(String jsonString)
            throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        int length = jsonArray.length();
        if (length < 1) {
            return null;
        }
        List<UncheckedStudent> list = new ArrayList<UncheckedStudent>();
        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            list.add(jsonObjectToUncheckedStudent(jsonObject));
        }
        return list;
    }

    private static UncheckedStudent jsonObjectToUncheckedStudent(JSONObject jsonObject)
            throws JSONException {
        String id = jsonObject.getString("mId");
        String name = jsonObject.getString("mName");
        double rate = jsonObject.getDouble("mUnCheckedRate");
        return new UncheckedStudent(id, name, rate);
    }
}
