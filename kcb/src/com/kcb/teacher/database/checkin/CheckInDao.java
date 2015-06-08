package com.kcb.teacher.database.checkin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.KSQLiteOpenHelper;
import com.kcb.teacher.model.checkin.CheckInResult;

public class CheckInDao {

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public CheckInDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void add(CheckInResult result) throws SQLException, JSONException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CheckInTable.COLUMN_DATE, result.getDate());
        contentValues.put(CheckInTable.COLUMN_RATE, result.getRate());
        contentValues.put(CheckInTable.COLUMN_ROW_DATA, result.toString());
        mDatabase.insert(CheckInTable.TABLE_NAME, null, contentValues);
    }

    public List<CheckInResult> getAll() {
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME, null, null, null, null, null, null);
        List<CheckInResult> results = new ArrayList<CheckInResult>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    results.add(CheckInResult.fromJsonObject(new JSONObject(cursor.getString(cursor
                            .getColumnIndex(CheckInTable.COLUMN_ROW_DATA)))));
                } catch (JSONException e) {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }

    public void close() {
        mDatabase.close();
    }
}
