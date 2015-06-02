package com.kcb.teacher.model.stucentre;

import java.io.Serializable;
import java.util.HashMap;

import org.json.JSONObject;

/**
 * 
 * @className: StudentInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:09
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId;
    private String mName;

    private int mCheckInTimes;
    private int mTotalCheckInTimes;

    private int mCorrectTimes;
    private int mTotalChoiceQuestion;

    private HashMap<String, Float> mCorrectRateMap;

    private double mUnCheckedRate;
    
    //add by ljx
    private double mCheckInRate;
    private double mCorrectRate;

    public Student() {}

    public Student(String name, String studentID) {
        mName = name;
        mId = studentID;
        mCheckInTimes = 0;
        mTotalCheckInTimes = 0;
    }
    
  
    public static Student from(JSONObject jsonObject) {
        Student studentInfo = new Student();
        studentInfo.mId = jsonObject.optString("id");
        studentInfo.mName = jsonObject.optString("name");
        studentInfo.mUnCheckedRate = jsonObject.optDouble("rate");
      //add by ljx
        studentInfo.mCheckInRate = jsonObject.optDouble("checkinrate");
        studentInfo.mCorrectRate = jsonObject.optDouble("correctrate");
        //------------------
        return studentInfo;
    }

    /**
     * 
     * Constructor: StudentInfo checkinTimes is success checkinTimes of the student
     */
    public Student(String name, String studentID, int checkinTimes, int totalCheckInTimes) {
        mName = name;
        mId = studentID;
        mCheckInTimes = checkinTimes;
        mTotalCheckInTimes = totalCheckInTimes;
    }

    /**
     * 
     * Constructor: StudentInfo checkinTimes is success checkinTimes of the student correctTimes is
     * correctTimes of the student
     * 
     */
    public Student(String name, String studentID, int checkinTimes, int totalCheckInTimes,
            int correctTimes, int totalChoiceQuestion) {
        mName = name;
        mId = studentID;
        mCheckInTimes = checkinTimes;
        mTotalCheckInTimes = totalCheckInTimes;
        mCorrectTimes = correctTimes;
        mTotalChoiceQuestion = totalChoiceQuestion;
    }

    public void setStudentID(String mStudentID) {
        this.mId = mStudentID;
    }

    public void setStudentName(String mStudentName) {
        this.mName = mStudentName;
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
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
