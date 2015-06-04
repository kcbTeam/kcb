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
    private ChoiceCheckInfo aCheckInfo;
    private ChoiceCheckInfo bCheckInfo;
    private ChoiceCheckInfo cCheckInfo;
    private ChoiceCheckInfo dCheckInfo;

    public QuestionAnswer(Question question) {
        mId = question.getId();
        aCheckInfo = new ChoiceCheckInfo(question.getChoiceA());
        bCheckInfo = new ChoiceCheckInfo(question.getChoiceB());
        cCheckInfo = new ChoiceCheckInfo(question.getChoiceC());
        dCheckInfo = new ChoiceCheckInfo(question.getChoiceD());
    }

    public int getId() {
        return mId;
    }

    public void setAnswer(Question question) {
        aCheckInfo.setIsSelected(question.getChoiceA());
        bCheckInfo.setIsSelected(question.getChoiceB());
        cCheckInfo.setIsSelected(question.getChoiceC());
        dCheckInfo.setIsSelected(question.getChoiceD());
    }

    public boolean hasFinished() {
        return aCheckInfo.isSelected() || bCheckInfo.isSelected() || cCheckInfo.isSelected()
                || dCheckInfo.isSelected();
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
            jsonArray.put(aCheckInfo.toJsonObject());
            jsonArray.put(bCheckInfo.toJsonObject());
            jsonArray.put(cCheckInfo.toJsonObject());
            jsonArray.put(dCheckInfo.toJsonObject());

            jsonObject.put(KEY_CHOICE, jsonArray);
        } catch (JSONException e) {}
        return jsonObject;
    }
}
