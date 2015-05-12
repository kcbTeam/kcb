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
	private int mMissTimes;

	private int mCorrectTimes;
	private int mTotalTimes;

	private HashMap<String, Float> mCorrectRateMap;

	public StudentInfo(String name, String studentID) {
		mStudentName = name;
		mStudentID = studentID;
		mCheckInTimes = 0;
		mMissTimes = 0;
	}

	public StudentInfo(String name, String studentID, int checkinTimes,
			int missTimes) {
		mStudentName = name;
		mStudentID = studentID;
		mCheckInTimes = checkinTimes;
		mMissTimes = missTimes;
	}

	public StudentInfo(String name, String studentID, int checkinTimes,
			int missTimes, int correctTimes, int totalTimes) {
		mStudentName = name;
		mStudentID = studentID;
		mCheckInTimes = checkinTimes;
		mMissTimes = missTimes;
		mCorrectTimes = correctTimes;
		mTotalTimes = totalTimes;
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

	public int getTotleTimes() {
		return this.mTotalTimes;
	}

	public void setTotleTimes(int mTotleTimes) {
		this.mTotalTimes = mTotleTimes;
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
			mTotalTimes += totalTimes;
		}

	}

	public float getCheckInRate() {
		return (float) mCheckInTimes / (mCheckInTimes + mMissTimes);
	}

	public float getCorrectRate() {
		return (float) mCorrectTimes / mTotalTimes;
	}

}
