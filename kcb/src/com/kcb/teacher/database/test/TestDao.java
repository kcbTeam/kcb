package com.kcb.teacher.database.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public TestDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    @SuppressLint("SimpleDateFormat")
    public void add(Test test) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TestTable.KEY_ID, test.getId());
        contentValues.put(TestTable.KEY_NAME, test.getName());
        contentValues.put(TestTable.KEY_TIME, test.getTime());
        contentValues.put(TestTable.KEY_DATE, test.getDate());
        contentValues.put(TestTable.KEY_HASTESTED, test.hasTested());
        contentValues.put(TestTable.KEY_TEXT, test.toString());
        mDatabase.insert(TestTable.TABLE_NAME, null, contentValues);
    }

    public Test get(String testName) {
        Test test = null;
        Cursor cursor =
                mDatabase.rawQuery("SELECT * FROM " + TestTable.TABLE_NAME + " WHERE "
                        + TestTable.KEY_NAME + "=?", new String[] {testName});
        if (cursor.moveToFirst()) {
            try {
                test =
                        Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                .getColumnIndex(TestTable.KEY_TEXT))));
            } catch (JSONException e) {}
        }
        cursor.close();
        return test;
    }

    public boolean hasRecords(String testName) {
        Cursor cursor = mDatabase.query(TestTable.TABLE_NAME, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public List<Test> getAll() {
        Cursor cursor = mDatabase.query(TestTable.TABLE_NAME, null, null, null, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Test test =
                            Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                    .getColumnIndex(TestTable.KEY_TEXT))));
                    tests.add(test);
                } catch (JSONException e) {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tests;
    }

    public List<Test> getHasTested() {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, null, "where " + TestTable.KEY_HASTESTED
                        + "=?", new String[] {String.valueOf(true)}, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Test test =
                            Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                    .getColumnIndex(TestTable.KEY_TEXT))));
                    tests.add(test);
                } catch (JSONException e) {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tests;
    }

    public List<String> getAllTestName() {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, new String[] {TestTable.KEY_NAME}, null,
                        null, null, null, null);
        List<String> names = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(TestTable.KEY_NAME));
                names.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }

    public void delete(Test test) {
        mDatabase.delete(TestTable.TABLE_NAME, TestTable.KEY_NAME + "=?",
                new String[] {test.getName()});
    }

    public void close() {
        mDatabase.close();
    }
}
