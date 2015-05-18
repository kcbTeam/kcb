package com.kcb.teacher.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @className: StudentInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:09
 */
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mStudentName;
    private String mStudentID;

    private int mCheckInTimes;
    private int mTotalCheckInTimes;

    private int mCorrectTimes;
    private int mTotalChoiceQuestion;

    private HashMap<String, Float> mCorrectRateMap;

    public StudentInfo(String name, String studentID) {
        mStudentName = name;
        mStudentID = studentID;
        mCheckInTimes = 0;
        mTotalCheckInTimes = 0;
    }

    /**
     * 
     * Constructor: StudentInfo checkinTimes is success checkinTimes of the student
     */
    public StudentInfo(String name, String studentID, int checkinTimes, int totalCheckInTimes) {
        mStudentName = name;
        mStudentID = studentID;
        mCheckInTimes = checkinTimes;
        mTotalCheckInTimes = totalCheckInTimes;
    }

    /**
     * 
     * Constructor: StudentInfo checkinTimes is success checkinTimes of the student correctTimes is
     * correctTimes of the student
     * 
     */
    public StudentInfo(String name, String studentID, int checkinTimes, int totalCheckInTimes,
            int correctTimes, int totalChoiceQuestion) {
        mStudentName = name;
        mStudentID = studentID;
        mCheckInTimes = checkinTimes;
        mTotalCheckInTimes = totalCheckInTimes;
        mCorrectTimes = correctTimes;
        mTotalChoiceQuestion = totalChoiceQuestion;
    }

    public void setStudentID(String mStudentID) {
        this.mStudentID = mStudentID;
    }

    public void setStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    public String getStudentID() {
        return this.mStudentID;
    }

    public String getStudentName() {
        return this.mStudentName;
    }

    public int getCheckInTimes() {
        return this.mCheckInTimes;
    }

    public void setCheckInTimes(int mCheckInTimes) {
        this.mCheckInTimes = mCheckInTimes;
    }

    public int getTotalCheckInTimes() {
        return this.mTotalCheckInTimes;
    }

    public void setTotalCheckInTimes(int mTotalCheckInTimes) {
        this.mTotalCheckInTimes = mTotalCheckInTimes;
    }

    public HashMap<String, Float> getCorrectRateMap() {
        return this.mCorrectRateMap;
    }

    public void setCorrectRateMap(HashMap<String, Float> mCorrectRateMap) {
        this.mCorrectRateMap = mCorrectRateMap;
    }

    public int getCorrectTimes() {
        return this.mCorrectTimes;
    }

    public void setCorrectTimes(int mCorrectTimes) {
        this.mCorrectTimes = mCorrectTimes;
    }

    public int getTotalChoiceQuestion() {
        return this.mTotalChoiceQuestion;
    }

    public void setTotalChoiceQuestion(int mTotalChoiceQuestion) {
        this.mTotalChoiceQuestion = mTotalChoiceQuestion;
    }

    public void addTestRecord(String testName, float correctRate) {
        if (null == mCorrectRateMap) {
            mCorrectRateMap = new HashMap<String, Float>();
        }
        // TODO check testName repeat problem
        mCorrectRateMap.put(testName, correctRate);
    }

    public void addCorrectRecord(int correctTimes, int totalTimes) {
        if (correctTimes <= totalTimes) {
            mCorrectTimes += correctTimes;
            mTotalChoiceQuestion += totalTimes;
        }

    }

    public float getCheckInRate() {
        return (float) mCheckInTimes / mTotalCheckInTimes;
    }

    public float getCorrectRate() {
        return (float) mCorrectTimes / mTotalChoiceQuestion;
    }

}
