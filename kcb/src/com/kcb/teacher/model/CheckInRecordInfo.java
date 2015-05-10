package com.kcb.teacher.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @className: CheckInRecordInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:01
 */
public class CheckInRecordInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3L;
    private String mSignDate;
    private float mSignRate;
    private List<StudentInfo> mMissedCheckInStus;

    public CheckInRecordInfo(String signDate, float signRate, List<StudentInfo> missedCheckInStus) {
        mSignDate = signDate;
        mSignRate = signRate;
        mMissedCheckInStus = missedCheckInStus;
    }

    public void setSignDate(String signDate) {
        mSignDate = signDate;
    }

    public void setSignRate(float signRate) {
        mSignRate = signRate;
    }

    public String getSignDate() {
        return this.mSignDate;
    }

    public float getSignRate() {
        return this.mSignRate;
    }

    public List<StudentInfo> getMissedCheckInStus() {
        return this.mMissedCheckInStus;
    }

    public void setMissedCheckInStus(List<StudentInfo> mMissedCheckInStus) {
        this.mMissedCheckInStus = mMissedCheckInStus;
    }

}
