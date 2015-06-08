package com.kcb.student.database.test;

/**
 * 
 * @className: TestTable
 * @description:
 * @author:
 * @date: 2015-6-5 下午9:56:22
 */
public class TestTable {

    public final static String TABLE_NAME = "stu_test";

    public final static String COLUMN_ID = "id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_HASTESTED = "has_tested";
    public final static String COLUMN_TEST = "test";

    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_ID + " text," + COLUMN_NAME + " text," + COLUMN_TIME + " integer,"
            + COLUMN_DATE + " long," + COLUMN_HASTESTED + " boolean," + COLUMN_TEST + " text)";
}
