package com.kcb.teacher.database.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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

    public boolean hasTest(String testName) {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, null, TestTable.COLUMN_NAME + "=?",
                        new String[] {testName}, null, null, null);
        int count = 0;
        if (null != cursor) {
            try {
                count = cursor.getCount();
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return count > 0;
    }

    public List<String> getAllTestName() {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, new String[] {TestTable.COLUMN_NAME}, null,
                        null, null, null, null);
        List<String> names = new ArrayList<String>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_NAME));
                    names.add(name);
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return names;
    }

    public void add(Test test) {
        mDatabase.insert(TestTable.TABLE_NAME, null, getTestContentValues(test));
    }

    public void update(Test test) {
        mDatabase.update(TestTable.TABLE_NAME, getTestContentValues(test), TestTable.COLUMN_NAME
                + "=?", new String[] {test.getName()});
    }

    private ContentValues getTestContentValues(Test test) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TestTable.COLUMN_ID, test.getId());
        contentValues.put(TestTable.COLUMN_NAME, test.getName());
        contentValues.put(TestTable.COLUMN_TIME, test.getTime());
        contentValues.put(TestTable.COLUMN_DATE, test.getDate());
        contentValues.put(TestTable.COLUMN_HASTESTED, test.hasTested());
        contentValues.put(TestTable.COLUMN_ROW_DATA, test.toString());
        return contentValues;
    }

    public Test getByName(String testName) {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, null, TestTable.COLUMN_NAME + "=?",
                        new String[] {testName}, null, null, null);
        Test test = null;
        if (null != cursor) {
            try {
                if (cursor.moveToFirst()) {
                    try {
                        test =
                                Test.fromJsonObject(new JSONObject(new String(cursor.getBlob(cursor
                                        .getColumnIndex(TestTable.COLUMN_ROW_DATA)))));
                    } catch (JSONException e) {}
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return test;
    }

    public List<Test> getHasTested() {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, null, TestTable.COLUMN_HASTESTED + "=?",
                        new String[] {String.valueOf(true)}, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        Test test =
                                Test.fromJsonObject(new JSONObject(new String(cursor.getBlob(cursor
                                        .getColumnIndex(TestTable.COLUMN_ROW_DATA)))));
                        tests.add(test);
                    } catch (JSONException e) {}
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return tests;
    }

    public List<Test> getAll() {
        Cursor cursor = mDatabase.query(TestTable.TABLE_NAME, null, null, null, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        Test test =
                                Test.fromJsonObject(new JSONObject(new String(cursor.getBlob(cursor
                                        .getColumnIndex(TestTable.COLUMN_ROW_DATA)))));
                        tests.add(test);
                    } catch (JSONException e) {}
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return tests;
    }

    public void delete(Test test) {
        mDatabase.delete(TestTable.TABLE_NAME, TestTable.COLUMN_NAME + "=?",
                new String[] {test.getName()});
    }

    public void deleteAll() {
        mDatabase.delete(TestTable.TABLE_NAME, null, null);
    }

    public void close() {
        mDatabase.close();
    }
}
