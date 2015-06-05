package com.kcb.student.database.checkin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.student.database.KSQLiteOpenHelper;
import com.kcb.student.model.checkin.CheckInResult;

public class CheckInDao {

    private KSQLiteOpenHelper mStudentSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public CheckInDao(Context context) {
        mStudentSQLiteOpenHelper = new KSQLiteOpenHelper(context);
        mSqLiteDatabase = mStudentSQLiteOpenHelper.getWritableDatabase();
    }

    public void add(CheckInResult check) {
        mSqLiteDatabase.beginTransaction();
        try {
//            mSqLiteDatabase.execSQL(
//                    "insert into" + CheckInDB.TABLE_NAME + "values(null,?)",
//                    new String[] {check.getDate().toString(),
//                            String.valueOf(check.getCheckInResult())});
            mSqLiteDatabase.setTransactionSuccessful();
        } finally {
            mSqLiteDatabase.endTransaction();
        }
    }

    public int getCheckIn() {
        int i = 0;
        Cursor cursor =
                mSqLiteDatabase.rawQuery("select from" + CheckInDB.TABLE_NAME + "where"
                        + CheckInDB.CHECKIN + "=?", new String[] {"1"});
        while (cursor.moveToNext()) {
            i = i + 1;
        }
        cursor.close();
        return i;
    }

    public int getSum() {
        int i = 0;
        Cursor cursor = mSqLiteDatabase.rawQuery("select from" + CheckInDB.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            i = i + 1;
        }
        cursor.close();
        return i;

    }

    public double getRate(int checkin, int sum) {
        if (sum == 0) {
            return 0;
        } else {
            return checkin / sum;
        }
    }

    public void close() {
        mSqLiteDatabase.close();
    }

}
