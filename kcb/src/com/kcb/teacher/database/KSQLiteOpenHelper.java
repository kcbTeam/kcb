package com.kcb.teacher.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kcb.teacher.database.checkin.CheckInDB;
import com.kcb.teacher.database.students.StudentDB;
import com.kcb.teacher.database.test.TestDB;

/**
 * 
 * @className: KSQLiteOpenHelper
 * @description:
 * @author: ZQJ
 * @date: 2015-6-6 下午9:55:11
 */
public class KSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kcb_teacher.db";
    public static final int DATABASE_VERSION = 1;

    public KSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TestDB.CREATE_TABLE);
        db.execSQL(CheckInDB.CREATE_TABLE);
        db.execSQL(StudentDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
