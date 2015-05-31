package com.kcb.teacher.database.test;

public class TestDB {
    public final static String TABLE_NAME = "test";
    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_TIME = "time";
    public final static String KEY_DATE = "date";
    public final static String KEY_QUESTIONS = "questions";
    public final static String CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " text," + KEY_TIME
            + " text," + KEY_DATE + " text," + KEY_QUESTIONS + " text)";
}
