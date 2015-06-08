package com.kcb.common.model.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kcb.common.model.answer.QuestionAnswer;
import com.kcb.common.model.answer.TestAnswer;

public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId; // from server when start test
    private String mName; // test name
    private int mTime = 5; // default test time is 5 minutes
    private long mDate; // test date, create date or start test date
    private boolean mHasTested; // true if teacher start test
    private List<Question> mQuestions;

    public Test() {
        mQuestions = new ArrayList<Question>();
    }

    public Test(String name, int num) {
        mName = name;
        mQuestions = new ArrayList<Question>();
        for (int i = 0; i < num; i++) {
            mQuestions.add(new Question());
        }
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public long getDate() {
        return mDate;
    }

    public String getDateString() {
        return new Date(mDate).toString();
    }

    public void setDate(long date) {
        mDate = date;
    }

    public boolean hasTested() {
        return mHasTested;
    }

    public void setTested(boolean isTested) {
        mHasTested = isTested;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public Question getQuestion(int index) {
        return mQuestions.get(index);
    }

    public void addQuestion() {
        mQuestions.add(new Question());
    }

    public void deleteQuestion(int index) {
        mQuestions.remove(index);
    }

    public void updateQuestion(int index, Question question) {
        mQuestions.set(index, question);
    }

    /**
     * set before start test, because use may add/delete question before start
     */
    public void setQuestionId() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).setId(i);
        }
    }

    public int getQuestionNum() {
        return mQuestions.size();
    }

    public boolean isCompleted() {
        for (int i = 0; i < mQuestions.size(); i++) {
            if (!mQuestions.get(i).isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public int getUnCompleteIndex() {
        for (int i = 0; i < mQuestions.size(); i++) {
            if (!mQuestions.get(i).isCompleted()) {
                return i;
            }
        }
        return -1;
    }

    public void changeStringToBitmap() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).changeStringToBitmap();
        }
    }

    public void setAnswer(TestAnswer testAnswer) {
        for (Question question : mQuestions) {
            for (QuestionAnswer questionAnswer : testAnswer.getQuestionAnswers()) {
                if (question.getId() == questionAnswer.getId()) {
                    question.setRate(questionAnswer.getRate());
                }
            }
        }
    }

    public String toString() {
        return toJsonObject().toString();
    }

    /**
     * test to json, json to test
     */
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";
    private static final String KEY_HASTESTED = "hastested";
    private static final String KEY_QUESTION = "question";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_NAME, mName);
            jsonObject.put(KEY_TIME, mTime);
            jsonObject.put(KEY_DATE, mDate);
            jsonObject.put(KEY_HASTESTED, mHasTested);

            JSONArray questionArray = new JSONArray();
            for (int i = 0; i < mQuestions.size(); i++) {
                mQuestions.get(i).setId(i);
                questionArray.put(mQuestions.get(i).toJsonObject());
            }

            jsonObject.put(KEY_QUESTION, questionArray);
        } catch (JSONException e) {}
        return jsonObject;
    }

    public static Test fromJsonObject(JSONObject jsonObject) {
        Test test = new Test();
        test.mId = jsonObject.optString(KEY_ID);
        test.mName = jsonObject.optString(KEY_NAME);
        test.mTime = jsonObject.optInt(KEY_TIME);
        test.mDate = jsonObject.optLong(KEY_DATE);
        test.mHasTested = jsonObject.optBoolean(KEY_HASTESTED);

        JSONArray questionArray = jsonObject.optJSONArray(KEY_QUESTION);
        for (int i = 0; i < questionArray.length(); i++) {
            try {
                Question question = Question.fromJson(questionArray.getJSONObject(i));
                test.mQuestions.add(question);
            } catch (JSONException e) {}
        }
        return test;
    }
}
