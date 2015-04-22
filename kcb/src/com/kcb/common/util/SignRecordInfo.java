package com.kcb.common.util;

/**
 * 
 * @className: SignRecordInfo
 * @description: every time sign info recorder
 * @author: ZQJ
 * @date: 2015年4月22日 下午5:26:57
 */
public class SignRecordInfo {
    private String mSignDate;
    private String mSignRate;

    public SignRecordInfo(String signDate, String signRate) {
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
