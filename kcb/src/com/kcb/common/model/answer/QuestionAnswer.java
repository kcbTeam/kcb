package com.kcb.common.model.answer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kcb.common.model.test.Question;

/**
 * 
 * @className: QuestionAnswer
 * @description: answer for one question
 * @author: wanghang
 * @date: 2015-6-4 下午1:11:42
 */
public class QuestionAnswer {

    private int mId; // question id
    private double mRate; // this question's correct rate
    private ChoiceCheckInfo mAInfo;
    private ChoiceCheckInfo mBInfo;
    private ChoiceCheckInfo mCInfo;
    private ChoiceCheckInfo mDInfo;

    public QuestionAnswer() {}

    public QuestionAnswer(Question question) {
        mId = question.getId();
        mAInfo = new ChoiceCheckInfo(question.getChoiceA());
        mBInfo = new ChoiceCheckInfo(question.getChoiceB());
        mCInfo = new ChoiceCheckInfo(question.getChoiceC());
        mDInfo = new ChoiceCheckInfo(question.getChoiceD());
    }

    public int getId() {
        return mId;
    }

    public double getRate() {
        return mRate;
    }

    public void setAnswer(Question question) {
        mAInfo.setIsSelected(question.getChoiceA());
        mBInfo.setIsSelected(question.getChoiceB());
        mCInfo.setIsSelected(question.getChoiceC());
        mDInfo.setIsSelected(question.getChoiceD());
    }

    public boolean hasFinished() {
        return mAInfo.isSelected() || mBInfo.isSelected() || mCInfo.isSelected()
                || mDInfo.isSelected();
    }

    /**
     * question answer to jsonobject
     */
    public static final String KEY_ID = "id";
    public static final String KEY_RATE = "rate";
    public static final String KEY_CHOICE = "choice";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_RATE, mRate);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(mAInfo.toJsonObject());
            jsonArray.put(mBInfo.toJsonObject());
            jsonArray.put(mCInfo.toJsonObject());
            jsonArray.put(mDInfo.toJsonObject());

            jsonObject.put(KEY_CHOICE, jsonArray);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static QuestionAnswer fromJsonObject(JSONObject jsonObject) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        try {
            questionAnswer.mId = jsonObject.optInt(KEY_ID);
            questionAnswer.mRate = jsonObject.optDouble(KEY_RATE);

            JSONArray jsonArray = jsonObject.optJSONArray(KEY_CHOICE);
            questionAnswer.mAInfo = ChoiceCheckInfo.fromJsonObject(jsonArray.getJSONObject(0));
            questionAnswer.mBInfo = ChoiceCheckInfo.fromJsonObject(jsonArray.getJSONObject(1));
            questionAnswer.mCInfo = ChoiceCheckInfo.fromJsonObject(jsonArray.getJSONObject(2));
            questionAnswer.mDInfo = ChoiceCheckInfo.fromJsonObject(jsonArray.getJSONObject(3));
        } catch (JSONException e) {}
        return questionAnswer;
    }
}
