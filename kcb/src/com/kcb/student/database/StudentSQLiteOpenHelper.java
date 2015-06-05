package com.kcb.student.database;

import com.kcb.student.database.checkin.CheckInDB;
import com.kcb.student.database.test.TestDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @className: SQLiteOpenHelper
 * @description: 
 * @author: Ding
 * @date: 2015年6月3日 下午3:38:41
 */
public class StudentSQLiteOpenHelper extends SQLiteOpenHelper{
    
    public static final String DATABASE_NAME="kcb_student.db";
    public static final int DATABASE_VERSION=1;
    
    public StudentSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TestDB.TABLE_STU_TEST);
        db.execSQL(CheckInDB.TABLE_STU_CHECKIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table"+ DATABASE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
