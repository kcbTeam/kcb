package com.kcb.student.database.checkin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.student.database.KSQLiteOpenHelper;
import com.kcb.student.model.checkin.CheckInResult;

/**
 * 
 * @className: CheckInDao
 * @description:
 * @author:
 * @date: 2015-6-5 下午10:00:05
 */
public class CheckInDao {

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public CheckInDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void add(CheckInResult result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CheckInTable.COLUMN_SUCCESS_TIMES, result.getSuccessTimes());
        contentValues.put(CheckInTable.COLUMN_ALL_TIMES, result.getAllTimes());
        contentValues.put(CheckInTable.COLUMN_CHECKIN, result.toString());
        mDatabase.insert(CheckInTable.TABLE_NAME, null, contentValues);
    }

    public int getSuccessTimes() {
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME,
                        new String[] {CheckInTable.COLUMN_SUCCESS_TIMES}, null, null, null, null,
                        null);
        int times = cursor.getInt(cursor.getColumnIndex(CheckInTable.COLUMN_SUCCESS_TIMES));
        cursor.close();
        return times;
    }

    public int getAllTimes() {
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME,
                        new String[] {CheckInTable.COLUMN_ALL_TIMES}, null, null, null, null, null);
        int times = cursor.getInt(cursor.getColumnIndex(CheckInTable.COLUMN_ALL_TIMES));
        cursor.close();
        return times;
    }

    public void close() {
        mDatabase.close();
    }
}
