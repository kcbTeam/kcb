package com.kcb.common.util;
/**
 * 
 * @className: StudentInfo
 * @description: student information object
 * @author: ZQJ
 * @date: 2015年4月22日 下午4:04:50
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
