package com.kcb.teacher.database.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kcb.teacher.database.CommonDB;

public class TestSQLiteOpenHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "test";
    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_TIME = "time";
    public final static String KEY_DATE = "date";
    public final static String KEY_QUESTIONS = "questions";

    public TestSQLiteOpenHelper(Context context) {
        super(context, CommonDB.DATABASE_NAME, null, CommonDB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " text," + KEY_TIME
                + " text," + KEY_DATE + " text," + KEY_QUESTIONS + " text)");
    }

    // TODO , when to invoked it;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE user ADD COLUMN other STRING");
    }
}
