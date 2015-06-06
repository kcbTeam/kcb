package com.kcb.teacher.database.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.common.model.test.Test;
import com.kcb.teacher.database.KSQLiteOpenHelper;

/**
 * 
 * @className: TestDao
 * @description:
 * @author:
 * @date: 2015-6-5 下午10:13:03
 */
public class TestDao {

    private KSQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public TestDao(Context context) {
        mSQLiteOpenHelper = new KSQLiteOpenHelper(context);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    // TODO use beginTransaction??
    @SuppressLint("SimpleDateFormat")
    public void add(Test test) {
        delete(test.getName());
        mSQLiteDatabase.execSQL("INSERT INTO " + TestDB.TABLE_NAME + " VALUES(null,?,?,?,?,?,?)",
                new String[] {test.getId(), test.getName(), String.valueOf(test.getTime()),
                        test.getDateString().toString(), String.valueOf(test.hasTested()),
                        test.toJsonObject().toString()});
    }

    public Test get(String testName) {
        Test test = null;
        Cursor cursor =
                mSQLiteDatabase.rawQuery("SELECT * FROM " + TestDB.TABLE_NAME + " WHERE "
                        + TestDB.KEY_NAME + "=?", new String[] {testName});
        if (cursor.moveToFirst()) {
            try {
                test =
                        Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                .getColumnIndex(TestDB.KEY_TEXT))));
            } catch (JSONException e) {}
        }
        cursor.close();
        return test;
    }

    public boolean hasRecorded(String testName) {
        Cursor cursor =
                mSQLiteDatabase.rawQuery("SELECT * FROM " + TestDB.TABLE_NAME + " WHERE "
                        + TestDB.KEY_NAME + "=?", new String[] {testName});
        if (cursor.getCount() < 1) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Test> getAll() {
        Cursor cursor =
                mSQLiteDatabase.query(TestDB.TABLE_NAME, null, null, null, null, null, null);
        List<Test> list = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Test test =
                            Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                    .getColumnIndex(TestDB.KEY_TEXT))));
                    list.add(test);
                } catch (JSONException e) {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Test> getHasTested() {
        Cursor cursor =
                mSQLiteDatabase.query(TestDB.TABLE_NAME, null, "where " + TestDB.KEY_HASTESTED
                        + "=?", new String[] {String.valueOf(true)}, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Test test =
                            Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                    .getColumnIndex(TestDB.KEY_TEXT))));
                    tests.add(test);
                } catch (JSONException e) {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tests;
    }

    public List<String> getAllTestName() {
        Cursor cursor =
                mSQLiteDatabase.query(TestDB.TABLE_NAME, new String[] {TestDB.KEY_NAME}, null,
                        null, null, null, null);
        List<String> names = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(TestDB.KEY_NAME));
                names.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }

    public void delete(String testName) {
        mSQLiteDatabase.delete(TestDB.TABLE_NAME, TestDB.KEY_NAME + "=?", new String[] {testName});
    }

    public void deleteAll() {
        mSQLiteDatabase.execSQL("DELETE FROM " + TestDB.TABLE_NAME);
    }

    public void close() {
        mSQLiteDatabase.close();
    }
}
