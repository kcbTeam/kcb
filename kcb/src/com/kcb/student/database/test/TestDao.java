package com.kcb.student.database.test;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.student.database.StudentSQLiteOpenHelper;
import com.kcb.student.model.test.Test;

public class TestDao {

    private StudentSQLiteOpenHelper mStudentSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public TestDao(Context context) {
        mStudentSQLiteOpenHelper = new StudentSQLiteOpenHelper(context);
        mSqLiteDatabase = mStudentSQLiteOpenHelper.getWritableDatabase();
    }

    public void add(Test test) {
        mSqLiteDatabase.beginTransaction();
        try {
            mSqLiteDatabase.execSQL("insert into " + TestDB.TABLE_NAME + "values(null,?,?)",
                    new String[] {test.getName(), String.valueOf(test.getSum()),
                            test.getDate().toString()});
            mSqLiteDatabase.setTransactionSuccessful();
        } finally {
            mSqLiteDatabase.endTransaction();
        }
    }

    public List<Test> query() {
        Cursor cursor = mSqLiteDatabase.rawQuery("select from" + TestDB.TABLE_NAME, null);
        List<Test> list = new ArrayList<Test>();
        while (cursor.moveToNext()) {
            Test test = new Test();
            test.setName(cursor.getString(cursor.getColumnIndex(TestDB.TESTNAME)));
            test.setSum(cursor.getInt(cursor.getColumnIndex(TestDB.QUESTIONNUM)));
            test.setDate(cursor.getString(cursor.getColumnIndex(TestDB.DATE)));
            list.add(test);
        }
        cursor.close();
        return list;
    }

    public void close() {
        mSqLiteDatabase.close();
    }
}
