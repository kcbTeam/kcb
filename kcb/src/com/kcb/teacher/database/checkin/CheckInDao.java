package com.kcb.teacher.database.checkin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.KSQLiteOpenHelper;
import com.kcb.teacher.model.checkin.CheckInResult;

public class CheckInDao {

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public CheckInDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
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
        mDatabase.execSQL(
                "INSERT INTO " + CheckInDB.TABLE_NAME + " VALUES(null,?,?,?)",
                new String[] {checkInResult.getDate().toString(),
                        String.valueOf(checkInResult.getRate()),
                        checkInResult.toJsonObject().toString()});
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
        Cursor cursor =
                mDatabase.rawQuery("SELECT * FROM " + CheckInDB.TABLE_NAME + " WHERE "
                        + CheckInDB.KEY_DATE + "=?", new String[] {date.toString()});
        List<CheckInResult> checkInResultsList = new ArrayList<CheckInResult>();
        if (cursor.moveToFirst()) {
            do {
                checkInResultsList.add(CheckInResult.fromJsonObject(new JSONObject(cursor
                        .getString(cursor.getColumnIndex(CheckInDB.KEY_CHECKINRESULT_CONTENT)))));
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
        Cursor cursor = mDatabase.query(CheckInDB.TABLE_NAME, null, null, null, null, null, null);
        List<CheckInResult> checkInResultsList = new ArrayList<CheckInResult>();
        if (cursor.moveToFirst()) {
            do {
                checkInResultsList.add(CheckInResult.fromJsonObject(new JSONObject(cursor
                        .getString(cursor.getColumnIndex(CheckInDB.KEY_CHECKINRESULT_CONTENT)))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checkInResultsList;
    }


    public void deleteCheckInResultsByDate(Date date) {
        mDatabase.delete(CheckInDB.TABLE_NAME, CheckInDB.KEY_DATE + "=?",
                new String[] {date.toString()});
    }

    /**
     * 
     * @title: deleteAllCheckInResults
     * @description: delete All records
     * @author: ZQJ
     * @date: 2015年5月31日 下午8:52:58
     */
    public void deleteAllCheckInResults() {
        mDatabase.execSQL("DELETE FROM " + CheckInDB.TABLE_NAME);
    }

    public void close() {
        mDatabase.close();
    }
}
