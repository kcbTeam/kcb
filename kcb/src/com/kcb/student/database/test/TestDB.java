package com.kcb.student.database.test;

/**
 * 
 * @className: TestDB
 * @description:
 * @author:
 * @date: 2015-6-5 下午9:56:22
 */
public class TestDB {

    public final static String TABLE_NAME = "stu_test";

    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_TIME = "time";
    public final static String KEY_DATE = "date";
    public final static String KEY_HASTESTED = "has_tested";
    public final static String KEY_TEXT = "text";

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID + "text," + KEY_NAME + " text,"
            + KEY_TIME + " integer," + KEY_DATE + " text," + KEY_HASTESTED + " integer," + KEY_TEXT
            + " text)";
}
