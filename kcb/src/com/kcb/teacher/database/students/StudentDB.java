package com.kcb.teacher.database.students;

public class StudentDB {
	public final static String TABLE_NAME = "studentcentre";
	public final static String KEY_NAME = "date";
	public final static String KEY_STUID = "rate";
	public final static String KEY_CHECKINRATE = "checkinRate";
	public final static String KEY_CORRECTRATE = "correctRate";
	public final static String CREATE_TABLE_CHECKIN = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_NAME
			+ " text,"
			+ KEY_STUID
			+ " text,"
			+ KEY_CHECKINRATE
			+ " text," + KEY_CORRECTRATE + " text)";
}
