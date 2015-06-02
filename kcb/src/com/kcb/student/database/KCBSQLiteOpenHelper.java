package com.kcb.student.database;

import com.kcb.common.util.LogUtil;
import com.kcb.student.constance.DataBaseContract.FeedEntry;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class KCBSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "KCBApp";
    private static final String TEXT_TYPE = " text";
    private static final String NUMERIC_TYPE = " int";
    private static final String SQL_CREATE = "CREATE TABLE "
            + FeedEntry.TABLE_NAME
            + " ("
            // + FeedEntry.COLUMN_NAME_ENTRY_ID + "integer primary key autoincrement" + ","
            + FeedEntry.COLUMN_NAME_ENTRY_TESTNAME + TEXT_TYPE + ","
            + FeedEntry.COLUMN_NAME_TESTDATE + TEXT_TYPE + "," + FeedEntry.COLUMN_NAME_TESTTIME
            + NUMERIC_TYPE + "," + FeedEntry.COLUMN_NAME_QUESTION + TEXT_TYPE + ","+ FeedEntry.COLUMN_NAME_QUESTIONTYPE + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONA + TEXT_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONATYPE
            + NUMERIC_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONATF + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONATF1 + NUMERIC_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONB
            + TEXT_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONBTYPE + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONBTF + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONBTF1 + NUMERIC_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONC
            + TEXT_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONCTYPE + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONCTF + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONCTF1 + NUMERIC_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIOND
            + TEXT_TYPE + "," + FeedEntry.COLUMN_NAME_OPTIONDTYPE + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONDTF + NUMERIC_TYPE + ","
            + FeedEntry.COLUMN_NAME_OPTIONDTF1 + NUMERIC_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public KCBSQLiteOpenHelper(Context context, int vision) {
        super(context, DB_NAME, null, vision);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        LogUtil.i("fff", "cdscvsdvc");
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
