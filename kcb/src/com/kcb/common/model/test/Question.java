package com.kcb.common.model.test;

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
public class Question {

    private int mId; // 题号，由客户端确定，从0开始递增，如果删除了一个测试中的某个题目，题目需要重新设置id，以保持id的连续性
    private double mRate; // 此题的正确率

    private QuestionItem mTitleItem; // 题目描述内容
    private QuestionItem mChoiceAItem; // 选项A
    private QuestionItem mChoiceBItem; // 选项B
    private QuestionItem mChoiceCItem; // 选项C
    private QuestionItem mChoiceDItem; // 选项D

    public Question() {
        mTitleItem = new QuestionItem();
        mTitleItem.setId(0);
        mChoiceAItem = new QuestionItem();
        mChoiceAItem.setId(1);
        mChoiceBItem = new QuestionItem();
        mChoiceBItem.setId(2);
        mChoiceCItem = new QuestionItem();
        mChoiceCItem.setId(3);
        mChoiceDItem = new QuestionItem();
        mChoiceDItem.setId(4);
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

    public void setRate(double rate) {
        mRate = rate;
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

    public void setChoiceA(QuestionItem item) {
        mChoiceAItem = item;
    }

    public QuestionItem getChoiceB() {
        return mChoiceBItem;
    }

    public void setChoiceB(QuestionItem item) {
        mChoiceBItem = item;
    }

    public QuestionItem getChoiceC() {
        return mChoiceCItem;
    }

    public void setChoiceC(QuestionItem item) {
        mChoiceCItem = item;
    }

    public QuestionItem getChoiceD() {
        return mChoiceDItem;
    }

    public void setChoiceD(QuestionItem item) {
        mChoiceDItem = item;
    }

    public boolean equal(Question questionObj) {
        return mTitleItem.equals(questionObj.getTitle())
                && mChoiceAItem.equals(questionObj.getChoiceA())
                && mChoiceBItem.equals(questionObj.getChoiceB())
                && mChoiceCItem.equals(questionObj.getChoiceC())
                && mChoiceDItem.equals(questionObj.getChoiceD());
    }

    /**
     * 编辑测试：此题是否编辑完成了
     */
    public boolean isEditFinish() {
        return mTitleItem.isEidtFinish()
                && mChoiceAItem.isEidtFinish()
                && mChoiceBItem.isEidtFinish()
                && mChoiceCItem.isEidtFinish()
                && mChoiceDItem.isEidtFinish()
                && (mChoiceAItem.isRight() || mChoiceBItem.isRight() || mChoiceCItem.isRight() || mChoiceDItem
                        .isRight());
    }

    public void deleteBitmap() {
        mTitleItem.deleteBitmap();
        mChoiceAItem.deleteBitmap();
        mChoiceBItem.deleteBitmap();
        mChoiceCItem.deleteBitmap();
        mChoiceDItem.deleteBitmap();
    }

    public void renameBitmap(String testName, int questionIndex) {
        mTitleItem.renameBitmap(testName, questionIndex, 0);
        mChoiceAItem.renameBitmap(testName, questionIndex, 1);
        mChoiceBItem.renameBitmap(testName, questionIndex, 2);
        mChoiceCItem.renameBitmap(testName, questionIndex, 3);
        mChoiceDItem.renameBitmap(testName, questionIndex, 4);
    }

    /**
     * 解码字符串
     */
    public void decode() {
        mTitleItem.decode();
        mChoiceAItem.decode();
        mChoiceBItem.decode();
        mChoiceCItem.decode();
        mChoiceDItem.decode();
    }

    public void encode() {
        mTitleItem.encode();
        mChoiceAItem.encode();
        mChoiceBItem.encode();
        mChoiceCItem.encode();
        mChoiceDItem.encode();
    }

    public void release() {
        mTitleItem.release();
        mTitleItem = null;
        mChoiceAItem.release();
        mChoiceAItem = null;
        mChoiceBItem.release();
        mChoiceBItem = null;
        mChoiceCItem.release();
        mChoiceCItem = null;
        mChoiceDItem.release();
        mChoiceDItem = null;
    }

    /**
     * question to json, json to question
     */
    public static final String KEY_ID = "id";
    public static final String KEY_RATE = "rate";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CHOICES = "choices";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId); // 题目id
            jsonObject.put(KEY_RATE, mRate); // 此题的正确率

            // 需要设置题目内容和选项的bitmapkey，如果包括图片，上传图片的时候需要用到bitmapkey
            mTitleItem.setBitmapKey(getId());
            jsonObject.put(KEY_TITLE, mTitleItem.toJsonObject()); // 题目内容

            JSONArray choiceArray = new JSONArray();
            mChoiceAItem.setBitmapKey(getId());
            choiceArray.put(mChoiceAItem.toJsonObject());
            mChoiceBItem.setBitmapKey(getId());
            choiceArray.put(mChoiceBItem.toJsonObject());
            mChoiceCItem.setBitmapKey(getId());
            choiceArray.put(mChoiceCItem.toJsonObject());
            mChoiceDItem.setBitmapKey(getId());
            choiceArray.put(mChoiceDItem.toJsonObject());

            jsonObject.put(KEY_CHOICES, choiceArray); // 四个选项
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static Question fromJsonObject(JSONObject jsonObject) {
        Question question = new Question();
        question.mId = jsonObject.optInt(KEY_ID);
        question.mTitleItem = QuestionItem.fromJsonObject(jsonObject.optJSONObject(KEY_TITLE));

        JSONArray choiceArray = jsonObject.optJSONArray(KEY_CHOICES);
        question.mChoiceAItem = QuestionItem.fromJsonObject(choiceArray.optJSONObject(0));
        question.mChoiceBItem = QuestionItem.fromJsonObject(choiceArray.optJSONObject(1));
        question.mChoiceCItem = QuestionItem.fromJsonObject(choiceArray.optJSONObject(2));
        question.mChoiceDItem = QuestionItem.fromJsonObject(choiceArray.optJSONObject(3));

        question.mRate = jsonObject.optDouble(KEY_RATE);
        return question;
    }

    // TODO fengexian
    
    public static Question fromInternetJsonObject(JSONObject jsonObject) {
        Question question = new Question();
        question.mId = Integer.valueOf(jsonObject.optString("id"));
        question.mTitleItem =
                QuestionItem.fromInternetJsonObject(jsonObject.optJSONObject("title"));
        try {
            question.mChoiceAItem =
                    QuestionItem.fromInternetJsonObject(jsonObject.optJSONArray("choice")
                            .getJSONObject(0));
            question.mChoiceBItem =
                    QuestionItem.fromInternetJsonObject(jsonObject.optJSONArray("choice")
                            .getJSONObject(1));
            question.mChoiceCItem =
                    QuestionItem.fromInternetJsonObject(jsonObject.optJSONArray("choice")
                            .getJSONObject(2));
            question.mChoiceDItem =
                    QuestionItem.fromInternetJsonObject(jsonObject.optJSONArray("choice")
                            .getJSONObject(3));
        } catch (JSONException e) {}
        return question;
    }


    /**
     * 
     * @title: toJsonForInternet
     * @description:
     * @author: Zqj
     * @date: 2015年9月15日 下午3:02:39
     * @return
     */
    // TODO
    public JSONObject toJsonForInternet(int questionNum) {
        final String KEY_TITLE = "title";
        final String KEY_CHOICE = "choice";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_TITLE, mTitleItem.toJsonForInternet(questionNum, 0));
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(mChoiceAItem.toJsonForInternet(questionNum, 1));
            jsonArray.put(mChoiceBItem.toJsonForInternet(questionNum, 2));
            jsonArray.put(mChoiceCItem.toJsonForInternet(questionNum, 3));
            jsonArray.put(mChoiceDItem.toJsonForInternet(questionNum, 4));
            jsonObject.put(KEY_CHOICE, jsonArray);
        } catch (JSONException e) {}
        return jsonObject;
    }
}
