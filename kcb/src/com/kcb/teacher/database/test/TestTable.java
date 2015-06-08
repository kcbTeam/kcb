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

    public final static String COLUMN_ID = "id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_HASTESTED = "has_tested";
    public final static String COLUMN_ROW_DATA = "text";

    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_ID + " text," + COLUMN_NAME + " text," + COLUMN_TIME + " integer,"
            + COLUMN_DATE + " long," + COLUMN_HASTESTED + " boolean," + COLUMN_ROW_DATA + " text)";
}
