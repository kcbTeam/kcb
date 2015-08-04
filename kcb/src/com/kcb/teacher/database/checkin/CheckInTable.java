package com.kcb.teacher.database.checkin;

public class CheckInTable {

    public final static String TABLE_NAME = "tch_checkin";

    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_RATE = "rate";
    public final static String COLUMN_ROW_DATA = "checkin";

    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_DATE + " text," + COLUMN_RATE + " double," + COLUMN_ROW_DATA + " text)";
}
