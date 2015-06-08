package com.kcb.teacher.database.students;

public class StudentTable {

    public final static String TABLE_NAME = "tch_student";

    public final static String COLUMN_STUID = "id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_CHECKINRATE = "checkinRate";
    public final static String COLUMN_CORRECTRATE = "correctRate";
    public final static String COLUMN_ROW_DATA = "student";

    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_NAME + " text," + COLUMN_STUID + " text," + COLUMN_CHECKINRATE + " double,"
            + COLUMN_CORRECTRATE + " double," + COLUMN_ROW_DATA + " text)";
}
