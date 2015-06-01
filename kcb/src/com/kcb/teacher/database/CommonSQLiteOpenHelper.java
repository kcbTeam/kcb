package com.kcb.teacher.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kcb.teacher.database.checkin.CheckInDB;
import com.kcb.teacher.database.test.TestDB;

public class CommonSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kcb_teacher.db";
    public static final int DATABASE_VERSION = 1;

    public CommonSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TestDB.CREATE_TABLE_TEST);
        db.execSQL(CheckInDB.CREATE_TABLE_CHECKIN);
        // TODO:create new table from here
    }

    // TODO , when to invoked it;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE user ADD COLUMN other STRING");
    }
}
