package com.kcb.common.model.test;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @className: Question
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 上午10:08:21
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 2L;

    private int mId; // from client
    private double mRate;

    private QuestionItem mTitleItem;
    private QuestionItem mChoiceAItem;
    private QuestionItem mChoiceBItem;
    private QuestionItem mChoiceCItem;
    private QuestionItem mChoiceDItem;

    public Question() {
        mTitleItem = new QuestionItem();
        mChoiceAItem = new QuestionItem();
        mChoiceAItem.setId(0);
        mChoiceBItem = new QuestionItem();
        mChoiceBItem.setId(1);
        mChoiceCItem = new QuestionItem();
        mChoiceCItem.setId(2);
        mChoiceDItem = new QuestionItem();
        mChoiceDItem.setId(3);
    }

    public static Question clone(Question object) {
        Question question = new Question();
        question.mId = object.mId;
        question.mRate = object.mRate;
        QuestionItem.copy(object.mTitleItem, question.mTitleItem);
        QuestionItem.copy(object.mChoiceAItem, question.mChoiceAItem);
        QuestionItem.copy(object.mChoiceBItem, question.mChoiceBItem);
        QuestionItem.copy(object.mChoiceCItem, question.mChoiceCItem);
        QuestionItem.copy(object.mChoiceDItem, question.mChoiceDItem);
        return question;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public double getRate() {
        return mRate;
    }

    public QuestionItem getTitle() {
        return mTitleItem;
    }

    public QuestionItem getChoiceA() {
        return mChoiceAItem;
    }

    public QuestionItem getChoiceB() {
        return mChoiceBItem;
    }

    public QuestionItem getChoiceC() {
        return mChoiceCItem;
    }

    public QuestionItem getChoiceD() {
        return mChoiceDItem;
    }

    public boolean equal(Question questionObj) {
        return mTitleItem.equals(questionObj.getTitle())
                && mChoiceAItem.equals(questionObj.getChoiceA())
                && mChoiceBItem.equals(questionObj.getChoiceB())
                && mChoiceCItem.equals(questionObj.getChoiceC())
                && mChoiceDItem.equals(questionObj.getChoiceD());
    }

    public boolean isCompleted() {
        return mTitleItem.isCompleted()
                && mChoiceAItem.isCompleted()
                && mChoiceBItem.isCompleted()
                && mChoiceCItem.isCompleted()
                && mChoiceDItem.isCompleted()
                && (mChoiceAItem.isRight() || mChoiceBItem.isRight() || mChoiceCItem.isRight() || mChoiceDItem
                        .isRight());
    }

    public void changeStringToBitmap() {
        mTitleItem.changeStringToBitmap();
        mChoiceAItem.changeStringToBitmap();
        mChoiceBItem.changeStringToBitmap();
        mChoiceCItem.changeStringToBitmap();
        mChoiceDItem.changeStringToBitmap();
    }

    /**
     * question to json, json to question
     */
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CHOICE = "choice";
    public static final String KEY_RATE = "rate";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_TITLE, mTitleItem.toJsonObject());

            JSONArray choiceArray = new JSONArray();
            choiceArray.put(mChoiceAItem.toJsonObject());
            choiceArray.put(mChoiceBItem.toJsonObject());
            choiceArray.put(mChoiceCItem.toJsonObject());
            choiceArray.put(mChoiceDItem.toJsonObject());

            jsonObject.put(KEY_CHOICE, choiceArray);
            jsonObject.put(KEY_RATE, mRate);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static Question fromJson(JSONObject jsonObject) {
        Question question = new Question();
        question.mId = jsonObject.optInt(KEY_ID);
        question.mTitleItem = QuestionItem.fromJsonObject(jsonObject.optJSONObject(KEY_TITLE));

        JSONArray choiceArray = jsonObject.optJSONArray(KEY_CHOICE);
        try {
            question.mChoiceAItem = QuestionItem.fromJsonObject(choiceArray.getJSONObject(0));
            question.mChoiceBItem = QuestionItem.fromJsonObject(choiceArray.getJSONObject(1));
            question.mChoiceCItem = QuestionItem.fromJsonObject(choiceArray.getJSONObject(2));
            question.mChoiceDItem = QuestionItem.fromJsonObject(choiceArray.getJSONObject(3));
        } catch (JSONException e) {}

        question.mRate = jsonObject.optDouble(KEY_RATE);
        return question;
    }
}
