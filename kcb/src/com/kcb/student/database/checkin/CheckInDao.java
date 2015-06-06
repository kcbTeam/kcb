package com.kcb.student.database.checkin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.student.database.KSQLiteOpenHelper;
import com.kcb.student.model.checkin.CheckInResultDetail;

/**
 * 
 * @className: CheckInDao
 * @description:
 * @author:
 * @date: 2015-6-5 下午10:00:05
 */
public class CheckInDao {

    private KSQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public CheckInDao(Context context) {
        mSQLiteOpenHelper = new KSQLiteOpenHelper(context);
        mSqLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    public void add(CheckInResultDetail result) {
        mSqLiteDatabase.beginTransaction();
        try {
            mSqLiteDatabase.execSQL(
                    "insert into " + CheckInDB.TABLE_NAME + " values(null,?)",
                    new String[] {String.valueOf(result.getDate()),
                            String.valueOf(result.hasChecked())});
            mSqLiteDatabase.setTransactionSuccessful();
        } finally {
            mSqLiteDatabase.endTransaction();
        }
    }

    // TODO
    public int getSuccessTimes() {
        int i = 0;
        Cursor cursor =
                mSqLiteDatabase.rawQuery("select * from " + CheckInDB.TABLE_NAME + " where "
                        + CheckInDB.HASCHECKED + " =? ", new String[] {"true"});
        while (cursor.moveToNext()) {
            i = i + 1;
        }
        cursor.close();
        return i;
    }

    // TODO 获取表的行数
    public int getAllTimes() {
        int i = 0;
        Cursor cursor = mSqLiteDatabase.rawQuery("select * from " + CheckInDB.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            i = i + 1;
        }
        return cursor.getCount();
    }

    public void close() {
        mSqLiteDatabase.close();
    }
}
