package com.kcb.teacher.model;

/**
 * 
 * @className: CheckInRecordInfo
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午7:08:01
 */
public class CheckInRecordInfo {
    private String mSignDate;
    private String mSignRate;

    public CheckInRecordInfo(String signDate, String signRate) {
        mSignDate = signDate;
        mSignRate = signRate;
    }

    public void setSignDate(String signDate) {
        mSignDate = signDate;
    }

    public void setSignRate(String signRate) {
        mSignRate = signRate;
    }

    public String getSignDate() {
        return this.mSignDate;
    }

    public String getSignRate() {
        return this.mSignRate;
    }


}
