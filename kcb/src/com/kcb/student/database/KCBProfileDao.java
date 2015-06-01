package com.kcb.student.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.student.constance.DataBaseContract.FeedEntry;
import com.kcb.student.util.IntToBooleans;
import com.kcb.teacher.model.test.Test;

public class KCBProfileDao {
    private KCBSQLiteOpenHelper mDbhelper;
    private SQLiteDatabase mSqLiteDatabase;
    private ContentValues mContentValues;

    public KCBProfileDao(Context context, int vision) {
        mDbhelper = new KCBSQLiteOpenHelper(context, vision);
    }


    public void getDataDb() {
        mSqLiteDatabase = mDbhelper.getWritableDatabase();
    }

    public void insert(Test profile1, int position) {

        mContentValues = new ContentValues();
        mContentValues.put(FeedEntry.COLUMN_NAME_ENTRY_TESTNAME, profile1.getName());
        mContentValues.put(FeedEntry.COLUMN_NAME_TESTTIME, profile1.getmTime());
        mContentValues.put(FeedEntry.COLUMN_NAME_QUESTION, profile1.getQuestion(position)
                .getTitle().getText());
        mContentValues.put(FeedEntry.COLUMN_NAME_QUESTIONTYPE, profile1.getQuestion(position)
                .getTitle().isText());

        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONA, profile1.getQuestion(position)
                .getChoiceA().getText());
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONATYPE, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceA().isText()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONATF, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceA().isRight()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONATF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceA().isRight1()));

        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONB, profile1.getQuestion(position)
                .getChoiceB().getText());
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONBTYPE, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceB().isText()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONBTF, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceB().isRight()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONBTF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceB().isRight1()));

        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONC, profile1.getQuestion(position)
                .getChoiceC().getText());
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONCTYPE, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceC().isText()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONCTF, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceC().isRight()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONCTF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceC().isRight1()));

        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIOND, profile1.getQuestion(position)
                .getChoiceD().getText());
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONDTYPE, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceD().isText()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONDTF, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceD().isRight()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONDTF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceD().isRight1()));
        
        mSqLiteDatabase.insert(FeedEntry.TABLE_NAME, null, mContentValues);

    }

    public void upDate(Test profile1, int position) {
        mContentValues = new ContentValues();
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONATF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceA().isRight1()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONBTF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceB().isRight1()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONCTF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceC().isRight1()));
        mContentValues.put(FeedEntry.COLUMN_NAME_OPTIONDTF1, IntToBooleans.toInt(profile1.getQuestion(position)
                .getChoiceD().isRight1()));

        String selection =
                FeedEntry.COLUMN_NAME_ENTRY_TESTNAME + "=? AND " + FeedEntry.COLUMN_NAME_QUESTION
                        + "=?";
        String[] selectionArgs =
                {profile1.getName(), profile1.getQuestion(position).getTitle().getText()};
        mSqLiteDatabase.update(FeedEntry.TABLE_NAME, mContentValues, selection, selectionArgs);
    }

    public void closeDataDB() {
        mSqLiteDatabase.close();
    }

    public Cursor select(String[] string) {

//        String[] columns =
//                {FeedEntry.COLUMN_NAME_QUESTION, FeedEntry.COLUMN_NAME_OPTIONA,
//                        FeedEntry.COLUMN_NAME_OPTIONATYPE, FeedEntry.COLUMN_NAME_OPTIONATF,
//                        FeedEntry.COLUMN_NAME_OPTIONATF1, FeedEntry.COLUMN_NAME_OPTIONB,
//                        FeedEntry.COLUMN_NAME_OPTIONBTYPE, FeedEntry.COLUMN_NAME_OPTIONBTF,
//                        FeedEntry.COLUMN_NAME_OPTIONBTF1, FeedEntry.COLUMN_NAME_OPTIONC,
//                        FeedEntry.COLUMN_NAME_OPTIONCTYPE, FeedEntry.COLUMN_NAME_OPTIONCTF,
//                        FeedEntry.COLUMN_NAME_OPTIONCTF1, FeedEntry.COLUMN_NAME_OPTIOND,
//                        FeedEntry.COLUMN_NAME_OPTIONATYPE, FeedEntry.COLUMN_NAME_OPTIONDTF,
//                        FeedEntry.COLUMN_NAME_OPTIONDTF1};
        String selection = FeedEntry.COLUMN_NAME_ENTRY_TESTNAME + "=? AND "+FeedEntry.COLUMN_NAME_TESTTIME + "=?";
        String[] selectionArgs = string;
        Cursor c =
                mSqLiteDatabase.query(FeedEntry.TABLE_NAME, null, selection, selectionArgs, null,
                        null, null);
//        Cursor c =
//                mSqLiteDatabase.query(FeedEntry.TABLE_NAME, null, null, null, null,
//                        null, null);
        return c;
    }

    public void delete() {
        mSqLiteDatabase.execSQL("DELETE FROM profile");
    }
}
