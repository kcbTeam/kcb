package com.kcb.teacher.database.checkin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.KSQLiteOpenHelper;

public class CheckInDao {

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public CheckInDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void add(CheckInResult result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CheckInTable.COLUMN_DATE, result.getDate());
        contentValues.put(CheckInTable.COLUMN_RATE, result.getRate());
        contentValues.put(CheckInTable.COLUMN_ROW_DATA, result.toString());
        mDatabase.insert(CheckInTable.TABLE_NAME, null, contentValues);
    }

    public void update(CheckInResult result) {
        ContentValues values = new ContentValues();
        values.put(CheckInTable.COLUMN_DATE, result.getDate());
        values.put(CheckInTable.COLUMN_RATE, result.getRate());
        values.put(CheckInTable.COLUMN_ROW_DATA, result.toString());
        mDatabase.update(CheckInTable.TABLE_NAME, values, CheckInTable.COLUMN_DATE + "=?",
                new String[] {String.valueOf(result.getDate())});
    }

    public List<CheckInResult> getAll() {
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME, null, null, null, null, null, null);
        List<CheckInResult> results = new ArrayList<CheckInResult>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        results.add(CheckInResult.fromJsonObject(new JSONObject(cursor
                                .getString(cursor.getColumnIndex(CheckInTable.COLUMN_ROW_DATA)))));
                    } catch (JSONException e) {}
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return results;
    }

    public void deleteAll() {
        mDatabase.delete(CheckInTable.TABLE_NAME, null, null);
    }

    public void close() {
        mDatabase.close();
    }
}
