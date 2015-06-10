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

    public List<Test> getAll() {
        Cursor cursor = mDatabase.query(TestTable.TABLE_NAME, null, null, null, null, null, null);
        List<Test> tests = new ArrayList<Test>();
        if (null != cursor) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        try {
                            Test test =
                                    Test.fromJsonObject(new JSONObject(cursor.getString(cursor
                                            .getColumnIndex(TestTable.COLUMN_ROW_DATA))));
                            tests.add(test);
                        } catch (JSONException e) {}
                    } while (cursor.moveToNext());
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
