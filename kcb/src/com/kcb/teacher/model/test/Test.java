package com.kcb.teacher.model.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId; // from server when start test
    private String mName; // test name
    private int mTime; // minute
    private List<Question> mQuestions;
    private Date mDate; // test date, from server when start test

    public Test(String name, int num) {
        mName = name;
        mQuestions = new ArrayList<Question>();
        for (int i = 0; i < num; i++) {
            mQuestions.add(new Question());
        }
    }

    public Test(String name, List<Question> questions, int time) {
        mName = name;
        mQuestions = questions;
        mTime = time;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
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

    public void changeTestToSerializable() {
        if (null == mQuestions || mQuestions.size() == 0) {
            return;
        }
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).changeQuestionToSerializable();
        }
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", mName);
            jsonObject.put("time", mTime);

            JSONArray questionArray = new JSONArray();
            for (int i = 0; i < mQuestions.size(); i++) {
                questionArray.put(mQuestions.get(i).toJsonObject());
            }
            jsonObject.put("questions", questionArray);
        } catch (JSONException e) {}
        return jsonObject;
    }
}
