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

    public StudentInfo(String name, String studentID) {
        mStudentName = name;
        mStudentID = studentID;
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
}
