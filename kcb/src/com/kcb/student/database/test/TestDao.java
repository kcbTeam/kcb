package com.kcb.student.database.test;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 开始测试，获取到测试后需要添加到数据库； 查看测试结果，获取到测试后需要添加到数据库；
     */
    public void add(Test test) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TestTable.COLUMN_ID, test.getId());
        contentValues.put(TestTable.COLUMN_NAME, test.getName());
        contentValues.put(TestTable.COLUMN_TIME, test.getTime());
        contentValues.put(TestTable.COLUMN_DATE, test.getDate());
        contentValues.put(TestTable.COLUMN_HASTESTED, test.hasTested());
        contentValues.put(TestTable.COLUMN_QUESTIONS, test.getQuestionsString());
        mDatabase.insert(TestTable.TABLE_NAME, null, contentValues);
    }

    private Test getTestFromCursor(Cursor cursor) {
        Test test = new Test();
        test.setId(cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_ID)));
        test.setName(cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_NAME)));
        test.setTime(cursor.getInt(cursor.getColumnIndex(TestTable.COLUMN_TIME)));
        test.setDate(cursor.getLong(cursor.getColumnIndex(TestTable.COLUMN_DATE)));
        int hasTested = cursor.getInt(cursor.getColumnIndex(TestTable.COLUMN_HASTESTED));
        test.setHasTested(hasTested == 1 ? true : false);
        test.setQuestions(cursor.getString(cursor.getColumnIndex(TestTable.COLUMN_QUESTIONS)));
        return test;
    }

    /**
     * 获得所有的测试
     */
    public List<Test> getAll() {
        Cursor cursor = mDatabase.query(TestTable.TABLE_NAME, null, null, null, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    Test test = getTestFromCursor(cursor);
                    tests.add(test);
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
