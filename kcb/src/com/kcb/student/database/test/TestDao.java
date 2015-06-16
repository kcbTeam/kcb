package com.kcb.student.database.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.common.model.test.Test;
import com.kcb.student.database.KSQLiteOpenHelper;

public class TestDao {

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public TestDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void add(Test test) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TestTable.COLUMN_ID, test.getId());
        contentValues.put(TestTable.COLUMN_NAME, test.getName());
        contentValues.put(TestTable.COLUMN_TIME, test.getTime());
        contentValues.put(TestTable.COLUMN_DATE, test.getDate());
        contentValues.put(TestTable.COLUMN_HASTESTED, test.hasTested());
        contentValues.put(TestTable.COLUMN_ROW_DATA, test.toString());
        mDatabase.insert(TestTable.TABLE_NAME, null, contentValues);
    }

    /**
     * 获取测试时间已到的测试
     */
    public List<Test> getTerminatedTests() {
        List<Test> tests = getAll();
        for (Test test : tests) {
            if (test.getDate() + test.getTime() * 60 * 1000 > System.currentTimeMillis()) {
                tests.remove(test);
            }
        }
        return tests;
    }

    /**
     * 获得所有的测试，包括已经结束的和未结束的
     */
    public List<Test> getAll() {
        Cursor cursor = mDatabase.query(TestTable.TABLE_NAME, null, null, null, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        Test test =
                                Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                        .getColumnIndex(TestTable.COLUMN_ROW_DATA))));
                        tests.add(test);
                    } catch (JSONException e) {}
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return tests;
    }

    public void deleteAll() {
        mDatabase.delete(TestTable.TABLE_NAME, null, null);
    }

    public void close() {
        mDatabase.close();
    }
}
