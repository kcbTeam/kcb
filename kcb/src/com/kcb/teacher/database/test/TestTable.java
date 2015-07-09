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

    public final static String COLUMN_ID = "id"; // 测试的id
    public final static String COLUMN_NAME = "name"; // 测试的名称
    public final static String COLUMN_TIME = "time"; // 测试的时间，单位为妙
    public final static String COLUMN_DATE = "date"; // 测试开始的时间戳
    public final static String COLUMN_HASTESTED = "has_tested"; // 是否已经测试过了
    public final static String COLUMN_QUESTIONS = "questions"; // 测试中的所有题目

    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_ID + " text," + COLUMN_NAME + " text primary key," + COLUMN_TIME + " integer,"
            + COLUMN_DATE + " long," + COLUMN_HASTESTED + " boolean," + COLUMN_QUESTIONS + " text)";
}
