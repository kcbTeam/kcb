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

    /**
     * 是否有这个测试了，如果有，不能添加重名的测试；
     */
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

    /**
     * 获取为开始的测试，可以用来开始、编辑
     */
    public List<String> getUnStartTestName() {
        Cursor cursor =
                mDatabase.query(TestTable.TABLE_NAME, new String[] {TestTable.COLUMN_NAME}, null,
                        null, null, null, null);
        // Cursor cursor =
        // mDatabase.query(TestTable.TABLE_NAME, new String[] {TestTable.COLUMN_NAME},
        // TestTable.COLUMN_HASTESTED + "=?", new String[] {String.valueOf(false)},
        // null, null, null);
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

    /**
     * 编辑测试后，保存到数据库； 查看测试结果，保存到数据库； 每次开始一个测试后，删除此测试，之后，此测试只能通过查看测试结果页面刷新出来；
     */
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
        contentValues.put(TestTable.COLUMN_QUESTIONS, test.getQuestionsString());
        return contentValues;
    }
    
    private ContentValues getTestFromCursor(Cursor cursor) {
        Test test = new Test();
        test.setId(cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_ID)));
        test.setName(cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_NAME)));
        test.setTime(cursor.getInt(cursor.getColumnIndex(TestTable.COLUMN_TIME)));
        test.setDate(cursor.getLong(cursor.getColumnIndex(TestTable.COLUMN_DATE)));
        test.setHasTested(cursor.getInt(cursor.getColumnIndex(TestTable.COLUMN_HASTESTED)));
        test.setQuestions(cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_ID)));

        
        contentValues.put(TestTable.COLUMN_ID, test.getId());
        contentValues.put(TestTable.COLUMN_NAME, test.getName());
        contentValues.put(TestTable.COLUMN_TIME, test.getTime());
        contentValues.put(TestTable.COLUMN_DATE, test.getDate());
        contentValues.put(TestTable.COLUMN_HASTESTED, test.hasTested());
        contentValues.put(TestTable.COLUMN_QUESTIONS, test.getQuestionsString());
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
                                        .getColumnIndex(TestTable.COLUMN_QUESTIONS)))));
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
                                        .getColumnIndex(TestTable.COLUMN_QUESTIONS)))));
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
                                        .getColumnIndex(TestTable.COLUMN_QUESTIONS)))));
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
