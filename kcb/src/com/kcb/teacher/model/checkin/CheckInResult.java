package com.kcb.teacher.model.checkin;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.kcb.teacher.model.StudentInfo;

/**
 * 
 * @className: CheckInRecordInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:01
 */
public class CheckInResult implements Serializable {

    private static final long serialVersionUID = 3L;

    private Date mDate;
    private double mRate;
    private List<StudentInfo> mUnCheckedStuInfos;

    public CheckInResult(Date signDate, Double signRate, List<StudentInfo> missedCheckInStus) {
        mDate = signDate;
        mRate = signRate;
        mUnCheckedStuInfos = missedCheckInStus;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public void setRate(float rate) {
        mRate = rate;
    }

    public double getRate() {
        return mRate;
    }

    public List<StudentInfo> getUnCheckedStudentInfos() {
        return mUnCheckedStuInfos;
    }

    public void setUnCheckedStudentInfos(List<StudentInfo> unCheckedStuInfos) {
        mUnCheckedStuInfos = unCheckedStuInfos;
    }
}
