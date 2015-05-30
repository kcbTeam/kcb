package com.kcb.teacher.database.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "db_user.db";
	private static final int DATABASE_VERSION = 1;

	public final static String TABLE_NAME = "profile";
	public final static String FIELD_NICK = "nick";
	public final static String FIELD_SIGN = "sign";
	public final static String FIELD_GENDER = "gender";
	public final static String FIELD_PASSWORD = "password";

	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ="CREATE TABLE IF NOT EXISTS profile (_id INTEGER PRIMARY KEY AUTOINCREMENT, nick VARCHAR[30], sign VARCHAR[60] ,gender VARCHAR[10],password VARCHAR[30])"
	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL("CREATE TABLE IF NOT EXISTS profile"
		// +
		// "(_id INTEGER PRIMARY KEY AUTOINCREMENT, nick VARCHAR[30], sign VARCHAR[60] ,gender VARCHAR[10],password VARCHAR[30])");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_NICK
				+ " text," + FIELD_SIGN + " text," + FIELD_GENDER + " text,"
				+ FIELD_PASSWORD + " text)");
	}

	// TODO , when to invoked it;
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE user ADD COLUMN other STRING");
	}
}
