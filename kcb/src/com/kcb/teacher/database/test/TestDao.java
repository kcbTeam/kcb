package com.kcb.teacher.database.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    // TODO close cursor
    private KSQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public TestDao(Context context) {
        mSQLiteOpenHelper = new KSQLiteOpenHelper(context);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    // TODO save boolean? use beginTransaction??
    @SuppressLint("SimpleDateFormat")
    public void add(Test test) throws SQLException, JSONException, IOException {
        int hasTested = 0;
        if (test.isTested()) {
            hasTested = 1;
        }
        mSQLiteDatabase.execSQL("INSERT INTO " + TestDB.TABLE_NAME + " VALUES(null,?,?,?,?,?,?)",
                new String[] {test.getId(), test.getName(), String.valueOf(test.getTime()),
                        test.getDate().toString(), String.valueOf(hasTested),
                        test.toJsonObject().toString()});
    }

    public Test get(String testName) throws JSONException, IOException, ParseException {
        Test test = null;
        Cursor cursor =
                mSQLiteDatabase.rawQuery("SELECT * FROM " + TestDB.TABLE_NAME + " WHERE "
                        + TestDB.KEY_NAME + "=?", new String[] {testName});
        if (cursor.moveToFirst()) {
            test =
                    Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                            .getColumnIndex(TestDB.KEY_TEXT))));
        }
        cursor.close();
        return test;
    }

    public boolean hasRecorded(String testName) {
        Cursor cursor =
                mSQLiteDatabase.rawQuery("SELECT * FROM " + TestDB.TABLE_NAME + " WHERE "
                        + TestDB.KEY_NAME + "=?", new String[] {testName});
        if (cursor.getCount() < 1) {
            return false;
        }
        return true;
    }

    public List<Test> getAll() throws JSONException, IOException, ParseException {
        Cursor cursor =
                mSQLiteDatabase.query(TestDB.TABLE_NAME, null, null, null, null, null, null);
        List<Test> list = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                Test test =
                        Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                .getColumnIndex(TestDB.KEY_TEXT))));
                list.add(test);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
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
