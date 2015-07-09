package com.kcb.student.database.checkin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.student.database.KSQLiteOpenHelper;

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
        contentValues.put(CheckInTable.COLUMN_DATE, result.getDate());
        contentValues.put(CheckInTable.COLUMN_HASCHECKED, result.getHasChecked());
        mDatabase.insert(CheckInTable.TABLE_NAME, null, contentValues);
    }

    /**
     * 查看成功签到的次数
     */
    public int getSuccessTimes() {
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME, null, CheckInTable.COLUMN_HASCHECKED
                        + "=?", new String[] {String.valueOf(true)}, null, null, null);
        int times = 0;
        if (null != cursor) {
            try {
                times = cursor.getCount();
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return times;
    }

    /**
     * 获取所有的签到次数
     */
    public int getAllTimes() {
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME, null, null, null, null, null, null);
        int times = 0;
        if (null != cursor) {
            try {
                times = cursor.getCount();
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return times;
    }

    /**
     * 获取最近的一次签到时间；
     * 将时间由大到小倒序后，取出第一个值就是最近的时间；
     */
    public long getLeastDate() {
        long date = 0;
        Cursor cursor =
                mDatabase.query(CheckInTable.TABLE_NAME, new String[] {CheckInTable.COLUMN_DATE},
                        null, null, null, null, "DESC");
        if (null != cursor) {
            try {
                cursor.moveToFirst();
                date = cursor.getLong(cursor.getColumnIndex(CheckInTable.COLUMN_DATE));
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return date;
    }

    public void deleteAll() {
        mDatabase.delete(CheckInTable.TABLE_NAME, null, null);
    }

    public void close() {
        mDatabase.close();
    }
}
