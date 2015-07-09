package com.kcb.teacher.database.test;

import org.json.JSONObject;

/**
 * 
 * @className: TestResult
 * @description: 测试的结果，包括每道题的正确率
 * @author: wanghang
 * @date: 2015-7-9 下午5:02:19
 */
public class QuestionResult {

    private int mId; // 题目的id
    private double mRate; // 此题的正确率

    public int getId() {
        return mId;
    }

    public double getRate() {
        return mRate;
    }

    private static final String KEY_ID = "id";
    private static final String KEY_RATE = "rate";

    public static QuestionResult fromJsonObject(JSONObject jsonObject) {
        QuestionResult result = new QuestionResult();
        result.mId = jsonObject.optInt(KEY_ID);
        result.mRate = jsonObject.optDouble(KEY_RATE);
        return result;
    }
}
