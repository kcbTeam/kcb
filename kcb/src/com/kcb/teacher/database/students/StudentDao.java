package com.kcb.teacher.database.students;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.KSQLiteOpenHelper;
import com.kcb.teacher.model.stucentre.Student;

public class StudentDao {
    private KSQLiteOpenHelper mCommonSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public StudentDao(Context context) {
        mCommonSQLiteOpenHelper = new KSQLiteOpenHelper(context);
        mSqLiteDatabase = mCommonSQLiteOpenHelper.getWritableDatabase();
    }

    /**
     * 
     * @param student
     * @throws SQLException
     * @throws JSONException
     */
    public void addStudentRecord(Student student) throws SQLException, JSONException {
        mSqLiteDatabase
                .execSQL(
                        "INSERT INTO " + StudentDB.TABLE_NAME + " VALUES(null,?,?,?,?,?)",
                        new String[] {student.getName(), student.getId(),
                                String.valueOf(student.getCheckInRate()),
                                String.valueOf(student.getCorrectRate()),
                                student.toJsonObject().toString()});
    }

    /**
     * 
     * @return
     * @throws ParseException
     * @throws JSONException
     */
    public List<Student> getAllStuCentre() throws ParseException, JSONException {
        Cursor cursor =
                mSqLiteDatabase.query(StudentDB.TABLE_NAME, null, null, null, null, null, null);
        List<Student> stuCentreList = new ArrayList<Student>();
        if (cursor.moveToFirst()) {
            do {
                stuCentreList.add(Student.fromjsonObject(new JSONObject(cursor.getString(cursor
                        .getColumnIndex(StudentDB.KEY_STUDENT)))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return stuCentreList;
    }

    public void deleteStuCentreById(String stuId) {
        mSqLiteDatabase.delete(StudentDB.TABLE_NAME, StudentDB.KEY_STUID + "=?",
                new String[] {stuId});
    }

    public void deleteAllStuCentre() {
        mSqLiteDatabase.execSQL("DELETE FROM " + StudentDB.TABLE_NAME);
    }

    public void close() {
        mSqLiteDatabase.close();
    }
}
