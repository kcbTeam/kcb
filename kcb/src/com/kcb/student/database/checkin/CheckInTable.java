package com.kcb.student.database.checkin;

/**
 * 
 * @className: CheckInDB
 * @description:
 * @author:
 * @date: 2015-6-5 下午9:56:49
 */
public class CheckInTable {

    public static final String TABLE_NAME = "stu_checkin";

    public static final String COLUMN_SUCCESS_TIMES = "success_times";
    public static final String COLUMN_ALL_TIMES = "all_times";
    public static final String COLUMN_CHECKIN = "result";

    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_SUCCESS_TIMES + " integer," + COLUMN_ALL_TIMES + " integer," + COLUMN_CHECKIN
            + " text)";
}
