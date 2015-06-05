package com.kcb.student.database.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.common.model.test.Test;
import com.kcb.student.database.KSQLiteOpenHelper;

public class TestDao {

    private KSQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public TestDao(Context context) {
        mSQLiteOpenHelper = new KSQLiteOpenHelper(context);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    @SuppressLint("SimpleDateFormat")
    public void add(Test test) {
        int hasTested = 0;
        if (test.isTested()) {
            hasTested = 1;
        }
        mSQLiteDatabase.beginTransaction();
        try {
            mSQLiteDatabase.execSQL("INSERT INTO " + TestDB.TABLE_NAME
                    + " VALUES(null,?,?,?,?,?,?)",
                    new String[] {test.getId(), test.getName(), String.valueOf(test.getTime()),
                            test.getDate().toString(), String.valueOf(hasTested),
                            test.toJsonObject().toString()});
            mSQLiteDatabase.setTransactionSuccessful();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
    }

    public List<Test> getAll() {
        Cursor cursor =
                mSQLiteDatabase.query(TestDB.TABLE_NAME, null, null, null, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                Test test;
                try {
                    test =
                            Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                    .getColumnIndex(TestDB.KEY_TEXT))));
                    tests.add(test);
                } catch (JSONException e) {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tests;
    }

    public void close() {
        mSQLiteDatabase.close();
    }
}
