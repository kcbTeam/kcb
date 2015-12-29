package com.kcb.common.model.answer;

import com.kcb.common.model.test.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
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

    public QuestionAnswer() {
    }

    public QuestionAnswer(Question question) {
        mId = question.getId();
        mAInfo = new ChoiceCheckInfo(question.getChoiceA());
        mBInfo = new ChoiceCheckInfo(question.getChoiceB());
        mCInfo = new ChoiceCheckInfo(question.getChoiceC());
        mDInfo = new ChoiceCheckInfo(question.getChoiceD());

        mQuestionPointer = question;
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
        } catch (JSONException e) {
        }
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
        } catch (JSONException e) {
        }
        return questionAnswer;
    }

    /**
     * xia
     */
    private Question mQuestionPointer;

    public JSONObject anserInfo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("qtb_id", mId);
            JSONArray jsonArray = new JSONArray();
            int isRight = 1;
            if (mAInfo.isSelected()) {
                jsonArray.put(mAInfo.getkeyValue());
                if (!mQuestionPointer.getChoiceA().isRight())
                    isRight = 0;
            }
            if (mBInfo.isSelected()) {
                jsonArray.put(mBInfo.getkeyValue());
                if (!mQuestionPointer.getChoiceB().isRight())
                    isRight = 0;
            }
            if (mCInfo.isSelected()) {
                jsonArray.put(mCInfo.getkeyValue());
                if (!mQuestionPointer.getChoiceC().isRight())
                    isRight = 0;
            }
            if (mDInfo.isSelected()) {
                jsonArray.put(mDInfo.getkeyValue());
                if (!mQuestionPointer.getChoiceD().isRight())
                    isRight = 0;
            }
            jsonObject.put("awr_key", jsonArray);
            jsonObject.put("isright", isRight);

            jsonArray = new JSONArray();
            if (mQuestionPointer.getChoiceA().isRight()) {
                jsonArray.put(mQuestionPointer.getChoiceA().getKeyValue());
            }
            if (mQuestionPointer.getChoiceB().isRight()) {
                jsonArray.put(mQuestionPointer.getChoiceB().getKeyValue());
            }
            if (mQuestionPointer.getChoiceC().isRight()) {
                jsonArray.put(mQuestionPointer.getChoiceC().getKeyValue());
            }
            if (mQuestionPointer.getChoiceD().isRight()) {
                jsonArray.put(mQuestionPointer.getChoiceD().getKeyValue());
            }

            jsonObject.put("right_key", jsonArray);
        } catch (JSONException e) {
        }
        return jsonObject;
    }
}
