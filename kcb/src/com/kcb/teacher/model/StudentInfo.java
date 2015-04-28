package com.kcb.teacher.model;

/**
 * 
 * @className: StudentInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:09
 */
public class StudentInfo {
    private String mStudentName;
    private String mStudentID;
    private int mCheckInTimes;
    private int mMissTimes;

    public StudentInfo(String name, String studentID) {
        mStudentName = name;
        mStudentID = studentID;
        mCheckInTimes = 0;
        mMissTimes = 0;
    }

    public StudentInfo(String name, String studentID, int checkinTimes, int missTimes) {
        mStudentName = name;
        mStudentID = studentID;
        mCheckInTimes = checkinTimes;
        mMissTimes = missTimes;
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

    public int getMissTimes() {
        return this.mMissTimes;
    }

    public void setMissTimes(int mMissTimes) {
        this.mMissTimes = mMissTimes;
    }


}
