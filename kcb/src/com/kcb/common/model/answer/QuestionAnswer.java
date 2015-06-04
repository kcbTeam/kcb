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

    private int mId; // question id;
    private ChoiceCheckInfo mAInfo;
    private ChoiceCheckInfo mBInfo;
    private ChoiceCheckInfo mCInfo;
    private ChoiceCheckInfo nDInfo;

    public QuestionAnswer(Question question) {
        mId = question.getId();
        mAInfo = new ChoiceCheckInfo(question.getChoiceA());
        mBInfo = new ChoiceCheckInfo(question.getChoiceB());
        mCInfo = new ChoiceCheckInfo(question.getChoiceC());
        nDInfo = new ChoiceCheckInfo(question.getChoiceD());
    }

    public int getId() {
        return mId;
    }

    public void setAnswer(Question question) {
        mAInfo.setIsSelected(question.getChoiceA());
        mBInfo.setIsSelected(question.getChoiceB());
        mCInfo.setIsSelected(question.getChoiceC());
        nDInfo.setIsSelected(question.getChoiceD());
    }

    public boolean hasFinished() {
        return mAInfo.isSelected() || mBInfo.isSelected() || mCInfo.isSelected()
                || nDInfo.isSelected();
    }

    /**
     * question answer to jsonobject
     */
    public static final String KEY_ID = "id";
    public static final String KEY_CHOICE = "choice";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(mAInfo.toJsonObject());
            jsonArray.put(mBInfo.toJsonObject());
            jsonArray.put(mCInfo.toJsonObject());
            jsonArray.put(nDInfo.toJsonObject());

            jsonObject.put(KEY_CHOICE, jsonArray);
        } catch (JSONException e) {}
        return jsonObject;
    }
}
