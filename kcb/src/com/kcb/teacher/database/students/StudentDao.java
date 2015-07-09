package com.kcb.teacher.database.students;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.KSQLiteOpenHelper;

public class StudentDao {

    private KSQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public StudentDao(Context context) {
        mOpenHelper = new KSQLiteOpenHelper(context);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void add(Student student) {
        mDatabase.insert(StudentTable.TABLE_NAME, null, getStudentContentValues(student));
    }

    public void update(Student student) {}

    private ContentValues getStudentContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.COLUMN_STUID, student.getId());
        contentValues.put(StudentTable.COLUMN_NAME, student.getName());
        contentValues.put(StudentTable.COLUMN_CHECKINRATE, student.getCheckInRate());
        contentValues.put(StudentTable.COLUMN_CORRECTRATE, student.getCorrectRate());
        contentValues.put(StudentTable.COLUMN_ROW_DATA, student.toString());
        return contentValues;
    }

    public List<Student> getAll() {
        Cursor cursor =
                mDatabase.query(StudentTable.TABLE_NAME, null, null, null, null, null, null);
        List<Student> students = new ArrayList<Student>();
        if (null != cursor) {
            try {
                while (cursor.moveToNext()) {
                    try {
                        students.add(Student.fromjsonObject(new JSONObject(cursor.getString(cursor
                                .getColumnIndex(StudentTable.COLUMN_ROW_DATA)))));
                    } catch (JSONException e) {}
                }
            } catch (Exception e) {} finally {
                cursor.close();
            }
        }
        return students;
    }

    public void deleteAll() {
        mDatabase.delete(StudentTable.TABLE_NAME, null, null);
    }

    public void close() {
        mDatabase.close();
    }
}
