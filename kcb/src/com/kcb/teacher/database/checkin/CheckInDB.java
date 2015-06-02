package com.kcb.teacher.database.checkin;

public class CheckInDB {
    public final static String TABLE_NAME = "checkin";
    public final static String KEY_DATE = "date";
    public final static String KEY_RATE = "rate";
    public final static String KEY_CHECKINRESULT_CONTENT = "checkinresult";
    public final static String CREATE_TABLE_CHECKIN = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE + " text," + KEY_RATE
            + " text," + KEY_CHECKINRESULT_CONTENT + " text)";
}
