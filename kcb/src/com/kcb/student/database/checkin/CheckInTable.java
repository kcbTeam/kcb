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

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_HASCHECKED = "haschecked";

    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + COLUMN_DATE + " long," + COLUMN_HASCHECKED + " boolean)";
}
