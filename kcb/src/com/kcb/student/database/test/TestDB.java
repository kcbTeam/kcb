package com.kcb.student.database.test;

public class TestDB {

    public static final String TABLE_NAME = "test";
    public static final String TESTNAME = "testname";
    public static final String QUESTIONNUM = "questionnumber";
    public static final String DATE = "date";
    public static final String TABLE_STU_TEST = "create table " + TABLE_NAME + "(" + TESTNAME
            + " text primary key, " + QUESTIONNUM + " integer," + DATE + " text)";

}
