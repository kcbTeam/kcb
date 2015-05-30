package com.kcb.teacher.database.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.test.utils.TestJsonUtils;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;

public class TestDao {
    private TestSQLiteOpenHelper mTestSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TestDao(Context context) {
        mTestSQLiteOpenHelper = new TestSQLiteOpenHelper(context);
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
        String date = formatter.format(test.getDate());
        mSqLiteDatabase.execSQL(
                "INSERT INTO " + TestSQLiteOpenHelper.TABLE_NAME + " VALUES(null,?,?,?,?)",
                new String[] {test.getName(), String.valueOf(test.getTime()), date,
                        TestJsonUtils.changeQuestionsToString(test.getQuestions())});
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
                mSqLiteDatabase
                        .rawQuery("SELECT * FROM " + TestSQLiteOpenHelper.TABLE_NAME + " WHERE "
                                + TestSQLiteOpenHelper.KEY_NAME + "=?", new String[] {testName});
        if (cursor.moveToFirst()) {
            int mTime =
                    Integer.valueOf(cursor.getString(cursor
                            .getColumnIndex(TestSQLiteOpenHelper.KEY_TIME)));
            List<Question> mQuestions =
                    TestJsonUtils.jsonStringToQuesitonList(cursor.getString(cursor
                            .getColumnIndex(TestSQLiteOpenHelper.KEY_QUESTIONS)));
            @SuppressWarnings("deprecation")
            Date mDate =
                    formatter.parse(cursor.getString(cursor
                            .getColumnIndex(TestSQLiteOpenHelper.KEY_DATE)));
            test = new Test(testName, mQuestions, mTime);
            test.setDate(mDate);
        }
        return test;
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
                mSqLiteDatabase.query(TestSQLiteOpenHelper.TABLE_NAME, null, null, null, null,
                        null, null);
        List<Test> list = new ArrayList<Test>();
        if (cursor.moveToFirst()) {
            do {
                String mName =
                        cursor.getString(cursor.getColumnIndex(TestSQLiteOpenHelper.KEY_NAME));
                int mTime =
                        Integer.valueOf(cursor.getString(cursor
                                .getColumnIndex(TestSQLiteOpenHelper.KEY_TIME)));
                List<Question> mQuestions =
                        TestJsonUtils.jsonStringToQuesitonList(cursor.getString(cursor
                                .getColumnIndex(TestSQLiteOpenHelper.KEY_QUESTIONS)));
                @SuppressWarnings("deprecation")
                Date mDate =
                        formatter.parse(cursor.getString(cursor
                                .getColumnIndex(TestSQLiteOpenHelper.KEY_DATE)));
                Test test = new Test(mName, mQuestions, mTime);
                test.setDate(mDate);
                list.add(test);
            } while (cursor.moveToNext());
        }
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
        Cursor cursor =
                mSqLiteDatabase
                        .rawQuery("SELECT * FROM " + TestSQLiteOpenHelper.TABLE_NAME + " WHERE "
                                + TestSQLiteOpenHelper.KEY_NAME + "=?", new String[] {testName});
        if (cursor.moveToFirst()) {
            mSqLiteDatabase.delete(TestSQLiteOpenHelper.TABLE_NAME, " WHERE "
                    + TestSQLiteOpenHelper.KEY_NAME + "=?", new String[] {testName});
        }
    }
}
