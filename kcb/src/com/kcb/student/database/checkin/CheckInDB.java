package com.kcb.student.database.checkin;

/**
 * 
 * @className: CheckInDB
 * @description:
 * @author:
 * @date: 2015-6-5 下午9:56:49
 */
public class CheckInDB {

    public static final String TABLE_NAME = "check_in";

    public static final String DATE = "date";
    public static final String HASCHECKED = "haschecked";

    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + DATE + " long," + HASCHECKED + " boolean)";
}
