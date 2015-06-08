package com.kcb.student.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kcb.student.database.checkin.CheckInTable;
import com.kcb.student.database.test.TestTable;

/**
 * @className: SQLiteOpenHelper
 * @description:
 * @author: Ding
 * @date: 2015年6月3日 下午3:38:41
 */
public class KSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kcb_student.db";
    private static final int DATABASE_VERSION = 1;

    public KSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CheckInTable.CREATE_TABLE);
        db.execSQL(TestTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
