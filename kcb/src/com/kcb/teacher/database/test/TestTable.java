package com.kcb.teacher.database.test;

/**
 * 
 * @className: TestDB
 * @description:
 * @author: ZQJ
 * @date: 2015-6-5 下午10:12:54
 */
public class TestTable {

    public final static String TABLE_NAME = "tch_test";

    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_TIME = "time";
    public final static String KEY_DATE = "date";
    public final static String KEY_HASTESTED = "has_tested";
    public final static String KEY_TEXT = "text";

    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + KEY_ID + " text," + KEY_NAME + " text," + KEY_TIME + " integer," + KEY_DATE
            + " long," + KEY_HASTESTED + " boolean," + KEY_TEXT + " text)";
}
