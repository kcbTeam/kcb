package com.kcb.student.constance;

import android.provider.BaseColumns;

public final class DataBaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DataBaseContract() {}
 
    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Test";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_ENTRY_TESTNAME = "testname";
        public static final String COLUMN_NAME_TESTDATE = "testdata";
        public static final String COLUMN_NAME_TESTTIME = "testtime";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_QUESTIONTYPE = "questiontype";
        public static final String COLUMN_NAME_OPTIONA = "optiona";
        public static final String COLUMN_NAME_OPTIONATYPE = "optionatype";
        public static final String COLUMN_NAME_OPTIONATF = "optionatf";
        public static final String COLUMN_NAME_OPTIONATF1 = "optionatf1";
        public static final String COLUMN_NAME_OPTIONB = "optionb";
        public static final String COLUMN_NAME_OPTIONBTYPE = "optionbtype";
        public static final String COLUMN_NAME_OPTIONBTF = "optionbtf";
        public static final String COLUMN_NAME_OPTIONBTF1 = "optionbtf1";
        public static final String COLUMN_NAME_OPTIONC = "optionc";
        public static final String COLUMN_NAME_OPTIONCTYPE = "optionctype";
        public static final String COLUMN_NAME_OPTIONCTF = "optionctf";
        public static final String COLUMN_NAME_OPTIONCTF1 = "optionctf1";
        public static final String COLUMN_NAME_OPTIOND = "optiond";
        public static final String COLUMN_NAME_OPTIONDTYPE = "optiondtype";
        public static final String COLUMN_NAME_OPTIONDTF = "optiondtf";
        public static final String COLUMN_NAME_OPTIONDTF1 = "optiondtf1";

    }
}
