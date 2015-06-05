package com.kcb.student.database.checkin;

public class CheckInDB {

    public static final String TABLE_NAME = "check in";
    public static final String DATE = "date";
    public static final String CHECKIN = "checkinresult";
    public static final String TABLE_STU_CHECKIN = "create table if not exists " + TABLE_NAME + "("
            + DATE + " text primary key," + CHECKIN + " integer)";

}
