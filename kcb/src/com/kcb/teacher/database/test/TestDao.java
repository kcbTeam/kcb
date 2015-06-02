package com.kcb.teacher.database.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.CommonSQLiteOpenHelper;
import com.kcb.teacher.model.test.Test;

public class TestDao {
    private CommonSQLiteOpenHelper mTestSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TestDao(Context context) {
        mTestSQLiteOpenHelper = new CommonSQLiteOpenHelper(context);
        mSqLiteDatabase = mTestSQLiteOpenHelper.getWritableDatabase();
    }

    /**
     * 
     * @title: add
     * @description: ADD a record to db
     * @author: ZQJ
     * @date: 2015年5月30日 下午8:44:29
     * @param test
     * @throws SQLException
     * @throws JSONException
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    public void add(Test test) throws SQLException, JSONException, IOException {
        if (null != test) {
            deleteTestByName(test.getName());
        }
        String hasTested = "0";
        if (test.isTested()) {
            hasTested = "1";
        }
        mSqLiteDatabase.execSQL("INSERT INTO " + TestDB.TABLE_NAME + " VALUES(null,?,?,?,?,?,?)",
                new String[] {test.getId(), test.getName(), String.valueOf(test.getTime()),
                        test.getDate().toString(), hasTested, test.toJsonObject().toString()});
    }

    /**
     * 
     * @title: getTestByName
     * @description: GET a test by name for db
     * @author: ZQJ
     * @date: 2015年5月30日 下午8:44:49
     * @param testName
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws ParseException
     */
    public Test getTestByName(String testName) throws JSONException, IOException, ParseException {
        Test test = null;
        Cursor cursor =
                mSqLiteDatabase.rawQuery("SELECT * FROM " + TestDB.TABLE_NAME + " WHERE "
                        + TestDB.KEY_NAME + "=?", new String[] {testName});
        if (cursor.moveToFirst()) {
            test =
                    Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                            .getColumnIndex(TestDB.KEY_TEST_CONTENT))));
        }
        cursor.close();
        return test;
    }

    /**
     * 
     * @title: hasRecorded
     * @description: check whether the testName is saved
     * @author: ZQJ
     * @date: 2015年6月2日 下午8:26:51
     * @param testName
     * @return
     */
    public boolean hasRecorded(String testName) {
        Cursor cursor =
                mSqLiteDatabase.rawQuery("SELECT * FROM " + TestDB.TABLE_NAME + " WHERE "
                        + TestDB.KEY_NAME + "=?", new String[] {testName});
        if (cursor.getCount() < 1) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @title: getAllRecord
     * @description: GET all tests from db
     * @author: ZQJ
     * @date: 2015年5月30日 下午8:53:54
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws ParseException
     */
    public List<Test> getAllRecord() throws JSONException, IOException, ParseException {
        Cursor cursor =
                mSqLiteDatabase.query(TestDB.TABLE_NAME, null, null, null, null, null, null);
        List<Test> list = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                Test test =
                        Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                .getColumnIndex(TestDB.KEY_TEST_CONTENT))));
                list.add(test);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 
     * @title: deleteTestByName
     * @description: DELETE test by name
     * @author: ZQJ
     * @date: 2015年5月30日 下午8:58:39
     * @param testName
     */
    public void deleteTestByName(String testName) {
        mSqLiteDatabase.delete(TestDB.TABLE_NAME, TestDB.KEY_NAME + "=?", new String[] {testName});
    }

    public void deleteAllRecord() {
        mSqLiteDatabase.execSQL("DELETE FROM " + TestDB.TABLE_NAME);
    }

    public void close() {
        mSqLiteDatabase.close();
    }
}
