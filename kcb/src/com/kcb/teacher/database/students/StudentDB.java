package com.kcb.teacher.database.students;

public class StudentDB {
    public final static String TABLE_NAME = "studentcentre";
    public final static String KEY_NAME = "name";
    public final static String KEY_STUID = "studentId";
    public final static String KEY_CHECKINRATE = "checkinRate";
    public final static String KEY_CORRECTRATE = "correctRate";
    public final static String KEY_STUDENT = "student";
    public final static String CREATE_TABLE_STUDENT_CENTRE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " text,"
            + KEY_STUID + " text," + KEY_CHECKINRATE + " text," + KEY_CORRECTRATE + " text,"
            + KEY_STUDENT + " text)";
}
